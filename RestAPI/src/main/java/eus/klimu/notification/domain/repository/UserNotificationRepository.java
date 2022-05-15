package eus.klimu.notification.domain.repository;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.notification.domain.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

    List<UserNotification> getAllByChannel(Channel channel);

}
