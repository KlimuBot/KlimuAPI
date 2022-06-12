package eus.klimu.notification.domain.repository;

import eus.klimu.notification.domain.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Access to the database for the notification types.
 * Extends from a JpaRepository.
 */
public interface NotificationTypeRepository extends JpaRepository<NotificationType, Long> {

    /**
     * Find a notification type based on its name.
     * @param name The name of the notification type the function is looking for.
     * @return The notification type, if it was found.
     */
    NotificationType findByName(String name);

    /**
     * Find all the notifications with a specific type.
     * @param type The type the notification type must be of.
     * @return A list with all the notification types with that type.
     */
    List<NotificationType> findAllByType(String type);

}
