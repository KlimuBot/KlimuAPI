package eus.klimu.notification.domain.service.definition;

import eus.klimu.notification.domain.model.NotificationType;

import java.util.List;

/**
 * Definition of the different functions available for a notification type.
 */
public interface NotificationTypeService {

    /**
     * Count all the notification types.
     * @return The number of notification types.
     */
    Long countAll();

    /**
     * Get a notification type based on its identification number.
     * @param id The identification number of the notification type.
     * @return The notification type with that identification number, if found.
     */
    NotificationType getNotificationType(long id);

    /**
     * Get a notification type based on its name.
     * @param name The name of the notification type.
     * @return The notification type with that name, if found.
     */
    NotificationType getNotificationType(String name);

    /**
     * Get all the notification types.
     * @return A list with all the notification types.
     */
    List<NotificationType> getAllNotificationTypes();

    /**
     * Get all the notification types of a specific type.
     * @param type The type that the notification types must be of.
     * @return A list with all the notification types of that type.
     */
    List<NotificationType> getAllNotificationTypes(String type);

    /**
     * Save a new notification type.
     * @param notificationType The notification type that is going to be saved.
     * @return The notification type after being saved.
     */
    NotificationType addNewNotificationType(NotificationType notificationType);

    /**
     * Save a list of new notification types.
     * @param notificationTypes A list of notification types that are going to be saved.
     * @return The list of notification types after being saved.
     */
    List<NotificationType> addAllNotificationTypes(List<NotificationType> notificationTypes);

    /**
     * Update a notification type.
     * @param notificationType The notification type that is going to be updated.
     * @return The notification type after being updated.
     */
    NotificationType updateNotificationType(NotificationType notificationType);

    /**
     * Delete a notification type.
     * @param notificationType The notification type that is going to be deleted.
     */
    void deleteNotificationType(NotificationType notificationType);

}
