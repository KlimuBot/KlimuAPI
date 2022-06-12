package eus.klimu.notification.domain.service.implementation;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.UserNotification;
import eus.klimu.notification.domain.repository.UserNotificationRepository;
import eus.klimu.notification.domain.service.definition.UserNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The implementation of the user notification service. It interacts with the different user notifications
 * using a user notification repository, modifying directly on the database.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserNotificationServiceImp implements UserNotificationService {

    /**
     * The connection with the user notification table on the database.
     */
    private final UserNotificationRepository userNotificationRepository;

    /**
     * Get a user notification based on its identification number from the database.
     * @param id The identification number of the user notification.
     * @return A user notification with that identification number from the database., if found.
     */
    @Override
    public UserNotification getUserNotification(long id) {
        log.info("Fetching user notification with id={}", id);
        return userNotificationRepository.findById(id).orElse(null);
    }

    /**
     * Get all the user notifications from the database.
     * @return A list with all the user notifications from the database.
     */
    @Override
    public List<UserNotification> getUserNotifications() {
        log.info("Fetching all user notifications from database");
        return userNotificationRepository.findAll();
    }

    /**
     * Get all the user notifications from a specific channel from the database.
     * @param channel The channel the user notifications must be from.
     * @return A list with all the user notifications from that channel from the database.
     */
    @Override
    public List<UserNotification> getUserNotificationsByChannel(Channel channel) {
        log.info("Fetching user notifications with channel={}", channel.getName());
        return userNotificationRepository.getAllByChannel(channel);
    }

    /**
     * Get all the user notifications with a specific localized notification from the database.
     * @param localizedNotification The localized notification that the user notifications must have.
     * @return A list with all the user notifications with that localized notification from the database.
     */
    @Override
    public List<UserNotification> getUserNotificationsByNotification(LocalizedNotification localizedNotification) {
        log.info("Fetching user notifications with localized notification for {}", localizedNotification.toString());
        return userNotificationRepository.getAllByNotifications(localizedNotification);
    }

    /**
     * Save a new user notification on the database.
     * @param userNotification The user notification that is going to be saved.
     * @return The user notification after being saved on the database, with a new ID.
     */
    @Override
    public UserNotification addNewUserNotification(UserNotification userNotification) {
        log.info("Saving a user notification on the database");
        return userNotificationRepository.save(userNotification);
    }

    /**
     * Save a list of new user notifications on the database.
     * @param userNotifications The list of user notifications that are going to be saved.
     * @return A list with all the user notifications after being saved on the database, each with a new ID.
     */
    @Override
    public List<UserNotification> addAllUserNotifications(List<UserNotification> userNotifications) {
        log.info("Saving {} user notifications on the database", userNotifications.size());
        return userNotificationRepository.saveAll(userNotifications);
    }

    /**
     * Update a user notification on the database.
     * @param userNotification The user notification that is going to be updated.
     * @return The user notification after being updated on the database.
     */
    @Override
    public UserNotification updateUserNotification(UserNotification userNotification) {
        log.info("Updating user notification with id={}", userNotification.getId());
        return userNotificationRepository.save(userNotification);
    }

    /**
     * Delete a user notification form the database.
     * @param userNotification The user notification that is going to be deleted.
     */
    @Override
    public void deleteUserNotifications(UserNotification userNotification) {
        log.info("Deleting user notification with id={}", userNotification.getId());
        userNotificationRepository.delete(userNotification);
    }
}
