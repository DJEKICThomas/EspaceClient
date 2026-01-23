package be.depairon.espaceclient.eglobal.utils;

import be.depairon.espaceclient.dto.UserCreationDTO;
import be.depairon.espaceclient.service.MailService;

public class ThreadMail implements Runnable {
    private final MailService service;
    private UserCreationDTO userCreationDTO;

    public ThreadMail(MailService service) {
        this.service = service;
    }

    public ThreadMail(MailService mailService, UserCreationDTO userCreationDTO) {
        this.service = mailService;
    }

    // Test
    @Override
    public void run() {

    }
}
