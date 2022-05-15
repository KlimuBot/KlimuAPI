package eus.klimu.notification.domain.repository;

import eus.klimu.notification.domain.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    NotificationType findByName(String name);
    List<NotificationType> findAllByType(String type);

}
