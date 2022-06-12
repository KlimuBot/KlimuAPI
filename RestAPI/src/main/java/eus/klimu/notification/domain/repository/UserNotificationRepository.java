package eus.klimu.notification.domain.repository;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Access to the database for the user notifications.
 * Extends from a JpaRepository.
 */
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    /**
     * Get all the user notifications for a channel.
     * @param channel The channel the user notifications must be of.
     * @return A list with all the user notifications with that channel.
     */
    List<UserNotification> getAllByChannel(Channel channel);

    /**
     * Get all the user notifications that contain a localized notification.
     * @param notification The localized notification that the user notifications must
     *                     contain on their list.
     * @return A list with all the user notifications who have that localized notification.
     */
    List<UserNotification> getAllByNotifications(LocalizedNotification notification);

}
