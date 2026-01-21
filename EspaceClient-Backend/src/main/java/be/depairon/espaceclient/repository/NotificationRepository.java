package be.depairon.espaceclient.repository;

import be.depairon.espaceclient.entity.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    Optional<Notification> findByGestionnaire(String gestionnaire);
}
