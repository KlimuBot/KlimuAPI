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
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A notification type for a specific location.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "localized_notification")
public class LocalizedNotification {

    /**
     * The identification number of the localized notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The notification type of the localized notification.
     */
    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType type;
    /**
     * The location the localized notification is for.
     */
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    /**
     * Transform a localized notification to a String, displaying the name of the
     * notification type and the location.
     * @return A localized notification as a String.
     */
    @Override
    public String toString() {
        return type.getName() + " [" + location.toString() + "]";
    }

    /**
     * Transform a localized notification Data Transfer Object into a localized notification.
     * @param localizedNotificationDTO The Data Transfer Object for a localized notification.
     * @param notificationTypeService The managing service for notification types.
     * @param locationService The managing service for locations.
     * @return A localized notification Data Transfer Object as a localized notification.
     */
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

    /**
     * Transform a list of localized notification Data Transfer Objects into localized notifications.
     * @param localizedNotifications A list of localized notification Data Transfer Objects.
     * @param localizedNotificationService The managing service for localized notifications.
     * @param notificationTypeService The managing service for notification types.
     * @param locationService The managing service for the locations.
     * @return A list of localized notification Data Transfer Objects as localized notifications.
     */
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
