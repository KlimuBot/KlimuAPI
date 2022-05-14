package eus.klimu.users.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class AppUserDTO implements Serializable {

    private long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Collection<Long> roles;
    private Collection<Long> notifications;

}
