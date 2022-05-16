package eus.klimu.notification.domain.model;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        return type.getName() + " [" + location.toString() + "]";
    }

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

    public static List<LocalizedNotification> generateLocalizedNotifications(
            List<LocalizedNotificationDTO> localizedNotifications,
            LocalizedNotificationService localizedNotificationService,
            NotificationTypeService notificationTypeService,
            LocationService locationService
    ) {
        List<LocalizedNotification> persistentLocalizedNotifications = new ArrayList<>();
        localizedNotifications.forEach(ln -> {
            LocalizedNotification persistentLN = LocalizedNotification
                    .generateLocalizedNotification(ln, notificationTypeService, locationService);

            if (localizedNotificationService.getLocalizedNotification(
                    persistentLN.getLocation(), persistentLN.getType()) != null
            ) {
                persistentLocalizedNotifications.add(persistentLN);
            }
        });
        return persistentLocalizedNotifications;
    }
}
