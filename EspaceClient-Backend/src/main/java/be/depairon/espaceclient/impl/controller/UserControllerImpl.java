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
import be.depairon.espaceclient.security.AdminAccessChecker;
import be.depairon.espaceclient.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import be.depairon.espaceclient.security.PasswordValidator;
import be.depairon.espaceclient.dto.AdminResetPasswordDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final WebManagedCustomerService webManagedCustomerService;
    private final CustomerService customerService;
    private final MailService mailService;
    private final WebUserCompanyService webUserCompanyService;
    private final AdminAccessChecker adminAccessChecker;

    public UserControllerImpl(@Autowired UserService userService, WebManagedCustomerService webManagedCustomerService,
                              CustomerService customerService, MailService mailService, WebUserCompanyService webUserCompanyService,
                              AdminAccessChecker adminAccessChecker) {
        this.userService = userService;
        this.webManagedCustomerService = webManagedCustomerService;
        this.customerService = customerService;
        this.mailService = mailService;
        this.webUserCompanyService = webUserCompanyService;
        this.adminAccessChecker = adminAccessChecker;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getList(String userMail, String token) {
        adminAccessChecker.requireAdmin(token);
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
        adminAccessChecker.requireAdmin(token);
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
        adminAccessChecker.requireAdmin(token);
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
                User user = userService.getUser(mail);
                if (user != null) {
                    String token = UUID.randomUUID().toString();
                    user.setResetToken(token);
                    user.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
                    userService.createOrUpdate(user);
                    mailService.sendForgotPassword(user, token);
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

                // Validation de la complexité du nouveau mot de passe
                List<String> errors = PasswordValidator.validate(resetPasswordDTO.getPassword());
                if (!errors.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new MessageDTO(String.join(" / ", errors)));
                }

                User user = userService.getByResetToken(resetPasswordDTO.getToken());
                if (user != null) {
                    if (LocalDateTime.now().isBefore(user.getResetTokenExpiry())) {
                        user.setPassword(resetPasswordDTO.getPassword());
                        user.setResetToken("");
                        user.setResetTokenExpiry(LocalDateTime.of(1900, 1, 1, 0, 0));
                        userService.createOrUpdate(user);
                        return ResponseEntity.ok(new MessageDTO("Mot de passe mis à jour"));
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO("Token expiré"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Token invalide !"));
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO("Mots de passe différents !"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO("Erreur aucun body reçu !"));
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> adminResetPassword(Long id, AdminResetPasswordDTO adminResetPasswordDTO, String token){
        //Appel possible que par l'admin
        adminAccessChecker.requireAdmin(token);

        //Body obligatoire
        if(adminResetPasswordDTO == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO("Aucun body reçu"));
        }

        //Vérification que les 2 mdp correspondent
        if(!StringUtils.equals(adminResetPasswordDTO.getNewPassword(), adminResetPasswordDTO.getConfirmPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO("Les mots de passes sont différents"));
        }

        //Validation de la complexité
        List<String> errors = PasswordValidator.validate(adminResetPasswordDTO.getNewPassword());
        if(!errors.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO(String.join("/", errors)));
        }

        //Récup de l'utilisateur cible
        User user = userService.getUserById(id);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Utilisateur introuvable"));
        }

        //MAJ du mdp
        user.setPassword(adminResetPasswordDTO.getNewPassword());
        userService.createOrUpdate(user);

        return ResponseEntity.ok(new MessageDTO("Mot de passe mis à jour"));

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

        User user;
        // Si on update un user existant, on charge depuis la BDD pour préserver
        // les champs non exposés dans le DTO (ERP_TOKEN, RESET_TOKEN, TWO_FA_SECRET, etc.)
        if (userDTO.getId() != null && userDTO.getId() != 0) {
            user = userService.getUserById(userDTO.getId());
            if (user == null) {
                user = new User();
            }
        } else {
            user = new User();
        }
        user.setId(userDTO.getId());
        user.setUserid(userDTO.getEmail().trim());
        user.setPassword(userDTO.getPassword().trim());
        user.setPwdexpired(userDTO.getPwdexpired());
        user.setName(userDTO.getLastname().trim());
        user.setFirstname(userDTO.getFirstname().trim());
        user.setYguser(userDTO.getYguser().trim());
        user.setUsrexpdate(userDTO.getUsrexpdate());
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
        user.setUsrexpdate(null);
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

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> validateToken(String token) {
        User user = userService.getByResetToken(token);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageDTO("Token invalide"));
        }

        if (user.getResetTokenExpiry() == null || LocalDateTime.now().isAfter(user.getResetTokenExpiry())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageDTO("Token expiré"));
        }

        return ResponseEntity.ok(new MessageDTO("Token valide"));
    }

}
