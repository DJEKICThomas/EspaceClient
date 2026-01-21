package be.depairon.espaceclient.service;

import be.depairon.espaceclient.dto.UserCreationDTO;
import be.depairon.espaceclient.entity.RequestHeader;
import be.depairon.espaceclient.entity.User;

public interface MailService {

    void sendNewRequest(RequestHeader requestHeader);

    void sendForgotPassword(User user);

    void sendUserCreation(UserCreationDTO userCreationDTO);
}
