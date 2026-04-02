package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.configuration.CustomProperties;
import be.depairon.espaceclient.dao.CustomerDao;
import be.depairon.espaceclient.dto.UserCreationDTO;
import be.depairon.espaceclient.eglobal.utils.encryption.YGEncryption;
import be.depairon.espaceclient.entity.*;
import be.depairon.espaceclient.repository.EglobalUserRepository;
import be.depairon.espaceclient.service.MailService;
import io.rocketbase.mail.EmailTemplateBuilder;
import io.rocketbase.mail.model.HtmlTextEmail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

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


    @Override
    public void sendForgotPassword(User user, String token) {
        try {
            HtmlTextEmail body = createBody(user, token);
            if (body != null) {
                MimeMessage mimeMessage = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setTo(user.getUserid());
                helper.setFrom(customProperties.getMailgestionnaire());
                helper.setSubject("Demande de récupération de mot de passe");
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

    private HtmlTextEmail createBody(User user, String token) {
        EmailTemplateBuilder.EmailTemplateConfigBuilder builder = new EmailTemplateBuilder.EmailTemplateConfigBuilder();
        String resetLink = resetpasslink + "?token=" + token;

        return builder.header()
                .logo(logo).logoHeight(41).and()
                .text("Demande de réinitialisation de mot de passe").h1().center().and()
                .text("Bonjour " + user.getTitle() + " " + user.getName() + " " + user.getFirstname() + ", ").and()
                .text("Vous avez effectué une demande de réinitialisation de mot de passe.").and()
                .text("Si ce n'est pas vous, veuillez ignorer ce mail.").and()
                .text("Ce lien est valide pendant 1 heure.").and()
                .text("Cliquez sur le bouton suivant pour y accéder.").and()
                .button("Réinitialiser", resetLink)
                .build();
    }

}
