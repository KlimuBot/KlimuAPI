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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserNotificationServiceImp implements UserNotificationService {

    private final UserNotificationRepository userNotificationRepository;

    @Override
    public UserNotification getUserNotification(long id) {
        log.info("Fetching user notification with id={}", id);
        return userNotificationRepository.getById(id);
    }

    @Override
    public List<UserNotification> getUserNotifications() {
        log.info("Fetching all user notifications from database");
        return userNotificationRepository.findAll();
    }

    @Override
    public List<UserNotification> getUserNotificationsByChannel(Channel channel) {
        log.info("Fetching user notifications with channel={}", channel.getName());
        return userNotificationRepository.getAllByChannel(channel);
    }

    @Override
    public List<UserNotification> getUserNotificationsByChannel(List<Channel> channels) {
        log.info("Fetching user notifications for {} channel(s)", channels.size());
        return userNotificationRepository.getAllByChannel(channels);
    }

    @Override
    public List<UserNotification> getUserNotificationsByNotification(LocalizedNotification localizedNotification) {
        log.info("Fetching user notifications with localized notification for {}", localizedNotification.toString());
        return userNotificationRepository.getAllByNotifications(localizedNotification);
    }

    @Override
    public List<UserNotification> getUserNotificationsByNotification(List<LocalizedNotification> localizedNotifications) {
        log.info("Fetching user notifications for {} localized notification(s)", localizedNotifications.size());
        return userNotificationRepository.getAllByNotifications(localizedNotifications);
    }

    @Override
    public UserNotification addNewUserNotification(UserNotification userNotification) {
        log.info("Saving a user notification on the database");
        return userNotificationRepository.save(userNotification);
    }

    @Override
    public List<UserNotification> addAllUserNotifications(List<UserNotification> userNotifications) {
        log.info("Saving {} user notifications on the database", userNotifications.size());
        return userNotificationRepository.saveAll(userNotifications);
    }

    @Override
    public UserNotification updateUserNotification(UserNotification userNotification) {
        log.info("Updating user notification with id={}", userNotification);
        return userNotificationRepository.save(userNotification);
    }

    @Override
    public void deleteUserNotifications(UserNotification userNotification) {
        log.info("Deleting user notification with id={}", userNotification.getId());
        userNotificationRepository.delete(userNotification);
    }
}
