package eus.klimu.users.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class AppUserDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String number;
    private String telegramId;
    private Collection<Long> roles;
    private Collection<Long> notifications;

}
