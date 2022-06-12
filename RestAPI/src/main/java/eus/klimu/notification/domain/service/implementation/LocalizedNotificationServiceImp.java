package eus.klimu.notification.domain.service.implementation;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.repository.LocalizedNotificationRepository;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the localized notification service. It interacts with the different localized notifications
 * using a localized notification repository, modifying directly on the database.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocalizedNotificationServiceImp implements LocalizedNotificationService {

    /**
     * The connection with the localized notification table on the database.
     */
    private final LocalizedNotificationRepository localizedNotificationRepository;

    /**
     * Get a localized notification based on its identification number from the database.
     * @param id The identification number of the localized notification.
     * @return The localized notification with that identification number on the database, if found.
     */
    @Override
    public LocalizedNotification getLocalizedNotification(long id) {
        log.info("Fetching notification with id={}", id);
        return localizedNotificationRepository.findById(id).orElse(null);
    }

    /**
     * Get a localized notification based on its location and notification type from the database.
     * @param location The location the localized notification must be from.
     * @param notificationType The notification type the localized notification must be of.
     * @return he localized notification from that location and of that type, if found.
     */
    @Override
    public LocalizedNotification getLocalizedNotification(Location location, NotificationType notificationType) {
        log.info("Fetching localized notification of type={} on location={}",
                location.toString(), notificationType.getName());
        return localizedNotificationRepository.getByLocationAndType(location, notificationType).orElse(null);
    }

    /**
     * Get all the localized notifications from the database.
     * @return A list with all the localized notifications from the database.
     */
    @Override
    public List<LocalizedNotification> getAllLocalizedNotifications() {
        log.info("Fetching all localized notifications");
        return localizedNotificationRepository.findAll();
    }

    /**
     * Get all the localized notifications for a specific location from the database.
     * @param location The location the localized notifications must be from.
     * @return A list with all the localized notifications from that location from the database.
     */
    @Override
    public List<LocalizedNotification> getAllLocalizedNotifications(Location location) {
        log.info("Fetching all localized notifications on {}", location.toString());
        return localizedNotificationRepository.getAllByLocation(location);
    }

    /**
     * Get all the localized notifications of a specific type from the database.
     * @param notificationType The notification type the localized notifications must be of.
     * @return A list with all the localized notifications of that type from the database.
     */
    @Override
    public List<LocalizedNotification> getAllLocalizedNotifications(NotificationType notificationType) {
        log.info("Fetching all localized notifications of type {}", notificationType.getName());
        return localizedNotificationRepository.getAllByType(notificationType);
    }

    /**
     * Save a new localized notification on the database.
     * @param localizedNotification The localized notification that is going to be saved.
     * @return The localized notification after being saved on the database, with a new ID.
     */
    @Override
    public LocalizedNotification addNewLocalizedNotification(LocalizedNotification localizedNotification) {
        log.info("Saving localized notification {} on the database", localizedNotification.toString());
        LocalizedNotification ln = localizedNotificationRepository.getByLocationAndType(
                localizedNotification.getLocation(),
                localizedNotification.getType()
        ).orElse(null);

        if (ln != null) {
            return ln;
        }
        return localizedNotificationRepository.save(localizedNotification);
    }

    /**
     * Save a list of new localized notifications on the database.
     * @param localizedNotifications The list of localized notifications that are going to be saved.
     * @return The list of localized notifications after being saved on the database, with new IDs.
     */
    @Override
    public List<LocalizedNotification> addAllLocalizedNotifications(List<LocalizedNotification> localizedNotifications) {
        log.info("Saving {} localized notifications on the database", localizedNotifications.size());
        List<LocalizedNotification> savedLN = new ArrayList<>();
        localizedNotifications.forEach(ln -> savedLN.add(addNewLocalizedNotification(ln)));
        return savedLN;
    }

    /**
     * Update a localized notification on the database.
     * @param localizedNotification The localized notification that is going to be updated.
     * @return The localized notification after being updated on the database.
     */
    @Override
    public LocalizedNotification updateLocalizedNotification(LocalizedNotification localizedNotification) {
        log.info("Updating localized notification with id={}", localizedNotification.getId());
        return localizedNotificationRepository.save(localizedNotification);
    }

    /**
     * Delete a localized notification form the database.
     * @param localizedNotification The localized notification that is going to be deleted.
     */
    @Override
    public void deleteLocalizedNotification(LocalizedNotification localizedNotification) {
        log.info("Deleting notification with id={}", localizedNotification.getId());
        localizedNotificationRepository.delete(localizedNotification);
    }
}
