package eus.klimu.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A credential type, it determines the actions a user can make while using the system.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Entity(name = "role")
public class Role {

    /**
     * The identification number of the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * The name of the role.
     */
    @Column(unique = true)
    private String name;

    /**
     * Generate a role from a role Data Transfer Object.
     * @param roleDTO The role Data Transfer Object.
     * @return A role generated from a Data Transfer Object.
     */
    public static Role generateRole(RoleDTO roleDTO) {
        return new Role(roleDTO.getId(), roleDTO.getName());
    }

}