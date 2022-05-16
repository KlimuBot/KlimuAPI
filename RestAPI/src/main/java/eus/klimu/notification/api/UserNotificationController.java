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

@Controller
@RequestMapping("/user-notification")
@RequiredArgsConstructor
public class UserNotificationController {

    private final ChannelService channelService;
    private final LocationService locationService;
    private final UserNotificationService userNotificationService;
    private final NotificationTypeService notificationTypeService;
    private final LocalizedNotificationService localizedNotificationService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserNotification> getUserNotification(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(userNotificationService.getUserNotification(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<UserNotification>> getUserNotifications() {
        return ResponseEntity.ok().body(userNotificationService.getUserNotifications());
    }

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

    @GetMapping(
            value = "/all/channel",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<UserNotification>> getUserNotificationsByChannel(@RequestBody List<ChannelDTO> channels) {
        if (channels != null && !channels.isEmpty()) {
            List<Channel> persistentChannels = new ArrayList<>();
            channels.forEach(c -> {
                if (channelService.getChannel(c.getName()) != null) {
                    persistentChannels.add(Channel.generateChannel(c));
                }
            });
            return ResponseEntity.ok().body(userNotificationService.getUserNotificationsByChannel(persistentChannels));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

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

    @GetMapping(
            value = "/all/notification",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<UserNotification>> getUserNotificationsByNotification(
            @RequestBody List<LocalizedNotificationDTO> notifications
    ) {
        if (notifications != null && !notifications.isEmpty()) {
            List<LocalizedNotification> persistentLocalizedNotifications = LocalizedNotification.generateLocalizedNotifications(
                    notifications, localizedNotificationService, notificationTypeService, locationService
            );
            if (!persistentLocalizedNotifications.isEmpty()) {
                return ResponseEntity.ok().body(userNotificationService
                        .getUserNotificationsByNotification(persistentLocalizedNotifications));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

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
