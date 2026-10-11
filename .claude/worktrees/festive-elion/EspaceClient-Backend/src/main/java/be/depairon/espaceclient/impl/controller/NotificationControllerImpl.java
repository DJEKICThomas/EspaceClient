package be.depairon.espaceclient.impl.controller;

import be.depairon.espaceclient.controller.NotificationController;
import be.depairon.espaceclient.dto.MessageDTO;
import be.depairon.espaceclient.dto.NotificationDTO;
import be.depairon.espaceclient.entity.Notification;
import be.depairon.espaceclient.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class NotificationControllerImpl implements NotificationController {
    private final Logger log = LoggerFactory.getLogger(NotificationControllerImpl.class);

    @Autowired
    protected ObjectMapper objectMapper;

    private final NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public NotificationControllerImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getNotification(String gestionnaire, String token) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            Notification byGestionnaire = notificationService.getByGestionnaire(gestionnaire);
            return ResponseEntity.ok(ow.writeValueAsString(convert(byGestionnaire)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateNotification(NotificationDTO notificationDTO, String token) {
        String gestionnaire = notificationDTO.getGestionnaire();
        Notification byGestionnaire = notificationService.getByGestionnaire(gestionnaire);
        if (byGestionnaire != null) {
            byGestionnaire.setIsNew(0);
            notificationService.save(byGestionnaire);
            return ResponseEntity.ok(new MessageDTO("Notification mise à jour"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO("Pas de notification trouvée avec le gestionnaire : " + gestionnaire));
        }

    }
    //    @GetMapping("/notification/test")

    @MessageMapping("/notification/test")
    @SendTo("/notification/listen")
    @Transactional(readOnly = true)
    public String getSocketNotification(String gestionnaire) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(convert(notificationService.getByGestionnaire(gestionnaire)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Map<String, String> convert(Notification byGestionnaire) {
        Map<String, String> m = new HashMap<>();
        if (byGestionnaire != null) {
            m.put("id", String.valueOf(byGestionnaire.getId()));
            m.put("gestionnaire", byGestionnaire.getGestionnaire());
            m.put("isNew", String.valueOf(byGestionnaire.getIsNew()));
        } else {
            m.put("id", "");
            m.put("gestionnaire", "");
            m.put("isNew", "0");
        }
        return m;
    }
}
