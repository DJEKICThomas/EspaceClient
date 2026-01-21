package be.depairon.espaceclient.eglobal.utils;

import be.depairon.espaceclient.dto.UserCreationDTO;
import be.depairon.espaceclient.entity.RequestHeader;
import be.depairon.espaceclient.service.MailService;

public class ThreadMail implements Runnable {

    private final MailService service;

    private boolean startRequestThread = false;
    private RequestHeader requestHeader;

    private boolean startUserCreationThread = false;
    private UserCreationDTO userCreationDTO;

    public ThreadMail(MailService service, RequestHeader requestHeader) {
        this.service = service;
        this.requestHeader = requestHeader;
        this.startRequestThread = true;
    }

    public ThreadMail(MailService mailService, UserCreationDTO userCreationDTO) {
        this.service = mailService;
        this.userCreationDTO = userCreationDTO;
        this.startUserCreationThread = true;
    }

    @Override
    public void run() {
//        System.out.println("Run sending mail");
        if (this.startRequestThread) {
            this.startRequestThread = false;
            service.sendNewRequest(requestHeader);
        }
        if (this.startUserCreationThread) {
            this.startUserCreationThread = false;
            service.sendUserCreation(userCreationDTO);
        }
    }
}
