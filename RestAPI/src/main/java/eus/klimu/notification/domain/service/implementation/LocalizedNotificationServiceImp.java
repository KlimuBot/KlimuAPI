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
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocalizedNotificationServiceImp implements LocalizedNotificationService {

    private final LocalizedNotificationRepository localizedNotificationRepository;

    @Override
    public LocalizedNotification getLocalizedNotification(long id) {
        log.info("Fetching notification with id={}", id);
        return localizedNotificationRepository.getById(id);
    }

    @Override
    public LocalizedNotification getLocalizedNotification(Location location, NotificationType notificationType) {
        log.info("Fetching localized notification of type={} on location={}",
                location.toString(), notificationType.getName());
        return localizedNotificationRepository.getByLocationAndType(location, notificationType).orElse(null);
    }

    @Override
    public List<LocalizedNotification> getAllLocalizedNotifications() {
        log.info("Fetching all localized notifications");
        return localizedNotificationRepository.findAll();
    }

    @Override
    public List<LocalizedNotification> getAllLocalizedNotifications(Location location) {
        log.info("Fetching all localized notifications on {}", location.toString());
        return localizedNotificationRepository.getAllByLocation(location);
    }

    @Override
    public List<LocalizedNotification> getAllLocalizedNotifications(NotificationType notificationType) {
        log.info("Fetching all localized notifications of type {}", notificationType.getName());
        return localizedNotificationRepository.getAllByType(notificationType);
    }

    @Override
    public LocalizedNotification addNewLocalizedNotification(LocalizedNotification localizedNotification) {
        log.info("Saving localized notification {} on the database", localizedNotification.toString());
        return localizedNotificationRepository.save(localizedNotification);
    }

    @Override
    public List<LocalizedNotification> addAllLocalizedNotifications(List<LocalizedNotification> localizedNotifications) {
        log.info("Saving {} localized notifications on the database", localizedNotifications.size());
        return localizedNotificationRepository.saveAll(localizedNotifications);
    }

    @Override
    public LocalizedNotification updateLocalizedNotification(LocalizedNotification localizedNotification) {
        log.info("Updating localized notification with id={}", localizedNotification.getId());
        return localizedNotificationRepository.save(localizedNotification);
    }

    @Override
    public void deleteLocalizedNotification(LocalizedNotification localizedNotification) {
        log.info("Deleting notification with id={}", localizedNotification.getId());
        localizedNotificationRepository.delete(localizedNotification);
    }
}
