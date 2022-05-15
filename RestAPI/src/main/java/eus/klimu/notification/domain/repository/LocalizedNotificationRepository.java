package eus.klimu.notification.domain.repository;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalizedNotificationRepository extends JpaRepository<LocalizedNotification, Long> {

    List<LocalizedNotification> getAllByLocation(Location location);
    List<LocalizedNotification> getAllByType(NotificationType notificationType);

}
