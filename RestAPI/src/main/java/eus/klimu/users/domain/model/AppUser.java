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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String number;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<UserNotification> notifications = new ArrayList<>();

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
                appUserDTO.getSurname(), appUserDTO.getEmail(), appUserDTO.getNumber(), roles, notifications
        );
    }

}