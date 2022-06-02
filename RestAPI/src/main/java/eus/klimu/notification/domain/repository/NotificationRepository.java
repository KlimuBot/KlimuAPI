package eus.klimu.notification.domain.repository;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.Notification;
import eus.klimu.notification.domain.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findFirst50ByOrderByDateDesc();
    List<Notification> getAllByType(NotificationType type);
    List<Notification> getAllByLocation(Location location);
    List<Notification> getAllByTypeAndDateBetween(NotificationType type, Date startDate, Date endDate);
    List<Notification> getAllByLocationAndDateBetween(Location location, Date startDate, Date endDate);

}
