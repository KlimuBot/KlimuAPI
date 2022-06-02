package eus.klimu.notification.api;

import com.google.gson.Gson;
import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.model.LocationDTO;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.model.*;
import eus.klimu.notification.domain.service.definition.NotificationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationTypeService notificationTypeService;
    private final NotificationService notificationService;
    private final LocationService locationService;
    private final Gson gson = new Gson();

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Notification> getNotification(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(notificationService.getNotificationById(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/location",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Notification>> getNotifications(@RequestBody LocationDTO location) {
        if (location != null) {
            return ResponseEntity.ok().body(
                    notificationService.getAllNotifications(Location.generateLocation(location)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/type",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Notification>> getNotifications(@RequestBody NotificationTypeDTO notificationType) {
        if (notificationType != null) {
            return ResponseEntity.ok().body(
                    notificationService.getAllNotifications(NotificationType.generateNotificationType(notificationType)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/date/location",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Notification>> getNotifications(
            @RequestBody LocationDTO location,
            @RequestBody Date startDate,
            @RequestBody Date endDate
    ) {
        DatePeriod datePeriod = null;

        if (startDate != null && endDate != null) {
            datePeriod = new DatePeriod(startDate, endDate);
        }
        if (location != null && datePeriod != null && datePeriod.check()) {
            return ResponseEntity.ok().body(notificationService.getNotificationsByDateBetween(
                    Location.generateLocation(location), datePeriod.getStartDate(), datePeriod.getEndDate()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/date/type",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Notification>> getNotifications(
            @RequestBody NotificationTypeDTO notificationType,
            @RequestBody Date startDate,
            @RequestBody Date endDate
    ) {
        DatePeriod datePeriod = null;

        if (startDate != null && endDate != null) {
            datePeriod = new DatePeriod(startDate, endDate);
        }
        if (notificationType != null && datePeriod != null && datePeriod.check()) {
            return ResponseEntity.ok().body(notificationService.getNotificationsByDateBetween(
                    NotificationType.generateNotificationType(notificationType),
                    datePeriod.getStartDate(), datePeriod.getEndDate()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Notification>> getNotifications(@RequestBody String dates) {
        DatePeriod datePeriod = null;

        if (dates != null) {
            JSONObject json = new JSONObject(dates);
            datePeriod = new DatePeriod(
                    gson.fromJson(json.getString("startDate"), Date.class),
                    gson.fromJson(json.getString("endDate"), Date.class)
            );
        }
        if (datePeriod != null && datePeriod.check()) {
            return ResponseEntity.ok().body(notificationService.getAllNotifications(
                    datePeriod.getStartDate(), datePeriod.getEndDate()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationDTO notification) {
        if (notification != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/notification/create").toUriString())
            ).body(notificationService.addNewNotification(Notification.generateNotification(
                    notification, notificationTypeService, locationService
            )));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Notification>> createAllNotifications(@RequestBody List<NotificationDTO> notifications) {
        if (notifications != null && !notifications.isEmpty()) {
            List<Notification> persistentNotifications = new ArrayList<>();
            notifications.forEach(n -> persistentNotifications.add(Notification.generateNotification(
                    n, notificationTypeService, locationService
            )));

            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/notification/create/all").toUriString())
            ).body(notificationService.addAllNotifications(persistentNotifications));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Notification> updateNotification(@RequestBody NotificationDTO notification) {
        if (notification != null && notificationService.getNotificationById(notification.getId()) != null) {
            return ResponseEntity.ok().body(notificationService.updateNotification(Notification.generateNotification(
                    notification, notificationTypeService, locationService
            )));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteNotification(@PathVariable long id) {
        if (id > 0) {
            notificationService.deleteNotification(notificationService.getNotificationById(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteNotification(@RequestBody NotificationDTO notification) {
        if (notification != null && notificationService.getNotificationById(notification.getId()) != null) {
            notificationService.deleteNotification(Notification.generateNotification(
                    notification, notificationTypeService, locationService
            ));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
