package eus.klimu.notification.domain.service.implementation;

import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.repository.NotificationTypeRepository;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The implementation of the notification type service. It interacts with the different notification types
 * using a notification type repository, modifying directly on the database.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationTypeServiceImp implements NotificationTypeService {

    /**
     * The connection with the notification type table on the database.
     */
    private final NotificationTypeRepository notificationTypeRepository;

    /**
     * Count all the notification types on the database.
     * @return The number of notification types on the database.
     */
    @Override
    public Long countAll() {
        long count = notificationTypeRepository.count();
        log.info("Found {} notification types on the database", count);
        return count;
    }

    /**
     * Get a notification type based on its identification number from the database.
     * @param id The identification number of the notification type.
     * @return The notification type with that identification number from the database, if found.
     */
    @Override
    public NotificationType getNotificationType(long id) {
        log.info("Fetching notification type with id={}", id);
        return notificationTypeRepository.findById(id).orElse(null);
    }

    /**
     * Get a notification type based on its name from the database.
     * @param name The name of the notification type.
     * @return The notification type with that name from the database, if found.
     */
    @Override
    public NotificationType getNotificationType(String name) {
        log.info("Fetching notification type with name={}", name);
        return notificationTypeRepository.findByName(name);
    }

    /**
     * Get all the notification types from the database.
     * @return A list with all the notification types from the database.
     */
    @Override
    public List<NotificationType> getAllNotificationTypes() {
        log.info("Fetching all notification types");
        return notificationTypeRepository.findAll();
    }

    /**
     * Get all the notification types of a specific type from the database.
     * @param type The type that the notification types must be of.
     * @return A list with all the notification types of that type from the database.
     */
    @Override
    public List<NotificationType> getAllNotificationTypes(String type) {
        log.info("Fetching all notification types with type={}", type);
        return notificationTypeRepository.findAllByType(type);
    }

    /**
     * Save a new notification type on the database.
     * @param notificationType The notification type that is going to be saved.
     * @return The notification type after being saved on the database, with a new ID.
     */
    @Override
    public NotificationType addNewNotificationType(NotificationType notificationType) {
        log.info("Saving notification type {} on the database", notificationType.getName());
        return notificationTypeRepository.save(notificationType);
    }

    /**
     * Save a list of new notification types on the database.
     * @param notificationTypes A list of notification types that are going to be saved.
     * @return The list of notification types after being saved on the database, each with a new ID.
     */
    @Override
    public List<NotificationType> addAllNotificationTypes(List<NotificationType> notificationTypes) {
        log.info("Saving {} notification types on the database", notificationTypes.size());
        return notificationTypeRepository.saveAll(notificationTypes);
    }

    /**
     * Update a notification type on the database.
     * @param notificationType The notification type that is going to be updated.
     * @return The notification type after being updated on the database.
     */
    @Override
    public NotificationType updateNotificationType(NotificationType notificationType) {
        log.info("Updating notification type with id={}", notificationType.getId());
        return notificationTypeRepository.save(notificationType);
    }

    /**
     * Delete a notification type from the database.
     * @param notificationType The notification type that is going to be deleted.
     */
    @Override
    public void deleteNotificationType(NotificationType notificationType) {
        log.info("Deleting notification type with id={}", notificationType.getId());
        notificationTypeRepository.delete(notificationType);
    }
}
