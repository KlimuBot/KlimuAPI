package eus.klimu.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "notification_type")
public class NotificationType {

    /**
     * The identification number of the notification type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The name of the notification type.
     */
    @Column(unique = true)
    private String name;
    /**
     * The description about the notification type.
     */
    private String description;
    /**
     * The type of notification, indicates the danger level of it,
     * can be of type Information, Warning or Dangerous.
     */
    private String type;

    /**
     * Generate a notification type from a notification type Data Transfer Object.
     * @param notificationTypeDTO The notification type Data Transfer Object.
     * @return A notification type generated from the Data Transfer Object.
     */
    public static NotificationType generateNotificationType(NotificationTypeDTO notificationTypeDTO) {
        return new NotificationType(
                notificationTypeDTO.getId(), notificationTypeDTO.getName(),
                notificationTypeDTO.getDescription(), notificationTypeDTO.getType()
        );
    }

}
