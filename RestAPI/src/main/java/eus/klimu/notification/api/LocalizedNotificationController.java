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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Create, update, delete and get information for the Localized Notifications. They represent the different
 * notifications of a specific type on a specific location. Takes care of all the CRUD methods, working with the
 * database through an internal Service.
 */
@Controller
@RequestMapping("/localized-notification")
@RequiredArgsConstructor
public class LocalizedNotificationController {

    /**
     * A class that allows modifying the localized notifications.
     */
    private final LocalizedNotificationService localizedNotificationService;
    /**
     * A class that allows modifying the notification types.
     */
    private final NotificationTypeService notificationTypeService;
    /**
     * A class that allows modifying the locations.
     */
    private final LocationService locationService;

    /**
     * <p>Get a localized notification based on it's ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/{id}">https://klimu.eus/RestAPI/localized-notification/{id}</a></p>
     *
     * <ul>
     *     <li>Consumes: text/plain</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the localized notification the function is going to look for.
     * @return A 200 ok with the localized notification as a JSON if found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get a localized notification based on it's location and it's notification type.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification">https://klimu.eus/RestAPI/localized-notification</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param location A Data Transfer Object of a location.
     * @param notificationType A Data Transfer Object of a notification type.
     * @return A 200 ok with the localized notification as a JSON if found or a 4000 bad request if it wasn't.
     */
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

    /**
     * <p>Get all the localized notifications from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/all">https://klimu.eus/RestAPI/localized-notification/all</a></p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @return A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.
     */
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<LocalizedNotification>> getLocalizedNotifications() {
        return ResponseEntity.ok().body(localizedNotificationService.getAllLocalizedNotifications());
    }

    /**
     * <p>Get all the localized notifications by location from the database.</p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/all/location">https://klimu.eus/RestAPI/localized-notification/all/location</a></p>
     *
     * @param location A Data Transfer Object of a location.
     * @return A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get all the localized notifications by location from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/all/type">https://klimu.eus/RestAPI/localized-notification/all/type</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param notificationType A Data Transfer Object of a notification type.
     * @return A 200 ok with the channel as a JSON if found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Save a new localized notification on the database, once it's added, an ID will be added to that
     * localized notification.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/create">https://klimu.eus/RestAPI/localized-notification/create</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param localizedNotification A Data Transfer Object of the localized notification.
     * @return A 201 created with the channel as a JSON if found or a 400 bad request if it wasn't.
     */
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
            ) == null) {
                return ResponseEntity.created(
                        URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/localized-notification/create").toUriString())
                ).body(localizedNotificationService.addNewLocalizedNotification(persistentLocalizedNotification));
            } else {
                return ResponseEntity.ok().body(localizedNotificationService.getLocalizedNotification(
                        persistentLocalizedNotification.getLocation(), persistentLocalizedNotification.getType()
                ));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * <p>Save an X amount of new localized notification to the database, once they are added, an ID will be added to
     * those localized notifications.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/create/all">https://klimu.eus/RestAPI/localized-notification/create/all</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param localizedNotifications A list of Data Transfer Objects for the localized notifications that are going
     *                               to be created.
     * @return A 201 created with the localized notifications as a JSON if created or a 400 bad request if they weren't.
     */
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

    /**
     * <p>Modify an existing channel on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/update">https://klimu.eus/RestAPI/localized-notification/update</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param localizedNotification A Data Transfer Object for the localized notification that is going to be updated.
     * @return A 200 ok with the localized notification as a JSON if updated or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Delete a channel from the database based on it's ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/channel/delete/{id}">https://klimu.eus/RestAPI/channel/delete/{id}</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id A Localized Notification Data Transfer Object for the localized notification that is going to be deleted.
     * @return A 200 ok with the localized notification was deleted or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Delete a channel from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/localized-notification/delete">https://klimu.eus/RestAPI/localized-notification/delete</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param localizedNotification A Channel Data Transfer Object for the localized notification that is going
     *                              to be deleted.
     * @return A 200 ok with the localized notification was deleted or a 400 bad request if it wasn't.
     */
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
