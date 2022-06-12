package eus.klimu.users.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * The Data Transfer Object for the role object.
 */
@Getter
@Setter
@XmlRootElement
@NoArgsConstructor
public class RoleDTO implements Serializable {

    /**
     * The identification number of the role.
     */
    private Long id;
    /**
     * The name of the role.
     */
    private String name;

}
