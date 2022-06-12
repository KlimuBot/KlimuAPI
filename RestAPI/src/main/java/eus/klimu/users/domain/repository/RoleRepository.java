package eus.klimu.users.domain.repository;

import eus.klimu.users.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Access to the database for the roles.
 * Extends from a JpaRepository.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role based on its name.
     * @param name The name of the role.
     * @return The role with that name, if found.
     */
    Role findByName(String name);

}
