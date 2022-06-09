package eus.klimu;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.service.definition.ChannelService;
import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.model.LocalizedNotification;
import eus.klimu.notification.domain.model.Notification;
import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.model.UserNotification;
import eus.klimu.notification.domain.service.definition.LocalizedNotificationService;
import eus.klimu.notification.domain.service.definition.NotificationService;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
import eus.klimu.notification.domain.service.definition.UserNotificationService;
import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.model.Role;
import eus.klimu.users.domain.service.definition.RoleService;
import eus.klimu.users.domain.service.definition.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@SpringBootApplication
public class RestApiApplication {

    private static final String DANGER = "Dangerous";
    private static final String WARNING = "Warning";

    private static final String DEFAULT_PASSWORD = "12345678aA@";
    private static final String DEFAULT_NUMBER = "612345789";

    /**
     * Default roles for the application.
     */
    private static final List<Role> roles = Arrays.asList(
            new Role(null, "USER_ROLE"),
            new Role(null, "ADMIN_ROLE")
    );

    /**
     * Default users for the application.
     */
    private static final List<AppUser> appUsers = Arrays.asList(
            new AppUser(null, "klimu.user", "klimu@user", "klimu", "user",
                    "klimubot@gmail.com", "654321987", null,
                    new ArrayList<>(), new ArrayList<>()
            ),
            new AppUser(null, "klimu.admin", "klimu@admin", "klimu", "admin",
                    "popbl6_talde2-group@alumni.mondragon.edu", "654321987", null,
                    new ArrayList<>(), new ArrayList<>()
            ),
            new AppUser(null, "joseba.izaguirre", DEFAULT_PASSWORD, "Joseba", "Izaguirre",
                    "joseba.izaguirre@alumni.mondragon.edu", DEFAULT_NUMBER, null,
                    new ArrayList<>(), new ArrayList<>()
            ),
            new AppUser(null, "aitor.alzola", DEFAULT_PASSWORD, "Aitor", "Alzola",
                    "aitor.alzola@alumni.mondragon.edu", DEFAULT_NUMBER, null,
                    new ArrayList<>(), new ArrayList<>()
            ),
            new AppUser(null, "jon.navaridas", DEFAULT_PASSWORD, "Jon", "Navaridas",
                    "jon.navaridas@alumni.mondragon.edu", DEFAULT_NUMBER, null,
                    new ArrayList<>(), new ArrayList<>()
            ),
            new AppUser(null, "gorka.urzelai", DEFAULT_PASSWORD, "Gorka", "Urzelai",
                    "gorka.urzelai@alumni.mondragon.edu", DEFAULT_NUMBER, null,
                    new ArrayList<>(), new ArrayList<>()
            ),
            new AppUser(null, "peio.mena", DEFAULT_PASSWORD, "Peio", "Mena",
                    "peio.mena@alumni.mondragon.edu", DEFAULT_NUMBER, null,
                    new ArrayList<>(), new ArrayList<>()
            )
    );

    /**
     * Default channels for the application.
     */
    private static final List<Channel> channels = Arrays.asList(
            new Channel(null, "Email", null),
            new Channel(null, "Desktop", null),
            new Channel(null, "Telegram", null)
    );

    private static final List<NotificationType> notificationTypes = Arrays.asList(
            new NotificationType(null, "Earthquake",
                    "You will receive a notification if an earthquake is detected.", DANGER),
            new NotificationType(null, "Rain",
                    "You will receive a notification when the probability of rain is high.", WARNING),
            new NotificationType(null, "Heatwave",
                    "You will receive a notification when the temperature reaches a high value" +
                            "for more than five days.", DANGER),
            new NotificationType(null, "High Temperature",
                    "You will receive a notification when the temperature reaches a high value.", DANGER),
            new NotificationType(null, "Cold",
                    "You will receive a notification when the temperature reaches a low value.", WARNING),
            new NotificationType(null, "Snow",
                    "You will receive a notification when the temperature reaches a low value " +
                            "and the humidity is high.", WARNING)
    );

