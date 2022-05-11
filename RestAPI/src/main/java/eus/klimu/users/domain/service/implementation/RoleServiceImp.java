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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Role getRole(long id) {
        log.info("Fetching role with id={} from repository", id);
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role getRole(String roleName) {
        log.info("Fetching role {} from repository", roleName);
        return roleRepository.findByName(roleName);
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("Fetching all roles from database");
        return roleRepository.findAll();
    }

    @Override
    public Role addNewRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public List<Role> addAllRoles(List<Role> roles) {
        log.info("Saving {} roles on the database", roles.size());
        return roleRepository.saveAll(roles);
    }

    @Override
    public Role updateRole(Role role) {
        log.info("Updating role with id={}", role.getId());
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        log.info("Deleting role with id={}", role.getId());
    }

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
