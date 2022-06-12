package eus.klimu.notification.domain.service.definition;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.Notification;
import eus.klimu.notification.domain.model.NotificationType;

import java.util.Date;
import java.util.List;

/**
 * Definition of the different functions available for a notification.
 */
public interface NotificationService {

    /**
     * Get a notification based on its identification number.
     * @param id The identification number of the notification.
     * @return The notification with that identification number, if found.
     */
    Notification getNotificationById(long id);

    /**
     * Get all the notifications from a specific location.
     * @param location The location the notifications must be from.
     * @return A list with all the notifications from that location.
     */
    List<Notification> getAllNotifications(Location location);

    /**
     * Get all the notification of a specific type.
     * @param type The notification type the notifications must be of.
     * @return A list with all the notifications of that type.
     */
    List<Notification> getAllNotifications(NotificationType type);

    /**
     * Get all the notifications.
     * @return A list with all the notifications.
     */
    List<Notification> getAllNotifications();

    /**
     * Get the last 50 notifications.
     * @return A list with the last 50 notifications.
     */
    List<Notification> getAllNotificationsLimited();

    /**
     * Get all the notifications from a location between two dates.
     * @param location The location the notifications must be from.
     * @param startDate The starting date for the notifications.
     * @param endDate The final date of the notifications.
     * @return A list with all the notifications from that location between those dates.
     */
    List<Notification> getNotificationsByDateBetween(Location location, Date startDate, Date endDate);

    /**
     * Get all the notifications of a notification type between two dates.
     * @param type The notification type the notifications must be of.
     * @param startDate The starting date for the notifications.
     * @param endDate The final date of the notifications.
     * @return A list with all the notifications of a notification type between two dates.
     */
    List<Notification> getNotificationsByDateBetween(NotificationType type, Date startDate, Date endDate);

    /**
     * Save a new notification.
     * @param notification The notification that is going to be saved.
     * @return The notification after being saved.
     */
    Notification addNewNotification(Notification notification);

    /**
     * Save a list of new notifications.
     * @param notifications The list of notifications that are going to be saved.
     * @return The list of notifications after being saved.
     */
    List<Notification> addAllNotifications(List<Notification> notifications);

    /**
     * Update a notification.
     * @param notification The notification that is going to be updated.
     * @return The notification after being updated.
     */
    Notification updateNotification(Notification notification);

    /**
     * Delete a notification.
     * @param notification The notification that is going to be deleted.
     */
    void deleteNotification(Notification notification);

}