    @Value("classpath:files/country-by-capital-city.json")
    private File resourceFile;

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Generate the default elements for the application. The function checks if the elements have already been
     * created and if they exist in the database it won't create them.
     *
     * @param userService                  The service for managing the users.
     * @param roleService                  The service for managing the roles.
     * @param channelService               The service for managing the channels.
     * @param locationService              The service for managing the locations.
     * @param notificationTypeService      The service for managing the notification types.
     * @param userNotificationService      The service for managing the user notifications.
     * @param notificationService          The service for managing the notifications.
     * @param localizedNotificationService The service for managing the localized notifications.
     * @return A collection of functions that will be executed at the beginning of execution.
     */
    @Bean
    public CommandLineRunner run(
            UserService userService, RoleService roleService, ChannelService channelService,
            LocationService locationService, NotificationTypeService notificationTypeService,
            UserNotificationService userNotificationService, NotificationService notificationService,
            LocalizedNotificationService localizedNotificationService
    ) {
        return args -> {
            log.info("Generating default configuration of the application");
            Random random = SecureRandom.getInstanceStrong();

            if (roleService.countAll() <= 0) {
                log.info("Generating default roles USER_ROLE and ADMIN_ROLE");
                roleService.addAllRoles(roles);
            }
            if (locationService.countAll() <= 0) {
                log.info("Generating default locations from file");
                JSONArray locationArray = new JSONArray(new String(Files.readAllBytes(resourceFile.toPath())));
                locationArray.forEach(location -> {
                    locationService.addNewLocation(
                            new Location(null,
                                    ((JSONObject) location).getString("city"),
                                    ((JSONObject) location).getString("country")
                            )
                    );
                    log.info("Adding location {}",
                            ((JSONObject) location).getString("city") + ", " +
                                    ((JSONObject) location).getString("country"));
                });
            }
            if (channelService.countAll() <= 0) {
                log.info("Generating default channels TELEGRAM, EMAIL and DESKTOP");
                channelService.addAllChannels(channels);
            }
            if (notificationTypeService.countAll() <= 0) {
                log.info("Generating default notification types");
                notificationTypeService.addAllNotificationTypes(notificationTypes);
            }
            if (userService.countAll() <= 0) {
                log.info("Generating default users klimu.user and klimu.admin");
                PasswordEncoder passwordEncoder = passwordEncoder();
                appUsers.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));

                userService.saveAllUsers(appUsers);
                for (AppUser user : appUsers) {
                    roleService.addRoleToUser(user.getUsername(), roles.get(0).getName());
                }
                roleService.addRoleToUser(appUsers.get(1).getUsername(), roles.get(1).getName());

                List<Location> locations = locationService.getAllLocations();
                List<NotificationType> types = notificationTypeService.getAllNotificationTypes();

                for (AppUser user : appUsers) {
                    List<UserNotification> userNotifications = new ArrayList<>();
                    channelService.getAllChannels().forEach(channel -> {
                        List<LocalizedNotification> localizedNotifications = new ArrayList<>();

                        for (int i = 0; i < 6; i++) {
                            localizedNotifications.add(
                                    localizedNotificationService.addNewLocalizedNotification(
                                            new LocalizedNotification(
                                                    null,
                                                    types.get(random.nextInt(types.size())),
                                                    locations.get(random.nextInt(locations.size()))
                                            )
                                    )
                            );
                        }
                        userNotifications.add(
                                userNotificationService.addNewUserNotification(
                                        new UserNotification(null, channel, localizedNotifications)
                                )
                        );
                    });
                    user.setNotifications(userNotifications);
                    userService.updateUser(user);
                }
            }
            List<Location> locations = locationService.getAllLocations();
            List<NotificationType> types = notificationTypeService.getAllNotificationTypes();

            for (int i = 0; i < 20; i++) {
                NotificationType type = types.get(random.nextInt(types.size()));
                String msg;

                switch (type.getName()) {
                    case "Earthquake":
                        msg = "un terremoto de magnitud considerable";
                        break;
                    case "Rain":
                        msg = "lluvias";
                        break;
                    case "Heatwave":
                        msg = "una ola de calor";
                        break;
                    case "High Temperature":
                        msg = "una temperatura elevada";
                        break;
                    case "Cold":
                        msg = "una temperatura considerablemente baja";
                        break;
                    case "Snow":
                        msg = "peligro por nieves";
                        break;
                    default:
                        msg = "peligro";
                        break;
                }

                notificationService.addNewNotification(new Notification(
                        null,
                        (type.getType().equals(DANGER)) ? "Mucho cuidado, se ha detectado " + msg + " en la zona." :
                                "Aviso, se ha detectado " + msg + " en la zona.",
                        Date.from(LocalDate.now().atTime(LocalTime.now()).toInstant(ZoneOffset.UTC)),
                        type, locations.get(random.nextInt(locations.size()))
                ));
            }
            log.info("All the elements have already been generated");
        };
    }

}
