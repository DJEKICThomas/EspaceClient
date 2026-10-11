package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.GenericController;
import be.depairon.espaceclient.controller.LoginController;
import be.depairon.espaceclient.dto.*;
import be.depairon.espaceclient.eglobal.utils.Sec_Usr_Psw_Dcript;
import be.depairon.espaceclient.entity.Customer;
import be.depairon.espaceclient.entity.ManagedCustomer;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.enumeration.WebUserProfile;
import be.depairon.espaceclient.service.CustomerService;
import be.depairon.espaceclient.service.LoginService;
import be.depairon.espaceclient.service.UserService;
import be.depairon.espaceclient.service.WebManagedCustomerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static be.depairon.espaceclient.enumeration.WebUserProfile.*;

@RestController
public class LoginControllerImpl extends GenericController implements LoginController {

    private final LoginService loginService;
    private final UserService userService;
    private final WebManagedCustomerService webManagedCustomerService;
    private final CustomerService customerService;
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenUtil jwtTokenUtil;
//    private JwtUserDetailsService userDetailsService;

    public LoginControllerImpl(LoginService loginService,
                               UserService userService,
                               WebManagedCustomerService webManagedCustomerService,
                               CustomerService customerService/*,
                               AuthenticationManager authenticationManager,
                               JwtTokenUtil jwtTokenUtil,
                               JwtUserDetailsService userDetailsService*/) {
        this.loginService = loginService;
        this.userService = userService;
        this.webManagedCustomerService = webManagedCustomerService;
        this.customerService = customerService;
//        this.authenticationManager = authenticationManager;
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.userDetailsService = userDetailsService;
    }

    @Override
    @Transactional(readOnly = true)
    public WebUserLoginDTO login(LoginFormDTO loginForm) {
        WebUserLoginDTO fullDTO = new WebUserLoginDTO();
        WebUserProfile profile = INCONNU;
        List<WebManagedCustomerDTO> list;
        User user = null;
        if (loginForm != null) {
            String username = loginForm.getUsername();
            String password = loginForm.getPassword();
            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                user = loginService.login(username, password);
            }
        }
        if (user != null) {
            String userId;
            profile = WebUserProfile.getWebUserProfile(user.getProfile());
            fullDTO.setUserId(user.getId());
            if (profile.equals(ADMINISTRATEUR) || profile.equals(DEPAIRON)) {
                fullDTO.setLoginOk(true);
            } else {
                userId = user.getUserid().trim();
                List<ManagedCustomer> listForUser = webManagedCustomerService.getList(userId);
                if (CollectionUtils.isNotEmpty(listForUser)) {
                    list = listForUser.stream().map(this::convert).toList();
                    profile = valueOf(StringUtils.trim(user.getProfile()));
                    fullDTO.setLoginOk(true);
                    fullDTO.setCustomers(list);
                }
            }


//            try {
//                authenticate(loginForm.getUsername(), loginForm.getPassword());
//                final UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getUsername());
//                final String token = jwtTokenUtil.generateToken(userDetails, profile);
//                jwtTokenUtil.getClaimFromToken(token, claims -> claims.get("userprofile"))
            fullDTO.setToken("");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            String userid = StringUtils.trim(userService.getEGlobalUserId(user));
            fullDTO.seteGlobalUserId(userid);
        }
        fullDTO.setProfile(profile);
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
