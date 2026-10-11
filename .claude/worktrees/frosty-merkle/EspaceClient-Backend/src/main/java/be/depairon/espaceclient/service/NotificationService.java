package be.depairon.espaceclient.service;

import be.depairon.espaceclient.entity.Notification;

public interface NotificationService {
    Notification getByGestionnaire(String gestionnaire);

    void save(Notification notification);
}
