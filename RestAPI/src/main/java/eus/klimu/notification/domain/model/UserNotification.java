package eus.klimu.notification.domain.model;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.service.definition.ChannelService;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_notification")
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    @ManyToMany
    private Collection<LocalizedNotification> notifications = new ArrayList<>();

    public static UserNotification generateUserNotification(
            UserNotificationDTO userNotificationDTO, ChannelService channelService,
            LocalizedNotificationService localizedNotificationService
    ) {
        Collection<LocalizedNotification> localizedNotifications = new ArrayList<>();
        userNotificationDTO.getLocalizedNotifications().forEach(
                localizedNotificationId -> localizedNotifications.add(
                        localizedNotificationService.getLocalizedNotification(localizedNotificationId))
        );
        return new UserNotification(userNotificationDTO.getId(),
                channelService.getChannel(userNotificationDTO.getChannelId()), localizedNotifications);
    }

}
