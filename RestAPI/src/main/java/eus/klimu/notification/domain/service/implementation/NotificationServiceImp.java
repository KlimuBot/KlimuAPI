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

/**
 * The implementation of the notification service. It interacts with the different notifications
 * using a notification repository, modifying directly on the database.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {

    /**
     * The connection with the notification table on the database.
     */
    private final NotificationRepository notificationRepository;

    /**
     * Get a notification based on its identification number from the database.
     * @param id The identification number of the notification.
     * @return The notification with that identification number from the database, if found.
     */
    @Override
    public Notification getNotificationById(long id) {
        log.info("Fetching notification with id={}", id);
        return notificationRepository.findById(id).orElse(null);
    }

    /**
     * Get all the notifications from a specific location form the database.
     * @param location The location the notifications must be from.
     * @return A list with all the notifications from that location from the database.
     */
    @Override
    public List<Notification> getAllNotifications(Location location) {
        log.info("Fetching all notifications for location={}", location);
        return notificationRepository.getAllByLocation(location);
    }

    /**
     * Get all the notification of a specific type from the database.
     * @param type The notification type the notifications must be of.
     * @return A list with all the notifications of that type from the database.
     */
    @Override
    public List<Notification> getAllNotifications(NotificationType type) {
        log.info("Fetching all notifications of type={}", type);
        return notificationRepository.getAllByType(type);
    }

    /**
     * Get all the notifications form the database.
     * @return A list with all the notifications from the database.
     */
    @Override
    public List<Notification> getAllNotifications() {
        log.info("Fetching last 50 notifications");
        return notificationRepository.findAll();
    }

    /**
     * Get the last 50 notifications from the database.
     * @return A list with the last 50 notifications from the database.
     */
    @Override
    public List<Notification> getAllNotificationsLimited() {
        log.info("Fetching last 50 notifications");
        return notificationRepository.findFirst50ByOrderByDateDesc();
    }

    /**
     * Get all the notifications from a location between two dates from the database.
     * @param location The location the notifications must be from.
     * @param startDate The starting date for the notifications.
     * @param endDate The final date of the notifications.
     * @return A list with all the notifications from that location between those dates from the database.
     */
    @Override
    public List<Notification> getNotificationsByDateBetween(Location location, Date startDate, Date endDate) {
        log.info("Fetching all notifications for location={} between {} and {}", location, startDate, endDate);
        return notificationRepository.getAllByLocationAndDateBetween(location, startDate, endDate);
    }

    /**
     * Get all the notifications of a notification type between two dates form the database.
     * @param type The notification type the notifications must be of.
     * @param startDate The starting date for the notifications.
     * @param endDate The final date of the notifications.
     * @return A list with all the notifications of a notification type between two dates from the database.
     */
    @Override
    public List<Notification> getNotificationsByDateBetween(NotificationType type, Date startDate, Date endDate) {
        log.info("Fetching all notifications of type={} between {} and {}", type, startDate, endDate);
        return notificationRepository.getAllByTypeAndDateBetween(type, startDate, endDate);
    }

    /**
     * Save a new notification on the database.
     * @param notification The notification that is going to be saved.
     * @return The notification after being saved on the database, with a new ID.
     */
    @Override
    public Notification addNewNotification(Notification notification) {
        log.info(
                "Saving notification {} on the database", notification.getType().getName() +
                " (" + notification.getLocation() + ")[" + notification.getDate() + "]"
        );
        return notificationRepository.save(notification);
    }

    /**
     * Save a list of new notifications on the database.
     * @param notifications The list of notifications that are going to be saved.
     * @return The list of notifications after being saved on the database, each with a new ID.
     */
    @Override
    public List<Notification> addAllNotifications(List<Notification> notifications) {
        log.info("Saving {} new notifications", notifications.size());
        return notificationRepository.saveAll(notifications);
    }

    /**
     * Update a notification from the database.
     * @param notification The notification that is going to be updated.
     * @return The notification after being updated on the database.
     */
    @Override
    public Notification updateNotification(Notification notification) {
        log.info("Updating notification with id={}", notification.getId());
        return notificationRepository.save(notification);
    }

    /**
     * Delete a notification from the database.
     * @param notification The notification that is going to be deleted.
     */
    @Override
    public void deleteNotification(Notification notification) {
        log.info("Deleting notification with id={}", notification.getId());
        notificationRepository.delete(notification);
    }
}
