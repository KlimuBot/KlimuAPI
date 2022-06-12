package eus.klimu.notification.domain.model;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.service.definition.ChannelService;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The configuration of the notifications that a user wants to receive for a specific channel.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "user_notification")
public class UserNotification {

    /**
     * The identification number of the user notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The channel the notification is for.
     */
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;
    /**
     * The list of localized notifications that the user wants to receive on that channel.
     */
    @ManyToMany
    private Collection<LocalizedNotification> notifications = new ArrayList<>();

    /**
     * Generate a user notification from a user notification Data Transfer Object.
     * @param userNotificationDTO The user notification Data Transfer Object.
     * @param channelService The channel managing service.
     * @param localizedNotificationService The localized notification managing service.
     * @return A user notification generated from a Data Transfer Object.
     */
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
