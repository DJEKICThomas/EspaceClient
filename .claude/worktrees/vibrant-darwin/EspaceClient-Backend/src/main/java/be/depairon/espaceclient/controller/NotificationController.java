package be.depairon.espaceclient.controller;

import be.depairon.espaceclient.dto.NotificationDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequestMapping("/notification")
public interface NotificationController {
    @GetMapping
    ResponseEntity<?> getNotification(@RequestParam("gestionnaire") String gestionnaire, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> updateNotification(@RequestBody NotificationDTO notificationDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token);
}
