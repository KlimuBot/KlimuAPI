package eus.klimu.users.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

/**
 * The Data Transfer Object for the AppUser object.
 */
@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class AppUserDTO implements Serializable {

    /**
     * The identification number of the AppUser.
     */
    private Long id;
    /**
     * The username of the AppUser.
     */
    private String username;
    /**
     * The password of the AppUser.
     */
    private String password;
    /**
     * The name of the AppUser.
     */
    private String name;
    /**
     * The surname of the AppUser.
     */
    private String surname;
    /**
     * The email of the AppUser.
     */
    private String email;
    /**
     * The telephone number of the AppUser.
     */
    private String number;
    /**
     * The telegram identification number of the AppUser.
     */
    private String telegramId;
    /**
     * A list of identification numbers for the different roles of the AppUser.
     */
    private Collection<Long> roles;
    /**
     * A list of identification numbers for the user notifications of the AppUser.
     */
    private Collection<Long> notifications;

}
