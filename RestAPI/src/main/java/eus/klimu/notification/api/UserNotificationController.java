package eus.klimu.notification.api;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.model.ChannelDTO;
import eus.klimu.channel.domain.service.definition.ChannelService;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.model.*;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import eus.klimu.notification.domain.service.definition.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Create, update, delete and get information for the UserNotifications. They represent the different notifications a
 * user has configured for a specific channel. Takes care of all the CRUD methods, working with the database through
 * an internal Service.
 */
@Controller
@RequestMapping("/user-notification")
@RequiredArgsConstructor
public class UserNotificationController {

    /**
     * A class that allows modifying the channels.
     */
    private final ChannelService channelService;
    /**
     * A class that allows modifying the locations.
     */
    private final LocationService locationService;
    /**
     * A class that allows modifying the user notifications.
     */
    private final UserNotificationService userNotificationService;
    /**
     * A class that allows modifying the notification types.
     */
    private final NotificationTypeService notificationTypeService;
    /**
     * A class that allows modifying the localized notifications.
     */
    private final LocalizedNotificationService localizedNotificationService;

    /**
     * <p>Get a user notification based on its ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/{id}">https://klimu.eus/RestAPI/user-notification/{id}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the user notification that is going to be fetched.
     * @return A 200 ok if the AppUser was found or a 400 bad request if it wasn't.
     */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserNotification> getUserNotification(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(userNotificationService.getUserNotification(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Get all the user notifications from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/all">https://klimu.eus/RestAPI/user-notification/all</a></p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @return A 200 ok if the User Notifications were found or a 400 bad request if it wasn't.
     */
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserNotification>> getUserNotifications() {
        return ResponseEntity.ok().body(userNotificationService.getUserNotifications());
    }

    /**
     * <p>Get all the user notifications for an specific channel.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/all/channel">https://klimu.eus/RestAPI/user-notification/all/channel</a></p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param channel The channel that is going to be used on the query.
     * @return A 200 ok if the User Notifications were found or a 400 bad request if it wasn't.
     */
    @GetMapping(
            value = "/all/channel",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<UserNotification>> getUserNotificationsByChannel(@RequestBody ChannelDTO channel) {
        if (channel != null && channelService.getChannel(channel.getName()) != null) {
            return ResponseEntity.ok().body(userNotificationService.getUserNotificationsByChannel(
                    Channel.generateChannel(channel)
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Get all the user notifications for an specific localized notification.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/all/notification">https://klimu.eus/RestAPI/user-notification/all/notification</a></p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param notification The localized notification that is going to be used on the query.
     * @return A 200 ok if the User Notifications were found or a 400 bad request if it wasn't.
     */
    @GetMapping(
            value = "/all/notification",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<UserNotification>> getUserNotificationsByNotification(
            @RequestBody LocalizedNotificationDTO notification
    ) {
        if (notification != null) {
            LocalizedNotification persistentNotification = LocalizedNotification.generateLocalizedNotification(
                    notification, notificationTypeService, locationService
            );
            if (localizedNotificationService.getLocalizedNotification(
                    persistentNotification.getLocation(), persistentNotification.getType()) != null)
            {
                return ResponseEntity.ok().body(userNotificationService
                        .getUserNotificationsByNotification(persistentNotification));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * <p>Save a new user notification on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/create">https://klimu.eus/RestAPI/user-notification/create</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param userNotification The User Notification that is going to be created.
     * @return A 200 ok if the User Notification was created or a 400 bad request if it wasn't.
     */
    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<UserNotification> createUserNotification(@RequestBody UserNotificationDTO userNotification) {
        if (userNotification != null) {
            return ResponseEntity.ok().body(userNotificationService.addNewUserNotification(
                    UserNotification.generateUserNotification(userNotification, channelService, localizedNotificationService)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Save an X amount of user notifications on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/create/all">https://klimu.eus/RestAPI/user-notification/create/all</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param userNotifications The User Notifications that are going to be created.
     * @return A 200 ok if the User Notifications were created or a 400 bad request if it wasn't.
     */
    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<UserNotification>> createAllUserNotifications(
            @RequestBody List<UserNotificationDTO> userNotifications
    ) {
        if (userNotifications != null && !userNotifications.isEmpty()) {
            List<UserNotification> persistentUserNotifications = new ArrayList<>();
            userNotifications.forEach(un -> persistentUserNotifications.add(
                    UserNotification.generateUserNotification(un, channelService, localizedNotificationService)
            ));
            if (!persistentUserNotifications.isEmpty()) {
                return ResponseEntity.ok().body(userNotificationService.addAllUserNotifications(persistentUserNotifications));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * <p>Update a user notification on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/update">https://klimu.eus/RestAPI/user-notification/update</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param userNotification The User Notification that is going to be updated.
     * @return A 200 ok if the User Notification was updated or a 400 bad request if it wasn't.
     */
    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<UserNotification> updateUserNotification(@RequestBody UserNotificationDTO userNotification) {
        if (userNotification != null && userNotificationService.getUserNotification(userNotification.getId()) != null) {
            return ResponseEntity.ok().body(userNotificationService.updateUserNotification(
                    UserNotification.generateUserNotification(userNotification, channelService, localizedNotificationService)
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete a user notification based on its ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/delete/{id}">https://klimu.eus/RestAPI/user-notification/delete/{id}</a></p>
     *
     * <ul>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the User Notification that is going to be deleted.
     * @return A 200 ok if the User Notification was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteUserNotification(@PathVariable long id) {
        if (id > 0 && userNotificationService.getUserNotification(id) != null) {
            userNotificationService.deleteUserNotifications(
                    userNotificationService.getUserNotification(id)
            );
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete a user notification from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user-notification/delete">https://klimu.eus/RestAPI/user-notification/delete</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param userNotification The User Notification that is going to be deleted.
     * @return A 200 ok if the User Notification was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteUserNotification(@RequestBody UserNotificationDTO userNotification) {
        if (userNotification != null && userNotificationService.getUserNotification(userNotification.getId()) != null) {
            userNotificationService.deleteUserNotifications(
                    UserNotification.generateUserNotification(userNotification, channelService, localizedNotificationService)
            );
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
