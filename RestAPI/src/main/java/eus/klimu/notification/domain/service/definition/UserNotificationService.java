package eus.klimu.notification.domain.service.definition;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.UserNotification;

import java.util.List;

/**
 * Definition of the different functions available for a user notification.
 */
public interface UserNotificationService {

    /**
     * Get a user notification based on its identification number.
     * @param id The identification number of the user notification.
     * @return A user notification with that identification number, if found.
     */
    UserNotification getUserNotification(long id);

    /**
     * Get all the user notifications.
     * @return A list with all the user notifications.
     */
    List<UserNotification> getUserNotifications();

    /**
     * Get all the user notifications from a specific channel.
     * @param channel The channel the user notifications must be from.
     * @return A list with all the user notifications from that channel.
     */
    List<UserNotification> getUserNotificationsByChannel(Channel channel);

    /**
     * Get all the user notifications with a specific localized notification.
     * @param localizedNotification The localized notification that the user notifications must have.
     * @return A list with all the user notifications with that localized notification.
     */
    List<UserNotification> getUserNotificationsByNotification(LocalizedNotification localizedNotification);

    /**
     * Save a new user notification.
     * @param userNotification The user notification that is going to be saved.
     * @return The user notification after being saved.
     */
    UserNotification addNewUserNotification(UserNotification userNotification);

    /**
     * Save a list of new user notifications.
     * @param userNotifications The list of user notifications that are going to be saved.
     * @return A list with all the user notifications after being saved.
     */
    List<UserNotification> addAllUserNotifications(List<UserNotification> userNotifications);

    /**
     * Update a user notification.
     * @param userNotification The user notification that is going to be updated.
     * @return The user notification after being updated.
     */
    UserNotification updateUserNotification(UserNotification userNotification);

    /**
     * Delete a user notification.
     * @param userNotification The user notification that is going to be deleted.
     */
    void deleteUserNotifications(UserNotification userNotification);

}
