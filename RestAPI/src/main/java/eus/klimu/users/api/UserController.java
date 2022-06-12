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

    /**
     * The header for the error messages.
     */
    private static final String ERROR_HEADER = "errorMsg";

    /**
     * A class that allows modifying the AppUsers.
     */
    private final UserService userService;
    /**
     * A class that allows modifying the roles.
     */
    private final RoleService roleService;
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
     * A class that allows managing the authorization tokens of the user.
     */
    private final TokenManagement tokenManagement = new TokenManagement();

    /**
     * <p>Fetch an AppUser based on its ID.</p>
     *
     * <p><a hred="https://klimu.eus/RestAPI/user/{id}">https://klimu.eus/RestAPI/user/{id}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the AppUser that is going to be fetched.
     * @return A 200 ok if the AppUser was found or a 400 bad request if it wasn't.
     */
    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUser(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(userService.getUser(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Fetch an AppUser based on its username.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/username/{username}">https://klimu.eus/RestAPI/user/username/{username}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param username The username of the AppUser that is going to be fetched.
     * @return A 200 ok if the AppUser was found or a 400 bad request if it wasn't.
     */
    @GetMapping(value = "/username/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
        if (username != null) {
            return ResponseEntity.ok().body(userService.getUser(username));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Fetch an AppUser based on its telegramId.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/chatId/{chatId}">https://klimu.eus/RestAPI/user/chatId/{chatId}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param chatId The telegramId of the AppUser that is going to be fetched.
     * @return A 200 ok if the AppUser was found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get an AppUser from a token.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/from-token/{token}">https://klimu.eus/RestAPI/user/from-token/{token}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param token The token the AppUser that is going to be fetched from.
     * @return A 200 ok if the AppUser was generated or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get a list of AppUsers that have a localized notification configured.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/{locationId}/{typeId}">https://klimu.eus/RestAPI/user/{locationId}/{typeId}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param locationId The ID of the location that is going to be used on the query.
     * @param typeId The ID of the notification type that is going to be used on the query.
     * @return A 200 ok if the AppUsers were found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Save a new AppUser on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/create">https://klimu.eus/RestAPI/user/create</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param user The AppUser that is going to be created.
     * @return A 200 ok if the AppUser was created or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Save an X amount of AppUsers on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/create/all">https://klimu.eus/RestAPI/user/create/all</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param users The list of AppUsers that are going to be created.
     * @return A 200 ok if the AppUsers were created or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Add a new localized notification to an AppUser.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/add/{chatId}/{channel}/{locationId}/{typeId}">https://klimu.eus/RestAPI/user/add/{chatId}/{channel}/{locationId}/{typeId}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param chatId The telegramId of the user that is going to be updated.
     * @param channel The name of the channel that the notification is going to be created for.
     * @param locationId The ID of the location for the notification.
     * @param typeId The ID of the notification type for the notification.
     * @return A 200 ok if the localized notification was created or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Set the chat ID of a user based on the username.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/set/chatId/{username}/{chatId}">https://klimu.eus/RestAPI/user/set/chatId/{username}/{chatId}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param username The username of the AppUser that is going to be created.
     * @param chatId The telegramId of the AppUser that is going to be created.
     * @return A 200 ok if the relation was created, a 404 if the AppUser was not found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Update an AppUser on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/update">https://klimu.eus/RestAPI/user/update</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param user The AppUser that is going to be updated.
     * @return A 200 ok if the AppUser was updated or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Update an AppUser's localized notification list for an specific channel.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/remove/{username}/{channelName}/{notificationId}">https://klimu.eus/RestAPI/user/remove/{username}/{channelName}/{notificationId}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param username The username of the AppUser that is going to be updated.
     * @param channelName The name of the Channel that is going to be updated.
     * @param notificationId The id of the Localized Notification that is going to be removed from the user's list.
     * @return A 200 ok if the AppUser was updated or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Delete an AppUser based on its ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/delete/{id}">https://klimu.eus/RestAPI/user/delete/{id}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the AppUser that is going to be created.
     * @return A 200 ok if the AppUser was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable int id) {
        if (id > 0) {
            userService.deleteUser(userService.getUser(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete an AppUser from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/user/delete">https://klimu.eus/RestAPI/user/delete</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param user The AppUser that is going to be deleted.
     * @return A 200 ok if the AppUser was deleted or a 400 bad request if it wasn't.
     */
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
