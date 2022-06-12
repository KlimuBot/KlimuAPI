package eus.klimu.users.domain.service.definition;

import eus.klimu.users.domain.model.Role;

import java.util.List;

/**
 * Definition of the different functions available for a role.
 */
public interface RoleService {

    /**
     * Count all the roles.
     * @return The number of roles.
     */
    Long countAll();

    /**
     * Get a role based on its identification number.
     * @param id The identification number of the role.
     * @return The role with that identification number, if found.
     */
    Role getRole(long id);

    /**
     * Get a role based on its name.
     * @param roleName The name of the role.
     * @return The role with that name, if found.
     */
    Role getRole(String roleName);

    /**
     * Get all the roles.
     * @return A list with all the roles.
     */
    List<Role> getAllRoles();

    /**
     * Save a new role.
     * @param role The role that is going to be saved.
     * @return The role after being saved.
     */
    Role addNewRole(Role role);

    /**
     * Save a list of new roles.
     * @param roles The list with all the roles that are going to be saved.
     * @return A list with all the roles after being saved.
     */
    List<Role> addAllRoles(List<Role> roles);

    /**
     * Update a role.
     * @param role The role that is going to be updated.
     * @return The role after being updated.
     */
    Role updateRole(Role role);

    /**
     * Delete a role.
     * @param role The role that is going to be deleted.
     */
    void deleteRole(Role role);

    /**
     * Add a role to a user.
     * @param username The username of the user that is going to get the role added.
     * @param roleName The name of the role that is going to be added.
     */
    void addRoleToUser(String username, String roleName);

}
