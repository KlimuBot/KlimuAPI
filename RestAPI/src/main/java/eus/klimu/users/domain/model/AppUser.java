package eus.klimu.users.domain.model;

import eus.klimu.notification.domain.model.UserNotification;
import eus.klimu.notification.domain.service.definition.UserNotificationService;
import eus.klimu.users.domain.service.definition.RoleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The information of a user that has the ability to access into the system.
 * It contains all the configuration and user credentials.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "app_user")
public class AppUser {

    /**
     * The identification number of the AppUser.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The username of the user, must be unique.
     */
    @Column(unique = true)
    private String username;
    /**
     * The password of the user after going through a hashing algorithm.
     */
    private String password;
    /**
     * The name of the user.
     */
    private String name;
    /**
     * The surname of the user.
     */
    private String surname;
    /**
     * The email of the user.
     */
    private String email;
    /**
     * The telephone number of the user.
     */
    private String number;
    /**
     * The telegram ID of the user.
     */
    private String telegramId;
    /**
     * The roles of the user.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    /**
     * The user notifications that the user has configured for the different channels.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<UserNotification> notifications = new ArrayList<>();

    /**
     * Generate an AppUser form an AppUser Data Transfer Object.
     * @param appUserDTO The AppUser Data Transfer Object.
     * @param roleService The role managing service.
     * @param userNotificationService The user notification managing service.
     * @return An AppUser generated from a Data Transfer Object.
     */
    public static AppUser generateAppUser(
            AppUserDTO appUserDTO, RoleService roleService, UserNotificationService userNotificationService
    ) {
        Collection<UserNotification> notifications = new ArrayList<>();
        Collection<Role> roles = new ArrayList<>();

        appUserDTO.getNotifications().forEach(notificationId -> notifications.add(
                userNotificationService.getUserNotification(notificationId)
        ));
        appUserDTO.getRoles().forEach(roleId -> roles.add(roleService.getRole(roleId)));

        return new AppUser(
                appUserDTO.getId(), appUserDTO.getUsername(), appUserDTO.getPassword(), appUserDTO.getName(),
                appUserDTO.getSurname(), appUserDTO.getEmail(), appUserDTO.getNumber(), appUserDTO.getTelegramId(),
                roles, notifications
        );
    }

}