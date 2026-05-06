package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.configuration.JwtTokenUtil;
import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.LoginController;
import be.depairon.espaceclient.dao.EglobalUserDao;
import be.depairon.espaceclient.entity.EglobalUser;
import be.depairon.espaceclient.dto.*;
import be.depairon.espaceclient.eglobal.utils.STR;
import be.depairon.espaceclient.eglobal.utils.Sec_Num_Alfa;
import be.depairon.espaceclient.eglobal.utils.Sec_Usr_Psw_Dcript;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.ManagedCustomer;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.enumeration.WebUserProfile;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.LoginService;
import be.depairon.espaceclient.service.EglobalUserService;
import be.depairon.espaceclient.service.UserService;
import be.depairon.espaceclient.service.WebManagedCustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;



import java.util.ArrayList;
import java.util.List;

import static be.depairon.espaceclient.enumeration.WebUserProfile.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginControllerImpl extends GenericController implements LoginController {

    private final LoginService loginService;
    private final UserService userService;
    private final WebManagedCustomerService webManagedCustomerService;
    private final CustomerService customerService;
    private final EglobalUserService eglobalUserService;
    //    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
//    private JwtUserDetailsService userDetailsService;

    public LoginControllerImpl(LoginService loginService,
                               UserService userService,
                               WebManagedCustomerService webManagedCustomerService,
                               CustomerService customerService,
                               JwtTokenUtil jwtTokenUtil,
                               EglobalUserService eglobalUserService/*,
                               AuthenticationManager authenticationManager,
                               JwtUserDetailsService userDetailsService*/) {
        this.loginService = loginService;
        this.userService = userService;
        this.webManagedCustomerService = webManagedCustomerService;
        this.customerService = customerService;
        this.eglobalUserService = eglobalUserService;
//        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
//        this.userDetailsService = userDetailsService;
    }

    @Override
    @Transactional(readOnly = true)
    public WebUserLoginDTO login(LoginFormDTO loginForm) {
        System.out.println(">>> Tentative de login pour : " + loginForm.getUsername());
        WebUserLoginDTO fullDTO = new WebUserLoginDTO();
        WebUserProfile profile = INCONNU;
        List<WebManagedCustomerDTO> list;
        User user = null;
        if (loginForm != null) {
            String username = loginForm.getUsername();
            String password = loginForm.getPassword();
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                user = loginService.login(username, password);
                if (user == null) {
                    System.out.println(">>> ÉCHEC : Utilisateur non trouvé dans l'AS400.");
                } else {
                    System.out.println(">>> SUCCÈS : Utilisateur trouvé, profil : " + user.getProfile());
                }
            }
        }
        if (user != null) {
            String userId;
            profile = WebUserProfile.getWebUserProfile(user.getProfile());
            fullDTO.setUserId(user.getId());
            fullDTO.setLanguage(user.getLanguage());

            // Si le 2FA est activé → on ne génère PAS le JWT maintenant
            // Le frontend devra d'abord soumettre le code TOTP via /totp/verify
            if (user.isTwoFaActive()) {
                System.out.println(">>> 2FA ACTIVÉ : en attente du code TOTP");
                fullDTO.setTotpRequired(true);
                fullDTO.setProfile(profile);
                return fullDTO;
            }

            fullDTO.setLoginOk(true);

            // 2FA non activé → on signale au front qu'il devra forcer le setup
            // (le token est quand même généré pour permettre d'appeler /totp/setup)
            if (!user.isTwoFaActive()) {
                fullDTO.setMustSetup2Fa(true);
            }

            // permet de transformer les données bruts en un objet via UserDetails
            // si le token doit contenir d'autres choses, les inclure ici
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUserid())
                    .password("")
                    .authorities(profile.toString())
                    .build();

            String token = jwtTokenUtil.generateToken(userDetails, profile);
            fullDTO.setToken(token);

            System.out.println(">>> TOKEN GÉNÉRÉ : " + token);

            userId = user.getUserid().trim();
            List<ManagedCustomer> listForUser = webManagedCustomerService.getList(userId, null);
            if (CollectionUtils.isNotEmpty(listForUser)) {
                list = listForUser.stream().map(this::convert).toList();
                fullDTO.setCustomers(list);
            }

            //MultiSociété
            List<EglobalUser> usrLines = eglobalUserService.getCompaniesByEmail(user.getUserid());
            if(usrLines != null && !usrLines.isEmpty()){
                List<UserCompanyDTO> companies = usrLines.stream().map(usr -> {
                    UserCompanyDTO companyDTO = new UserCompanyDTO();
                    companyDTO.setUsercmp(StringUtils.trim(usr.getUsercmp()));
                    companyDTO.setUserdep(StringUtils.trim(usr.getUserdep()));
                    companyDTO.seteGlobalUserId(StringUtils.trim(usr.getUserid()));
                    companyDTO.setCompanyName(resolveCompanyName(usr.getUsercmp()));
                    return companyDTO;
                }).toList();
                fullDTO.setCompanies(companies);

                if(companies.size() == 1){
                    fullDTO.setEGlobalUserId(companies.get(0).geteGlobalUserId());
                }
            }
        }
        fullDTO.setProfile(profile);
        return fullDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public WebUserLoginDTO loginFromErp(LoginRemoteDTO loginRemoteDTO){
        WebUserLoginDTO fullDTO = new WebUserLoginDTO();

        if(loginRemoteDTO == null){
            return fullDTO;
        }

        String username = loginRemoteDTO.getUsername();
        String encryptedPassword = loginRemoteDTO.getPassword();
        String ncs = loginRemoteDTO.getCustomerNumber();

        // 1. Déchiffre le mdp
        Sec_Num_Alfa Snum1 = new Sec_Num_Alfa();
        String USR_PAS = encryptedPassword;

        if (USR_PAS.equals("")) {
            return fullDTO;
        } else {
            USR_PAS = STR.Complete(USR_PAS, 21);
            String Zn20a = USR_PAS.substring(0, 20);
            String Zn1a = USR_PAS.substring(20, 21);
            Snum1.SetNum(Zn20a);
            Snum1.SetTbl(Zn1a);
            Snum1.Sec_Num();
            if (Snum1.AlfGetRtn() != 0) {
                return fullDTO;
            }
            USR_PAS = Snum1.AlfGet();
        }
        String passwordDecrypted = USR_PAS.trim();

        // 2. Vérifier email + mot de passe en base
        User user = loginService.login(username, passwordDecrypted);

        if (user == null) {
            return fullDTO;
        }

        // 3. Générer le JWT token
        WebUserProfile profile = WebUserProfile.getWebUserProfile(user.getProfile());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUserid().trim(), "", new ArrayList<>());
        String token = jwtTokenUtil.generateToken(userDetails, profile);

        // 4. Remplir la réponse
        fullDTO.setLoginOk(true);
        fullDTO.setUserId(user.getId());
        fullDTO.setToken(token);
        fullDTO.setProfile(profile);

        // 5. Récupérer le nom du client via le NCS
        if (StringUtils.isNotBlank(ncs)) {
            Customer customer = customerService.getByNcsNda(ncs, 0);
            if (customer != null) {
                WebManagedCustomerDTO customerDTO = new WebManagedCustomerDTO();
                customerDTO.setNcs(ncs);
                customerDTO.setCustomerName(StringUtils.trim(customer.getCusNm1()));
                fullDTO.setCustomers(List.of(customerDTO));
            }
        }

        // 6. Construction de l'URL
        String customerName = "";
        if (fullDTO.getCustomers() != null && !fullDTO.getCustomers().isEmpty()){
            customerName = fullDTO.getCustomers().get(0).getCustomerName();
        }

        String redirectUrl = "http://espace.depairon.be/erp-login"
                + "?token" + token
                + "&ncs" + ncs
                + "&profile" + profile
                + "&customerName" + customerName
                + "&username" + username;
        fullDTO.setRedirectUrl(redirectUrl);

        return fullDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public WebUserRemoteDTO loginFromRemote(LoginRemoteDTO loginRemoteDTO) {
        WebUserRemoteDTO remoteDTO = null;
        if (loginRemoteDTO != null) {
            remoteDTO = new WebUserRemoteDTO();
            String username = loginRemoteDTO.getUsername();
            String password = loginRemoteDTO.getPassword();
            String customerNumber = loginRemoteDTO.getCustomerNumber();
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) &&
                    StringUtils.isNotBlank(customerNumber)) {
                Sec_Usr_Psw_Dcript decrypt = new Sec_Usr_Psw_Dcript();
                decrypt.Set_iUsrPswTim(password);
                decrypt.Sec_Dcript();
                String passwordDecrypted = StringUtils.trim(decrypt.GetPsw());
                User user = loginService.login(username, passwordDecrypted);
                if (user != null) {
                    remoteDTO.setUserId(user.getId());
                    remoteDTO.setUsername(StringUtils.trim(user.getUserid()));
                    if (user.getProfile().equals(ADMINISTRATEUR.toString()) || user.getProfile().equals(DEPAIRON.toString())) {
                        remoteDTO.setProfile(WebUserProfile.getWebUserProfile(user.getProfile()));
                        Customer customer = customerService.getByNcsNda(customerNumber, 0);
                        if (customer != null) {
                            remoteDTO.setCustomerNumber(customerNumber);
                            remoteDTO.setCustomerName(StringUtils.trim(customer.getCusNm1()));
                        }
                    }
                }
            }
        }
        return remoteDTO;
    }

//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }

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
