package eus.klimu.notification.domain.model;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The information about a notification type on a specific location at a specific date.
 * Contains the information that is going to be sent to the users.
 */
@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Notification {

    /**
     * The identification number of the notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The message of the notification.
     */
    private String message;
    /**
     * The date when the notification was created.
     */
    private Date date;
    /**
     * The type of the notification.
     */
    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType type;
    /**
     * The location the notification was generated on.
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     * Transform a notification Data Transfer Object into a notification.
     * @param notificationDTO The notification Data Transfer Object.
     * @param notificationTypeService The managing service for the notification types.
     * @param locationService The managing service for the location.
     * @return The notification Data Transfer Object as a notification.
     */
    public static Notification generateNotification(
            NotificationDTO notificationDTO, NotificationTypeService notificationTypeService, LocationService locationService
    ) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        try {
            return new Notification(
                    notificationDTO.getId(), notificationDTO.getMessage(), dateFormat.parse(notificationDTO.getDate()),
                    notificationTypeService.getNotificationType(notificationDTO.getNotificationTypeId()),
                    locationService.getLocationById(notificationDTO.getLocationId())
            );
        } catch (ParseException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
