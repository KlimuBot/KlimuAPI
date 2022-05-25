package eus.klimu.users.domain.service.definition;

import eus.klimu.users.domain.model.Role;

import java.util.List;

public interface RoleService {

    Long countAll();
    Role getRole(long id);
    Role getRole(String roleName);
    List<Role> getAllRoles();
    Role addNewRole(Role role);
    List<Role> addAllRoles(List<Role> roles);
    Role updateRole(Role role);
    void deleteRole(Role role);
    void addRoleToUser(String username, String roleName);

}
