package be.depairon.espaceclient.impl.service;

import be.depairon.espaceclient.entity.Notification;
import be.depairon.espaceclient.repository.NotificationRepository;
import be.depairon.espaceclient.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    @Override
    @Transactional
    public Notification getByGestionnaire(String gestionnaire) {
        return notificationRepository.findByGestionnaire(gestionnaire).orElse(null);
    }

    @Override
    @Transactional
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}
