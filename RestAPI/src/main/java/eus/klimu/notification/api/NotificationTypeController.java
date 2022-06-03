package eus.klimu.notification.api;

import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.model.NotificationTypeDTO;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Create, update, delete and get information for the Notification Types. They represent the different notifications
 * that can be generated. Takes care of all the CRUD methods, working with the database through an internal Service.
 */
@Controller
@RequestMapping("/notification-type")
@RequiredArgsConstructor
public class NotificationTypeController {

    private final NotificationTypeService notificationTypeService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<NotificationType> getNotificationType(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(notificationTypeService.getNotificationType(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/name/{name}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<NotificationType> getNotificationTypeByName(@PathVariable String name) {
        if (name != null) {
            return ResponseEntity.ok().body(notificationTypeService.getNotificationType(name));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<NotificationType>> getNotifications() {
        return ResponseEntity.ok().body(notificationTypeService.getAllNotificationTypes());
    }

    @GetMapping(value = "/all/{type}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<NotificationType>> getNotifications(@PathVariable String type) {
        if (type != null) {
            return ResponseEntity.ok().body(notificationTypeService.getAllNotificationTypes(type));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<NotificationType> createNotificationType(@RequestBody NotificationTypeDTO notificationType) {
        if (notificationType != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/notification-type/create").toUriString())
            ).body(notificationTypeService.addNewNotificationType(NotificationType.generateNotificationType(notificationType)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<NotificationType>> createAllNotificationTypes(
            @RequestBody List<NotificationTypeDTO> notificationTypes)
    {
        if (notificationTypes != null && !notificationTypes.isEmpty()) {
            List<NotificationType> persistentNotificationTypes = new ArrayList<>();
            notificationTypes.forEach(n -> persistentNotificationTypes.add(NotificationType.generateNotificationType(n)));

            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/notification/create/all").toUriString())
            ).body(notificationTypeService.addAllNotificationTypes(persistentNotificationTypes));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<NotificationType> updateNotificationType(@RequestBody NotificationTypeDTO notificationType) {
        if (notificationType != null && notificationTypeService.getNotificationType(notificationType.getId()) != null) {
            return ResponseEntity.ok().body(notificationTypeService.updateNotificationType(
                    NotificationType.generateNotificationType(notificationType)
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteNotificationType(@PathVariable long id) {
        NotificationType notificationType = notificationTypeService.getNotificationType(id);

        if (id > 0 && notificationType != null) {
            notificationTypeService.deleteNotificationType(notificationType);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteNotificationType(@RequestBody NotificationTypeDTO notificationType) {
        if (notificationType != null && notificationTypeService.getNotificationType(notificationType.getId()) != null) {
            notificationTypeService.deleteNotificationType(NotificationType.generateNotificationType(notificationType));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
