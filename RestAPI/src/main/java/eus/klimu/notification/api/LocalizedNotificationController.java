package eus.klimu.notification.api;

import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.model.LocationDTO;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.LocalizedNotificationDTO;
import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.model.NotificationTypeDTO;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/localized-notification")
@RequiredArgsConstructor
public class LocalizedNotificationController {

    private final LocalizedNotificationService localizedNotificationService;
    private final NotificationTypeService notificationTypeService;
    private final LocationService locationService;

    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<LocalizedNotification> getLocalizedNotification(@PathVariable long id) {
        if (id > 0) {
            LocalizedNotification ln = localizedNotificationService.getLocalizedNotification(id);
            if (ln != null) {
                return ResponseEntity.ok().body(ln);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<LocalizedNotification> getLocalizedNotification(
            @RequestBody LocationDTO location, @RequestBody NotificationTypeDTO notificationType
    ) {
        if (location != null && notificationType != null) {
            LocalizedNotification notification = localizedNotificationService.getLocalizedNotification(
                    Location.generateLocation(location), NotificationType.generateNotificationType(notificationType)
            );
            if (notification != null) {
                return ResponseEntity.ok().body(notification);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<LocalizedNotification>> getLocalizedNotifications() {
        return ResponseEntity.ok().body(localizedNotificationService.getAllLocalizedNotifications());
    }

    @GetMapping(
            value = "/all/location",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<LocalizedNotification>> getLocalizedNotifications(@RequestBody LocationDTO location) {
        if (location != null) {
            return ResponseEntity.ok().body(
                    localizedNotificationService.getAllLocalizedNotifications(Location.generateLocation(location)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/all/type",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<LocalizedNotification>> getLocalizedNotifications(
            @RequestBody NotificationTypeDTO notificationType
    ) {
        if (notificationType != null) {
            return ResponseEntity.ok().body(localizedNotificationService
                    .getAllLocalizedNotifications(NotificationType.generateNotificationType(notificationType)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<LocalizedNotification> createLocalizedNotification(
            @RequestBody LocalizedNotificationDTO localizedNotification
    ) {
        if (localizedNotification != null) {
            LocalizedNotification persistentLocalizedNotification = LocalizedNotification.generateLocalizedNotification(
                    localizedNotification, notificationTypeService, locationService
            );
            if (localizedNotificationService.getLocalizedNotification(
                    persistentLocalizedNotification.getLocation(), persistentLocalizedNotification.getType()
            ) != null) {
                return ResponseEntity.ok().body(localizedNotificationService
                        .addNewLocalizedNotification(persistentLocalizedNotification));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<LocalizedNotification>> createLocalizedNotifications(
            @RequestBody List<LocalizedNotificationDTO> localizedNotifications
    ) {
        if (localizedNotifications != null && !localizedNotifications.isEmpty()) {
            List<LocalizedNotification> persistentLocalizedNotifications = LocalizedNotification.generateLocalizedNotifications(
                    localizedNotifications, localizedNotificationService, notificationTypeService, locationService
            );
            if (!persistentLocalizedNotifications.isEmpty()) {
                return ResponseEntity.ok().body(localizedNotificationService
                        .addAllLocalizedNotifications(persistentLocalizedNotifications));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<LocalizedNotification> updateLocalizedNotification(@RequestBody LocalizedNotificationDTO localizedNotification) {
        if (localizedNotification != null && localizedNotificationService
                .getLocalizedNotification(localizedNotification.getId()) != null
        ) {
            return ResponseEntity.ok().body(localizedNotificationService.updateLocalizedNotification(
                    LocalizedNotification.generateLocalizedNotification(localizedNotification, notificationTypeService, locationService)
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteLocalizedNotification(@PathVariable long id) {
        if (id > 0 && localizedNotificationService.getLocalizedNotification(id) != null) {
            localizedNotificationService.deleteLocalizedNotification(
                    localizedNotificationService.getLocalizedNotification(id)
            );
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(
            value = "/delete",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Object> deleteLocalizedNotification(@RequestBody LocalizedNotificationDTO localizedNotification) {
        if (localizedNotification != null && localizedNotificationService
                .getLocalizedNotification(localizedNotification.getId()) != null) {
            localizedNotificationService.deleteLocalizedNotification(LocalizedNotification.generateLocalizedNotification(
                    localizedNotification, notificationTypeService, locationService
            ));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
