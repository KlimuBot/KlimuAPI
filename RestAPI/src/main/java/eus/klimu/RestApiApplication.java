package eus.klimu;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.channel.domain.service.definition.ChannelService;
import eus.klimu.location.domain.model.Location;
import eus.klimu.location.domain.service.definition.LocationService;
import eus.klimu.notification.domain.model.NotificationType;
import eus.klimu.notification.domain.service.definition.NotificationTypeService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
public class RestApiApplication {

    private static final String DANGER = "Dangerous";
    private static final String WARNING = "Warning";
    private static final String INFORMATION = "Information";

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
                    "klimubot@gmail.com", new ArrayList<>(), new ArrayList<>()),
            new AppUser(null, "klimu.admin", "klimu@admin", "klimu", "admin",
                    "popbl6_talde2-group@alumni.mondragon.edu", new ArrayList<>(), new ArrayList<>())
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
            new NotificationType(null, "Earthquake", "Earthquake", DANGER),
            new NotificationType(null, "Heavy Rain", "Heavy Rain", DANGER),
            new NotificationType(null, "Rain", "Rain", WARNING),
            new NotificationType(null, "Light Rain", "Light Rain", INFORMATION),
            new NotificationType(null, "Heatwave", "Heatwave", DANGER),
            new NotificationType(null, "High Temperature", "High Temperature", DANGER),
            new NotificationType(null, "Cold", "Cold", WARNING),
            new NotificationType(null, "Freezing Cold", "Freezing Cold", DANGER),
            new NotificationType(null, "Foggy", "Foggy", WARNING),
            new NotificationType(null, "Light Snow", "Light Snow", INFORMATION),
            new NotificationType(null, "Heavy Snow", "Heavy Snow", DANGER)
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
     * @param userService The service for managing the users.
     * @param roleService The service for managing the roles.
     * @return A collection of functions that will be executed at the beginning of execution.
     */
    @Bean
    public CommandLineRunner run(
            UserService userService, RoleService roleService, ChannelService channelService,
            LocationService locationService, NotificationTypeService notificationTypeService
    ) {
        return args -> {
            log.info("Generating default configuration of the application");

            if (roleService.countAll() <= 0) {
                log.info("Generating default roles USER_ROLE and ADMIN_ROLE");
                roleService.addAllRoles(roles);
            }
            if (userService.countAll() <= 0) {
                log.info("Generating default users klimu.user and klimu.admin");
                PasswordEncoder passwordEncoder = passwordEncoder();
                appUsers.forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));

                userService.saveAllUsers(appUsers);
                roleService.addRoleToUser(appUsers.get(0).getUsername(), roles.get(0).getName());
                roleService.addRoleToUser(appUsers.get(1).getUsername(), roles.get(0).getName());
                roleService.addRoleToUser(appUsers.get(1).getUsername(), roles.get(1).getName());
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
            log.info("All the elements have already been generated");
        };
    }

}
