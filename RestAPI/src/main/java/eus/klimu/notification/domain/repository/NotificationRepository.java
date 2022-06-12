package eus.klimu.notification.domain.repository;

import eus.klimu.location.domain.model.Location;
import eus.klimu.notification.domain.model.Notification;
import eus.klimu.notification.domain.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Access to the database for the notifications.
 * Extends from a JpaRepository.
 */
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Get the 50 most recent notifications from the database on a descending order.
     * @return A list with the last 50 notifications.
     */
    List<Notification> findFirst50ByOrderByDateDesc();

    /**
     * Get all the notifications for a specific type.
     * @param type The notification type the notifications must be of.
     * @return A list with all the notifications with that type.
     */
    List<Notification> getAllByType(NotificationType type);

    /**
     * Get all the notifications for a specific location.
     * @param location The location the notifications must be from.
     * @return A list with all the notifications for that location.
     */
    List<Notification> getAllByLocation(Location location);

    /**
     * Get all the notifications for a specific type between two dates.
     * @param type The notification type the notifications must be of.
     * @param startDate The starting Date for the notifications.
     * @param endDate The final date of the notifications.
     * @return A list with all the notifications of that type between those dates.
     */
    List<Notification> getAllByTypeAndDateBetween(NotificationType type, Date startDate, Date endDate);

    /**
     * Get all the notifications for a specific location between two dates.
     * @param location The location the notifications must be from.
     * @param startDate The starting Date for the notifications.
     * @param endDate The final date of the notifications.
     * @return A list with all the notifications from that location between those dates.
     */
    List<Notification> getAllByLocationAndDateBetween(Location location, Date startDate, Date endDate);

}
