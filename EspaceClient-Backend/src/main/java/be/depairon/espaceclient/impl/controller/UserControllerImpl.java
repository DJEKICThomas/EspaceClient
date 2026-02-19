package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.UserController;
import be.depairon.espaceclient.dto.MessageDTO;
import be.depairon.espaceclient.dto.ResetPasswordDTO;
import be.depairon.espaceclient.dto.UserCreationDTO;
import be.depairon.espaceclient.dto.UserDTO;
import be.depairon.espaceclient.eglobal.utils.ThreadMail;
import be.depairon.espaceclient.eglobal.utils.encryption.YGEncryption;
import be.depairon.espaceclient.entity.User;
import be.depairon.espaceclient.entity.WebUserCompany;
import be.depairon.espaceclient.enumeration.WebUserProfile;
import be.depairon.espaceclient.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final WebManagedCustomerService webManagedCustomerService;
    private final CustomerService customerService;
    private final MailService mailService;
    private final WebUserCompanyService webUserCompanyService;

    public UserControllerImpl(@Autowired UserService userService, WebManagedCustomerService webManagedCustomerService,
                              CustomerService customerService, MailService mailService, WebUserCompanyService webUserCompanyService) {
        this.userService = userService;
        this.webManagedCustomerService = webManagedCustomerService;
        this.customerService = customerService;
        this.mailService = mailService;
        this.webUserCompanyService = webUserCompanyService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getList(String userMail, String token) {
        User user = userService.getUser(userMail);
        List<UserDTO> userDTOList = new ArrayList<>();
        if (user != null) {
            List<User> list;
            if (user.getProfile().equals((WebUserProfile.ADMINISTRATEUR.toString()))
                    || user.getProfile().equals(WebUserProfile.DEPAIRON.toString())) {
                list = userService.getList(user);
            } else {
                list = userService.getByGestionnaireCompanies(user.getUserid());
            }
            if (CollectionUtils.isNotEmpty(list)) {
                userDTOList = list.stream().map(this::convert).toList();
            }
        }
        return userDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO get(String username, String token) {
        User user = userService.getUser(username);
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = convert(user);
        }
        return userDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(Long id, String token) {
        User user = userService.getUserById(id);
        UserDTO userDTO;
        if (user != null) {
            userDTO = convert(user);
        } else {
            userDTO = new UserDTO();
        }
        return userDTO;
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(UserDTO userDTO, String token) {
        if (isPasswordConfirmed(userDTO)) {
            UserDTO createdUserDto = convert(userService.createOrUpdate(convert(userDTO)));
            return ResponseEntity.status(HttpStatus.OK).body(createdUserDto);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new MessageDTO("Erreur : les mots de passe sont différents"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(UserCreationDTO userCreationDTO) {
        if (isEmailConfirmed(userCreationDTO)) {
            if (!userService.checkExist(userCreationDTO.getEmail())) {
                if (isPasswordConfirmed(userCreationDTO)) {
                    User user = convert(userCreationDTO);
                    user.setActive(0);
                    user.setProfile(WebUserProfile.UTILISATEUR.toString());
                    UserDTO createdUserDto = convert(userService.createOrUpdate(user));
                    WebUserCompany webUserCompany = convertToWebUserCompany(userCreationDTO);
                    webUserCompanyService.save(webUserCompany);
                    Runnable thread = new ThreadMail(mailService, userCreationDTO);
                    Thread t1 = new Thread(thread);
                    t1.start();
                    return ResponseEntity.status(HttpStatus.OK).body(createdUserDto);
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(new MessageDTO("Erreur : les mots de passe sont différents"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new MessageDTO("Erreur : utilisateur déjà existant avec cet email"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new MessageDTO("Erreur : les emails sont différents"));
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> delete(Long id, String token) {
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("Utilisateur supprimé"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDTO(e.getLocalizedMessage()));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> forgotPassword(UserDTO userDTO) {
        if (userDTO != null) {
            String mail = userDTO.getEmail();
            if (StringUtils.isNotBlank(mail)) {
                User user = userService.getUser(mail); // TODO do this line before if ???
                if (user != null) {
                    mailService.sendForgotPassword(user);
                    return ResponseEntity.ok(new MessageDTO("Email envoyé"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Aucun utilisateur trouvé pour l'email : " + mail));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Aucun email encodé"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Aucun email encodé"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        if (resetPasswordDTO != null) {
            if (StringUtils.equals(resetPasswordDTO.getPassword(), resetPasswordDTO.getConfirmPassword())) {
                // save here
                long webUserId;
                try {
                    String encryptedUserId = resetPasswordDTO.getWebUserId();
                    YGEncryption ygEncryption = new YGEncryption(YGEncryption.DES_ENCRYPTION_SCHEME);
                    String plainValue = ygEncryption.decrypt(encryptedUserId);
                    webUserId = Long.parseLong(plainValue);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDTO(""));
                }
                User user = userService.getUserById(webUserId);
                if (user != null) {
                    user.setPassword(resetPasswordDTO.getPassword());
                    userService.createOrUpdate(user);
                    return ResponseEntity.ok(new MessageDTO("Mot de passe mis à jour"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Aucun utilisateur trouvé !"));
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO("Mots de passe différents !"));
        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO("Erreur aucun body reçu !"));
        }
    }

    private UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getUserid().trim());
        userDTO.setLastname(user.getName().trim());
        userDTO.setFirstname(user.getFirstname().trim());
        userDTO.setUsrexpdate(user.getUsrexpdate());
        userDTO.setPwdexpired(user.getPwdexpired());
        userDTO.setNomail(user.getNomail());
        userDTO.setYguser(user.getYguser().trim());
        userDTO.setProfile(user.getProfile().trim());
        userDTO.setMobile(user.getGsm().trim());
        userDTO.setTelephone(user.getPhone().trim());
        userDTO.setFax(user.getFax().trim());
        userDTO.setPassword(user.getPassword().trim());
        userDTO.setConfirmPassword(user.getPassword().trim());
//        userDTO.setPassword("");
//        userDTO.setConfirmPassword("");
        userDTO.setTitle(user.getTitle().trim());
        userDTO.setFunction(user.getFunction().trim());
        userDTO.setMain(user.getMain());
        userDTO.setNewsletter(user.getNewsletter());
        userDTO.setActive(user.getActive());
        userDTO.setExtacces(user.getExtacces());
        return userDTO;
    }

    private User convert(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserid(userDTO.getEmail().trim());
        user.setPassword(userDTO.getPassword().trim());
        user.setPwdexpired(userDTO.getPwdexpired());
        user.setName(userDTO.getLastname().trim());
        user.setFirstname(userDTO.getFirstname().trim());
        user.setYguser(userDTO.getYguser().trim());
        LocalDate usrexpdate = userDTO.getUsrexpdate();
        user.setUsrexpdate((usrexpdate != null ? LocalDateTime.of(usrexpdate, LocalTime.of(23, 0)) : null));
        user.setPhone(userDTO.getTelephone().trim());
        user.setFax(userDTO.getFax().trim());
        user.setFunction(userDTO.getFunction());
        user.setLanguage("FR");
        user.setTitle(userDTO.getTitle().trim());
        user.setGsm(userDTO.getMobile().trim());
        user.setMain(userDTO.getMain());
        user.setProfile(userDTO.getProfile().trim());
        user.setNewsletter(userDTO.getNewsletter());
        user.setActive(userDTO.getActive());
        user.setNomail(userDTO.getNomail());
        user.setExtacces(userDTO.getExtacces());
        return user;
    }


    private User convert(UserCreationDTO userCreationDTO) {
        User user = new User();
        user.setUserid(StringUtils.trim(userCreationDTO.getEmail()));
        user.setPassword(StringUtils.trim(userCreationDTO.getPassword()));
        user.setName(StringUtils.trim(userCreationDTO.getLastname()));
        user.setFirstname(StringUtils.trim(userCreationDTO.getFirstname()));
        user.setPhone(StringUtils.trim(userCreationDTO.getTelephone()));
        user.setFax(StringUtils.trim(userCreationDTO.getFax()));
        user.setFunction(userCreationDTO.getFunction());
        user.setTitle(StringUtils.trim(userCreationDTO.getTitle()));
        user.setGsm(StringUtils.trim(userCreationDTO.getMobile()));
        user.setProfile(WebUserProfile.UTILISATEUR.toString());
        user.setUsrexpdate(LocalDate.now()); // FIXME mettre null ?????
        user.setYguser("");
        user.setLanguage("FR");
        user.setPwdexpired(0);
        user.setMain(0);
        user.setNewsletter(0);
        user.setActive(0);
        user.setNomail(0);
        user.setExtacces(0);
        return user;
    }


    private WebUserCompany convertToWebUserCompany(UserCreationDTO userCreationDTO) {
        WebUserCompany webUserCompany = new WebUserCompany();
        webUserCompany.setUserid(userCreationDTO.getEmail());
        webUserCompany.setName(userCreationDTO.getSocityName());
        webUserCompany.setAdress1(userCreationDTO.getAddess());
        webUserCompany.setAdress2("");
        webUserCompany.setPostalcode(userCreationDTO.getPostalCode());
        webUserCompany.setLocality(userCreationDTO.getLocality());
        webUserCompany.setCountry(userCreationDTO.getCountry());
        webUserCompany.setVat(userCreationDTO.getVat());
        webUserCompany.setSector(userCreationDTO.getActivitySector());
        int employee = 0;
        try {
            employee = Integer.parseInt(StringUtils.trim(userCreationDTO.getEmployeesNumber()));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        webUserCompany.setEmployee(employee);
        webUserCompany.setPhone(userCreationDTO.getGeneralTelephone());
        webUserCompany.setFax(userCreationDTO.getGeneralFax());
        webUserCompany.setMail(userCreationDTO.getSocityMail());
        webUserCompany.setComment(userCreationDTO.getAdditionalNotes());
        return webUserCompany;
    }


    private boolean isPasswordConfirmed(UserDTO userDTO) {
        return userDTO != null && userDTO.getPassword().equals(userDTO.getConfirmPassword());
    }

    private boolean isPasswordConfirmed(UserCreationDTO userCreationDTO) {
        return userCreationDTO != null && userCreationDTO.getPassword().equals(userCreationDTO.getConfirmPassword());
    }

    private boolean isEmailConfirmed(UserCreationDTO userCreationDTO) {
        return userCreationDTO != null && userCreationDTO.getEmail().equals(userCreationDTO.getConfirmEmail());
    }

}
