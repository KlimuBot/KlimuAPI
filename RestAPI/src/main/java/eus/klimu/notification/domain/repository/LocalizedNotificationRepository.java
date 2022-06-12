package eus.klimu.notification.domain.repository;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Access to the database for the localized notifications.
 * Extends from a JpaRepository.
 */
public interface LocalizedNotificationRepository extends JpaRepository<LocalizedNotification, Long> {

    /**
     * Find all the localized notifications for a specific location.
     * @param location The location the localized notifications must be from.
     * @return A list with all the localized notifications for that location.
     */
    List<LocalizedNotification> getAllByLocation(Location location);

    /**
     * Find all the localized notifications with a specific type.
     * @param notificationType The type the localized notifications must be of.
     * @return A list with all the localized notifications of that type.
     */
    List<LocalizedNotification> getAllByType(NotificationType notificationType);

    /**
     * Find a localized notification for a specific location and type.
     * @param location The location the localized notification must be from.
     * @param notificationType The type the localized notification must be of.
     * @return A localized notification for that location and of that type, if found.
     */
    Optional<LocalizedNotification> getByLocationAndType(Location location, NotificationType notificationType);

}
