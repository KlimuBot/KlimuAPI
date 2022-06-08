package eus.klimu.users.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.service.definition.ChannelService;
import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import eus.klimu.notification.domain.service.definition.UserNotificationService;
import eus.klimu.security.TokenManagement;
import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.model.AppUserDTO;
import eus.klimu.users.domain.service.definition.RoleService;
import eus.klimu.users.domain.service.definition.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create, update, delete and get information for the AppUsers. They save the information about the users of the
 * application. Takes care of all the CRUD methods, working with the database through an internal Service.
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final String ERROR_HEADER = "errorMsg";

    private final UserService userService;
    private final RoleService roleService;
    private final ChannelService channelService;
    private final LocationService locationService;
    private final UserNotificationService userNotificationService;
    private final NotificationTypeService notificationTypeService;
    private final LocalizedNotificationService localizedNotificationService;
    private final TokenManagement tokenManagement = new TokenManagement();

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUser(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(userService.getUser(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/username/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
        if (username != null) {
            return ResponseEntity.ok().body(userService.getUser(username));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/chatId/{chatId}")
    public ResponseEntity<AppUser> getUserByChatId(@PathVariable String chatId) {
        if (chatId != null) {
            AppUser user = userService.getUserFromTelegram(chatId);

            if (user != null) {
                return ResponseEntity.ok().body(user);
            }
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/from-token/{token}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUserFromToken(@PathVariable String token) {
        try {
            String username = tokenManagement.getUserFromToken(token).getUsername();
            AppUser user = userService.getUser(username);

            if (user != null) {
                return ResponseEntity.ok().body(user);
            }
        } catch (JWTVerificationException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/{locationId}/{typeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<AppUser>> getUsersWithNotifications(
            @PathVariable Long locationId,
            @PathVariable Long typeId
    ) {
        List<AppUser> users = userService.findAll();
        LocalizedNotification ln = localizedNotificationService.getLocalizedNotification(
                locationService.getLocationById(locationId),
                notificationTypeService.getNotificationType(typeId)
        );

        List<AppUser> notifiedUsers = new ArrayList<>();
        users.forEach(user -> user.getNotifications().forEach(userNotification -> {
            if (!notifiedUsers.contains(user) && userNotification.getNotifications().contains(ln)) {
                notifiedUsers.add(user);
            }
        }));
        return ResponseEntity.ok().body(notifiedUsers);
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<AppUser> createUser(@RequestBody AppUserDTO user) {
        AppUser newUser = AppUser.generateAppUser(user, roleService, userNotificationService);
        String msg = userService.checkUser(newUser);

        if (msg.equals("OK")) {
            newUser = userService.saveUser(newUser);
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/create").toUriString())
            ).body(newUser);
        } else {
            return ResponseEntity.badRequest().header(ERROR_HEADER, msg).build();
        }
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<AppUser>> createAllUsers(@RequestBody List<AppUser> users) {
        String msg;

        for (AppUser user : users) {
            msg = userService.checkUser(user);
            if (!msg.equals("OK")) {
                return ResponseEntity.badRequest()
                        .header(ERROR_HEADER, user.getUsername() + " => " + msg).build();
            }
        }
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/create/all").toUriString())
        ).body(userService.saveAllUsers(users));
    }

    @PostMapping("/add/{chatId}/{channel}/{locationId}/{typeId}")
    public ResponseEntity<Object> addLocalizedAlertToUser(
            @PathVariable String chatId,
            @PathVariable String channel,
            @PathVariable long locationId,
            @PathVariable long typeId
    ) {
        Location location = locationService.getLocationById(locationId);
        NotificationType type = notificationTypeService.getNotificationType(typeId);
        AppUser user = userService.getUserFromTelegram(chatId);

        if (location != null && type != null && user != null) {
            LocalizedNotification localizedNotification = localizedNotificationService
                    .addNewLocalizedNotification(new LocalizedNotification(null, type, location));

            user.getNotifications().forEach(userNotification -> {
                if (userNotification.getChannel().getName().equalsIgnoreCase(channel)) {
                    userNotification.getNotifications().add(localizedNotification);
                    userNotificationService.updateUserNotification(userNotification);
                }
            });
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping(
            value = "/set/chatId/{username}/{chatId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<AppUser> setChatId(
            @PathVariable String username,
            @PathVariable String chatId
    ) {
        AppUser user = userService.getUser(username);

        if (user != null && userService.getUserFromTelegram(chatId) == null) {
            user.setTelegramId(chatId);
            return ResponseEntity.ok().body(userService.updateUser(user));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUserDTO user) {
        AppUser appUser = AppUser.generateAppUser(user, roleService, userNotificationService);
        String msg = userService.checkUser(appUser);

        if (!msg.equals("OK")) {
            return ResponseEntity.badRequest().header(ERROR_HEADER, msg).build();
        }
        return ResponseEntity.ok().body(userService.updateUser(appUser));
    }

    @PutMapping(value = "/remove/{username}/{channelName}/{notificationId}")
    public ResponseEntity<AppUser> removeLocalizedNotificationFromUser(
            @PathVariable String username,
            @PathVariable String channelName,
            @PathVariable long notificationId
    ) {
        AppUser user = userService.getUser(username);

        if (user != null) {
            LocalizedNotification notification = localizedNotificationService.getLocalizedNotification(notificationId);
            Channel channel = channelService.getChannel(channelName);

            if (notification != null && channel != null) {
                // Remove the notification from the user notification for that channel.
                user.getNotifications().forEach(userNotification -> {
                    if (userNotification.getChannel().getName().equals(channel.getName())) {
                        Collection<LocalizedNotification> lnList = userNotification.getNotifications();

                        lnList.removeIf(ln -> ln.getId().equals(notification.getId()));
                        userNotification.setNotifications(lnList);

                        userNotificationService.updateUserNotification(userNotification);
                    }
                });
                return ResponseEntity.ok().body(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable int id) {
        if (id > 0) {
            userService.deleteUser(userService.getUser(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteUser(@RequestBody AppUserDTO user) {
        if (user != null && userService.getUser(user.getId()) != null) {
            userService.deleteUser(AppUser.generateAppUser(user, roleService, userNotificationService));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
