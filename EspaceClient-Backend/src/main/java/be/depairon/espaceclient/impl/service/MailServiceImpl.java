package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.configuration.CustomProperties;
import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dto.MessageDTO;
import be.depairon.espaceclient.dto.UserCreationDTO;
import be.depairon.espaceclient.eglobal.utils.encryption.AESUtils;
import be.depairon.espaceclient.eglobal.utils.encryption.YGEncryption;
import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.repository.CustomerRepository;
import be.depairon.espaceclient.repository.EglobalUserRepository;
import be.depairon.espaceclient.service.MailService;
import io.rocketbase.mail.EmailTemplateBuilder;
import io.rocketbase.mail.model.HtmlTextEmail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.mail.internet.MimeMessage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;
    private final EglobalUserRepository eglobalUserRepository;
    private final CustomerDao customerDao;

    @Value("${com.yglobal.mailgestionnaire}")
    private String mailGestionnaire;

    @Value("${com.yglobal.logo}")
    private String logo;

    @Value("${com.yglobal.resetpasslink}")
    private String resetpasslink;

    private final CustomProperties customProperties;

    @Autowired
    public MailServiceImpl(JavaMailSender emailSender, EglobalUserRepository eglobalUserRepository, CustomerDao customerDao, CustomProperties customProperties) {
        this.emailSender = emailSender;
        this.eglobalUserRepository = eglobalUserRepository;
        this.customerDao = customerDao;
        this.customProperties = customProperties;
    }

    // TODO utiliserle fichier .properties pour mettre les email from/to
    @Override
    public void sendNewRequest(RequestHeader requestHeader) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(requestHeader.getUser());
            helper.setFrom(customProperties.getMailgestionnaire());
            helper.setSubject("Site Depairon: Confirmation de votre demande");
            HtmlTextEmail body = createBody(requestHeader);
            helper.setText(body.getHtml(), true);
            emailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendForgotPassword(User user) {
        try {
            HtmlTextEmail body = createBody(user);
            if (body != null) {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setTo(user.getUserid());
                helper.setFrom(customProperties.getMailgestionnaire());
                helper.setSubject("Demande de récupération de mot de passe");
//            System.out.println(body.getHtml());
                helper.setText(body.getHtml(), true);
                emailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendUserCreation(UserCreationDTO userCreationDTO) {
        try {
            HtmlTextEmail body = createBody(userCreationDTO);
//            System.out.println(body.getHtml());
            if (body != null) {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setTo(customProperties.getMailinscription());
                helper.setFrom("noreply@depairon.be");
                helper.setSubject("Demande de création utilisateur");
                helper.setText(body.getHtml(), true);
                emailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HtmlTextEmail createBody(UserCreationDTO userCreationDTO) {
        EmailTemplateBuilder.EmailTemplateConfigBuilder builder = new EmailTemplateBuilder.EmailTemplateConfigBuilder();
        return builder.header().and()
                .text("Nouvelle demande de création de compte : ").and()
                .text("Données de l'utilisateur :").and()
                .attribute()
                .keyValue("Email", userCreationDTO.getEmail())
                .keyValue("Titre", userCreationDTO.getTitle())
                .keyValue("Fonction dans l'entreprise", userCreationDTO.getFunction())
                .keyValue("Nom", userCreationDTO.getLastname())
                .keyValue("Prénom", userCreationDTO.getFirstname())
                .keyValue("Téléphone", userCreationDTO.getTelephone())
                .keyValue("GSM", userCreationDTO.getMobile())
                .keyValue("Fax", userCreationDTO.getFax())
                .and()
                .text("Données de la société :").and()
                .attribute()
                .keyValue("Nom société", userCreationDTO.getSocityName())
                .keyValue("TVA ", userCreationDTO.getVat())
                .keyValue("Code postal ", userCreationDTO.getPostalCode())
                .keyValue("Localité ", userCreationDTO.getLocality())
                .keyValue("Pays ", userCreationDTO.getCountry())
                .keyValue("N° de téléphone général ", userCreationDTO.getGeneralTelephone())
                .keyValue("N° de fax général ", userCreationDTO.getGeneralFax())
                .keyValue("Adresse e-mail de l'entreprise ", userCreationDTO.getSocityMail())
                .keyValue("Secteur d'activité ", userCreationDTO.getActivitySector())
                .keyValue("Nombre de personnes employées ", userCreationDTO.getEmployeesNumber())
                .and()
                .hr()
                .and()
                .text("Note complémentaire").italic().and()
                .text(userCreationDTO.getAdditionalNotes())
                .build();
    }

    private HtmlTextEmail createBody(User user) {
        EmailTemplateBuilder.EmailTemplateConfigBuilder builder = new EmailTemplateBuilder.EmailTemplateConfigBuilder();
        Long id = user.getId();
        String cypherId;
        try {
            YGEncryption ygEncryption = new YGEncryption(YGEncryption.DES_ENCRYPTION_SCHEME);
            cypherId = URLEncoder.encode(ygEncryption.encrypt(String.valueOf(id)), StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return builder.header()
                .logo(logo).logoHeight(41).and()
                .text("Demande de récupération de mot de passe").h1().center().and()
                .text("Bonjour " + user.getTitle() + " " + user.getName() + " " + user.getFirstname() + ", ").and()
                .text("Vous avez effectué une demande de récupération de mot de passe.").and()
                .text("Si ce n'est pas vous, veuillez ignorer ce mail.").and()
                .text("Cliquez sur le bouton suivant pour y accéder.").and()
                .button("Réinitialiser", resetpasslink + "?userid=" + cypherId)
                .build();
    }

    private HtmlTextEmail createBody(RequestHeader requestHeader) { // Pour Depairon S.A., votre gestionnaire dossier  => CUS_SAL_RES
        String resUserdes = "";
        String resTel = "";
        String resMail = "";
        String cusNcs = "";
        String cusName = "";
        String comName = "";
        StringBuilder txt = new StringBuilder();
        for (RequestDetail requestDetail : requestHeader.getRequestDetail()) {
            txt.append("- ").append(requestDetail.getText()).append(" \n");
        }
        Customer customer = requestHeader.getCustomer();
        if (customer != null) {
            cusNcs = customer.getCusNcs();
            cusName = customer.getCusNm1();
            List<CusSal> cusSal = customer.getCusSal();
            CusSal c = cusSal.stream().findFirst().orElse(null);
            if (c != null) {
                EglobalUser eglobalUser = eglobalUserRepository.findByUserid(StringUtils.trim(c.getCusSalRes())).orElse(null);
                if (eglobalUser != null) {
                    resUserdes = eglobalUser.getUserdes();
                    resTel = eglobalUser.getTel();
                    resMail = eglobalUser.getEmail();
                }
            }
            Customer byNcsNda = customerDao.getByNcsNda(customer.getCusNcs(), 0);
            if (byNcsNda != null) {
                comName = byNcsNda.getCusNm1();
            }
        } // TODO SI PAS DE CUSTOMER, LE RECHERCHER VIA L'EMAIL DE L'UTILSATEUR, FAIRE çA ICI OU DANS LE CONTROLLER AVANT DE SAVE EN BD

        EmailTemplateBuilder.EmailTemplateConfigBuilder builder = new EmailTemplateBuilder.EmailTemplateConfigBuilder();
        String txtUnderline = StringUtils.replace(txt.toString(), "Commentaire utilisateur :", "<br><u>Commentaire utilisateur :</u>");
        String txtUnderlineBr = StringUtils.replace(txtUnderline, "\n", " <br> ");
        return builder
                .header()
                .logo(logo).logoHeight(41).and()
                .text("Confirmation de votre demande").h1().center().and()
                .attribute()
                .keyValue("N° de société", StringUtils.trim(cusNcs))
                .keyValue("Nom de la société", StringUtils.trim(comName))
                .keyValue("Site", StringUtils.trim(requestHeader.getSite()))
                .keyValue("Nom du demandeur", StringUtils.trim(requestHeader.getUser()))
                .keyValue("Numéro de la demande", StringUtils.trim(requestHeader.getTicket()))
                .keyValue("Porteur concerné", StringUtils.trim(requestHeader.getCarrier()))
                .and()
                .html("<u>Voici l'ensemble de vos demandes : </u>").and()
                .html(txtUnderlineBr).and()
                .text("Nous prenons immédiatement votre demande en compte et vous pourrez suivre vos différentes demandes dans votre espace dans la rubrique mes demandes.").italic().and()
                .text("Pour Depairon S.A., votre gestionnaire dossier").bold().and()
                .text(StringUtils.trim(resUserdes)).bold().and()
                .text("Tél: " + StringUtils.trim(resTel)).bold().and()
                .text("Fax: 087/31.57.74").bold().and()
                .text("Mail: " + StringUtils.trim(resMail)).bold().and()
                .footerText("[Depairon SA]").and()
                .build();
    }

}
