package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.configuration.JwtTokenUtil;
import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.TotpController;
import be.depairon.espaceclient.dto.TotpRequestDTO;
import be.depairon.espaceclient.dto.TotpResponseDTO;
import be.depairon.espaceclient.dto.WebManagedCustomerDTO;
import be.depairon.espaceclient.dto.WebUserLoginDTO;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.ManagedCustomer;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.enumeration.WebUserProfile;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.TotpService;
import be.depairon.espaceclient.service.UserService;
import be.depairon.espaceclient.service.WebManagedCustomerService;
import dev.samstevens.totp.exceptions.QrGenerationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TotpControllerImpl extends GenericController implements TotpController {

    private final TotpService totpService;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final WebManagedCustomerService webManagedCustomerService;
    private final CustomerService customerService;

    public TotpControllerImpl(TotpService totpService,
                              UserService userService,
                              JwtTokenUtil jwtTokenUtil,
                              WebManagedCustomerService webManagedCustomerService,
                              CustomerService customerService) {
        this.totpService = totpService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.webManagedCustomerService = webManagedCustomerService;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public TotpResponseDTO setup(TotpRequestDTO requestDTO) {
        TotpResponseDTO response = new TotpResponseDTO();
        User user = userService.getUserById(requestDTO.getUserId());

        if (user == null) {
            response.setSuccess(false);
            response.setMessage("Utilisateur non trouvé");
            return response;
        }

        try {
            String secret = totpService.generateSecret();
            String qrCodeDataUri = totpService.getQrCodeDataUri(secret, user.getUserid());

            // Sauvegarde le secret mais n'active PAS encore le 2FA
            // Il sera activé après confirmation du premier code (dans verify)
            user.setTwoFaSecret(secret);
            userService.createOrUpdate(user);

            response.setSuccess(true);
            response.setQrCodeDataUri(qrCodeDataUri);
        } catch (QrGenerationException e) {
            response.setSuccess(false);
            response.setMessage("Erreur lors de la génération du QR code");
        }

        return response;
    }

    @Override
    @Transactional
    public WebUserLoginDTO verify(TotpRequestDTO requestDTO) {
        WebUserLoginDTO fullDTO = new WebUserLoginDTO();
        User user = userService.getUserById(requestDTO.getUserId());

        if (user == null) {
            fullDTO.setProfile(WebUserProfile.INCONNU);
            return fullDTO;
        }

        String secret = user.getTwoFaSecret();
        boolean isCodeValid = totpService.verifyCode(secret, requestDTO.getCode());

        if (!isCodeValid) {
            fullDTO.setProfile(WebUserProfile.INCONNU);
            fullDTO.setTotpRequired(true);
            fullDTO.setUserId(user.getId());
            return fullDTO;
        }

        // Si le 2FA n'était pas encore activé → c'est la confirmation du setup
        if (!user.isTwoFaActive()) {
            user.setTwoFaEnabled("Y");
            userService.createOrUpdate(user);
        }

        // Code TOTP valide → génération du JWT (même logique que LoginControllerImpl)
        WebUserProfile profile = WebUserProfile.getWebUserProfile(user.getProfile());
        fullDTO.setUserId(user.getId());
        fullDTO.setLoginOk(true);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUserid())
                .password("")
                .authorities(profile.toString())
                .build();

        String token = jwtTokenUtil.generateToken(userDetails, profile);
        fullDTO.setToken(token);
        fullDTO.setProfile(profile);

        String userId = user.getUserid().trim();
        List<ManagedCustomer> listForUser = webManagedCustomerService.getList(userId);
        if (CollectionUtils.isNotEmpty(listForUser)) {
            List<WebManagedCustomerDTO> list = listForUser.stream().map(this::convert).toList();
            fullDTO.setCustomers(list);
        }

        String eGlobalUserId = StringUtils.trim(userService.getEGlobalUserId(user));
        fullDTO.seteGlobalUserId(eGlobalUserId);

        return fullDTO;
    }

    @Override
    @Transactional
    public TotpResponseDTO disable(TotpRequestDTO requestDTO) {
        TotpResponseDTO response = new TotpResponseDTO();
        User user = userService.getUserById(requestDTO.getUserId());

        if (user == null) {
            response.setSuccess(false);
            response.setMessage("Utilisateur non trouvé");
            return response;
        }

        boolean isCodeValid = totpService.verifyCode(user.getTwoFaSecret(), requestDTO.getCode());

        if (!isCodeValid) {
            response.setSuccess(false);
            response.setMessage("Code invalide");
            return response;
        }

        user.setTwoFaSecret(null);
        user.setTwoFaEnabled("N");
        userService.createOrUpdate(user);

        response.setSuccess(true);
        response.setMessage("2FA désactivé");
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public TotpResponseDTO status(Long userId) {
        TotpResponseDTO response = new TotpResponseDTO();
        User user = userService.getUserById(userId);

        if (user == null) {
            response.setSuccess(false);
            response.setMessage("Utilisateur non trouvé");
            return response;
        }

        // success = true si le 2FA est activé, false sinon
        response.setSuccess(user.isTwoFaActive());
        return response;
    }

    private WebManagedCustomerDTO convert(ManagedCustomer managedCustomer) {
        WebManagedCustomerDTO dto = mapper.map(managedCustomer, WebManagedCustomerDTO.class);
        long customerId = managedCustomer.getCustomer().getId();
        Customer customer = customerService.getById(customerId);
        if (customer != null) {
            dto.setNcs(StringUtils.trim(customer.getCusNcs()));
            String cusName = customer.getCusNm1();
            if (StringUtils.isNotBlank(cusName)) {
                dto.setCustomerName(cusName.trim());
            }
        }
        return dto;
    }
}
