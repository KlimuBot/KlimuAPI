package eus.klimu.users.api;

import eus.klimu.channel.domain.model.Channel;
import eus.klimu.users.domain.model.Role;
import eus.klimu.users.domain.service.definition.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Role> getChannelById(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(roleService.getRole(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/{roleName}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Role> getRole(@PathVariable String roleName) {
        Role role = roleService.getRole(roleName);
        if (role != null) {
            return ResponseEntity.ok().body(role);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    @PostMapping(
            value = "/create",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role newRole = roleService.addNewRole(role);
        if (newRole != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/create").toUriString())
            ).body(newRole);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Role>> createAllRoles(@RequestBody List<Role> roles) {
        List<Role> newRoles = roleService.addAllRoles(roles);
        if (newRoles != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/create/all").toUriString())
            ).body(newRoles);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        Role updatedRole = roleService.updateRole(role);
        if (updatedRole != null) {
            return ResponseEntity.ok().body(updatedRole);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(value = "/set")
    public ResponseEntity<?> setUserRole(
            @RequestParam String username,
            @RequestParam String roleName
    ) {
        roleService.addRoleToUser(username, roleName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable long id) {
        if (id > 0) {
            roleService.deleteRole(roleService.getRole(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteRole(@RequestBody Role role) {
        if (role != null) {
            roleService.deleteRole(role);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
