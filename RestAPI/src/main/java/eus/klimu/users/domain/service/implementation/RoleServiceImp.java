package eus.klimu.users.domain.service.implementation;

import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.model.Role;
import eus.klimu.users.domain.repository.RoleRepository;
import eus.klimu.users.domain.repository.UserRepository;
import eus.klimu.users.domain.service.definition.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * The implementation of the role service. It interacts with the different roles
 * using a role repository, modifying directly on the database.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    /**
     * The connection with the AppUser table on the database.
     */
    private final UserRepository userRepository;
    /**
     * The connection with the role table on the database.
     */
    private final RoleRepository roleRepository;

    /**
     * Count all the roles on the database.
     * @return The number of roles on the database.
     */
    @Override
    public Long countAll() {
        long count = roleRepository.count();
        log.info("Found {} roles on the database", count);
        return count;
    }

    /**
     * Get a role based on its identification number from the database.
     * @param id The identification number of the role.
     * @return The role with that identification number from the database, if found.
     */
    @Override
    public Role getRole(long id) {
        log.info("Fetching role with id={} from repository", id);
        return roleRepository.findById(id).orElse(null);
    }

    /**
     * Get a role based on its name from the database.
     * @param roleName The name of the role.
     * @return The role with that name from the database, if found.
     */
    @Override
    public Role getRole(String roleName) {
        log.info("Fetching role {} from repository", roleName);
        return roleRepository.findByName(roleName);
    }

    /**
     * Get all the roles from the database.
     * @return A list with all the roles from the database.
     */
    @Override
    public List<Role> getAllRoles() {
        log.info("Fetching all roles from database");
        return roleRepository.findAll();
    }

    /**
     * Save a new role on the database.
     * @param role The role that is going to be saved.
     * @return The role after being saved on the database.
     */
    @Override
    public Role addNewRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepository.save(role);
    }

    /**
     * Save a list of new roles on the database.
     * @param roles The list with all the roles that are going to be saved.
     * @return A list with all the roles after being saved on the database.
     */
    @Override
    public List<Role> addAllRoles(List<Role> roles) {
        log.info("Saving {} roles on the database", roles.size());
        return roleRepository.saveAll(roles);
    }

    /**
     * Update a role on the database.
     * @param role The role that is going to be updated.
     * @return The role after being updated on the database.
     */
    @Override
    public Role updateRole(Role role) {
        log.info("Updating role with id={}", role.getId());
        return roleRepository.save(role);
    }

    /**
     * Delete a role from the database.
     * @param role The role that is going to be deleted.
     */
    @Override
    public void deleteRole(Role role) {
        log.info("Deleting role with id={}", role.getId());
        roleRepository.delete(role);
    }

    /**
     * Add a role to a user.
     * @param username The username of the user that is going to get the role added.
     * @param roleName The name of the role that is going to be added.
     */
    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser user = userRepository.findByUsername(username).orElse(null);
        Role role = roleRepository.findByName(roleName);

        if (user != null && role != null) {
            user.getRoles().add(role);
        } else {
            log.error("Couldn't add role {} to user {}", roleName, username);
        }
    }
}
