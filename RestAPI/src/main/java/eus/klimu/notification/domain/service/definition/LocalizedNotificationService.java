package eus.klimu.notification.domain.service.definition;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.NotificationType;

import java.util.List;

/**
 * Definition of the different functions available for a localized notification.
 */
public interface LocalizedNotificationService {

    /**
     * Get a localized notification based on its identification number.
     * @param id The identification number of the localized notification.
     * @return The localized notification with that identification number, if found.
     */
    LocalizedNotification getLocalizedNotification(long id);

    /**
     * Get a localized notification based on its location and notification type.
     * @param location The location the localized notification must be from.
     * @param notificationType The notification type the localized notification must be of.
     * @return The localized notification from that location and of that type, if found.
     */
    LocalizedNotification getLocalizedNotification(Location location, NotificationType notificationType);

    /**
     * Get all the localized notifications.
     * @return A list with all the localized notifications.
     */
    List<LocalizedNotification> getAllLocalizedNotifications();

    /**
     * Get all the localized notifications for a specific location.
     * @param location The location the localized notifications must be from.
     * @return A list with all the localized notifications from that location.
     */
    List<LocalizedNotification> getAllLocalizedNotifications(Location location);

    /**
     * Get all the localized notifications of a specific type.
     * @param notificationType The notification type the localized notifications must be of.
     * @return A list with all the localized notifications of that type.
     */
    List<LocalizedNotification> getAllLocalizedNotifications(NotificationType notificationType);

    /**
     * Save a new localized notification.
     * @param localizedNotification The localized notification that is going to be saved.
     * @return The localized notification after being saved.
     */
    LocalizedNotification addNewLocalizedNotification(LocalizedNotification localizedNotification);

    /**
     * Save a list of new localized notifications.
     * @param localizedNotifications The list of localized notifications that are going to be saved.
     * @return The list of localized notifications after being saved.
     */
    List<LocalizedNotification> addAllLocalizedNotifications(List<LocalizedNotification> localizedNotifications);

    /**
     * Update a localized notification.
     * @param localizedNotification The localized notification that is going to be updated.
     * @return The localized notification after being updated.
     */
    LocalizedNotification updateLocalizedNotification(LocalizedNotification localizedNotification);

    /**
     * Delete a localized notification.
     * @param localizedNotification The localized notification that is going to be deleted.
     */
    void deleteLocalizedNotification(LocalizedNotification localizedNotification);

}
