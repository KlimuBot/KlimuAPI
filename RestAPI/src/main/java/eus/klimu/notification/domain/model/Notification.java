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

@Slf4j
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType type;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

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
