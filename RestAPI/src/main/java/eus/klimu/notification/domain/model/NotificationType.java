package eus.klimu.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "notification_type")
public class NotificationType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String type; // info, warning, danger, etc.

    public static NotificationType generateNotificationType(NotificationTypeDTO notificationTypeDTO) {
        return new NotificationType(
                notificationTypeDTO.getId(), notificationTypeDTO.getName(),
                notificationTypeDTO.getDescription(), notificationTypeDTO.getType()
        );
    }

}
