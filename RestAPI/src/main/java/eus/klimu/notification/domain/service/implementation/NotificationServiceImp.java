package eus.klimu.notification.domain.service.implementation;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.Notification;
import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.repository.NotificationRepository;
import eus.klimu.notification.domain.service.definition.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification getNotificationById(long id) {
        log.info("Fetching notification with id={}", id);
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Notification> getAllNotifications(Location location) {
        log.info("Fetching all notifications for location={}", location);
        return notificationRepository.getAllByLocation(location);
    }

    @Override
    public List<Notification> getAllNotifications(NotificationType type) {
        log.info("Fetching all notifications of type={}", type);
        return notificationRepository.getAllByType(type);
    }

    @Override
    public List<Notification> getAllNotifications(Date startDate, Date endDate) {
        log.info("Fetching all notifications between {} and {}", startDate, endDate);
        return notificationRepository.getAllByDateBetween(startDate, endDate);
    }

    @Override
    public List<Notification> getNotificationsByDateBetween(Location location, Date startDate, Date endDate) {
        log.info("Fetching all notifications for location={} between {} and {}", location, startDate, endDate);
        return notificationRepository.getAllByLocationAndDateBetween(location, startDate, endDate);
    }

    @Override
    public List<Notification> getNotificationsByDateBetween(NotificationType type, Date startDate, Date endDate) {
        log.info("Fetching all notifications of type={} between {} and {}", type, startDate, endDate);
        return notificationRepository.getAllByTypeAndDateBetween(type, startDate, endDate);
    }

    @Override
    public Notification addNewNotification(Notification notification) {
        log.info(
                "Saving notification {} on the database", notification.getType() +
                " (" + notification.getLocation() + ")[" + notification.getDate() + "]"
        );
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> addAllNotifications(List<Notification> notifications) {
        log.info("Saving {} new notifications", notifications.size());
        return notificationRepository.saveAll(notifications);
    }

    @Override
    public Notification updateNotification(Notification notification) {
        log.info("Updating notification with id={}", notification.getId());
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Notification notification) {
        log.info("Deleting notification with id={}", notification.getId());
        notificationRepository.delete(notification);
    }
}
