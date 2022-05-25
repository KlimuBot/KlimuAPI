package eus.klimu.notification.domain.service.implementation;

import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.repository.NotificationTypeRepository;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationTypeServiceImp implements NotificationTypeService {

    private final NotificationTypeRepository notificationTypeRepository;

    @Override
    public Long countAll() {
        long count = notificationTypeRepository.count();
        log.info("Found {} notification types on the database", count);
        return count;
    }

    @Override
    public NotificationType getNotificationType(long id) {
        log.info("Fetching notification type with id={}", id);
        return notificationTypeRepository.findById(id).orElse(null);
    }

    @Override
    public NotificationType getNotificationType(String name) {
        log.info("Fetching notification type with name={}", name);
        return notificationTypeRepository.findByName(name);
    }

    @Override
    public List<NotificationType> getAllNotificationTypes() {
        log.info("Fetching all notification types");
        return notificationTypeRepository.findAll();
    }

    @Override
    public List<NotificationType> getAllNotificationTypes(String type) {
        log.info("Fetching all notification types with type={}", type);
        return notificationTypeRepository.findAllByType(type);
    }

    @Override
    public NotificationType addNewNotificationType(NotificationType notificationType) {
        log.info("Saving notification type {} on the database", notificationType.getName());
        return notificationTypeRepository.save(notificationType);
    }

    @Override
    public List<NotificationType> addAllNotificationTypes(List<NotificationType> notificationTypes) {
        log.info("Saving {} notification types on the database", notificationTypes.size());
        return notificationTypeRepository.saveAll(notificationTypes);
    }

    @Override
    public NotificationType updateNotificationType(NotificationType notificationType) {
        log.info("Updating notification type with id={}", notificationType.getId());
        return notificationTypeRepository.save(notificationType);
    }

    @Override
    public void deleteNotificationType(NotificationType notificationType) {
        log.info("Deleting notification type with id={}", notificationType.getId());
        notificationTypeRepository.delete(notificationType);
    }
}
