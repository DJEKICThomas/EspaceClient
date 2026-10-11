package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.User;

public interface MailService {
    void sendForgotPassword(User user, String token);
}
