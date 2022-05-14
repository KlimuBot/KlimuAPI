package eus.klimu.notification.domain.model;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "localized_notification")
public class LocalizedNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType type;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public static LocalizedNotification generateLocalizedNotification(
            LocalizedNotificationDTO localizedNotificationDTO, NotificationTypeService notificationTypeService,
            LocationService locationService
    ) {
        return new LocalizedNotification(
                localizedNotificationDTO.getId(),
                notificationTypeService.getNotificationType(localizedNotificationDTO.getNotificationTypeId()),
                locationService.getLocationById(localizedNotificationDTO.getLocationId())
        );
    }

}
