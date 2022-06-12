package eus.klimu.users.api;

import eus.klimu.users.domain.model.Role;
import eus.klimu.users.domain.model.RoleDTO;
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

/**
 * Create, update, delete and get information for the Roles. They represent the different types of users the
 * application has, granting different permissions depending on the role. Takes care of all the CRUD methods,
 * working with the database through an internal Service.
 */
@Controller
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    /**
     * A class that allows modifying the roles.
     */
    private final RoleService roleService;

    /**
     * <p>Get a role based on its ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/{id}">https://klimu.eus/RestAPI/role/{id}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the role that is going to be fetch.
     * @return A 200 ok if the role was found or a 400 bad request if it wasn't.
     */
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

    /**
     * <p>Get a role based on its name.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/name/{roleName}">https://klimu.eus/RestAPI/role/name/{roleName}</a></p>
     *
     * <ul>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param roleName The name of the role that is going to be fetched.
     * @return A 200 ok if the role was found or a 400 bad request if it wasn't.
     */
    @GetMapping(
            value = "/name/{roleName}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Role> getRoleByName(@PathVariable String roleName) {
        Role role = roleService.getRole(roleName);
        if (role != null) {
            return ResponseEntity.ok().body(role);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Get all the roles.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/all">https://klimu.eus/RestAPI/role/all</a></p>
     *
     * <ul>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @return A 200 ok if the roles were found or a 400 bad request if it wasn't.
     */
    @GetMapping(
            value = "/all",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

    /**
     * <p>Save a new role on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/create">https://klimu.eus/RestAPI/role/create</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param role The role that is going to be created.
     * @return A 200 ok if the role was created or a 400 bad request if it wasn't.
     */
    @PostMapping(
            value = "/create",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Role> createRole(@RequestBody RoleDTO role) {
        Role newRole = roleService.addNewRole(Role.generateRole(role));
        if (newRole != null) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/create").toUriString())
            ).body(newRole);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Save an X amount of roles on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/create/all">https://klimu.eus/RestAPI/role/create/all</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json</li>
     *     <li>Produces: application/json</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param roles The list of roles that are going to be created.
     * @return A 200 ok if the roles were created or a 400 bad request if it wasn't.
     */
    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<Role>> createAllRoles(@RequestBody List<Role> roles) {
        List<Role> newRoles = roleService.addAllRoles(roles);
        if (newRoles != null && !roles.isEmpty()) {
            return ResponseEntity.created(
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/create/all").toUriString())
            ).body(newRoles);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Update a role on the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/update">https://klimu.eus/RestAPI/role/update</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param role The role that is going to be updated.
     * @return A 200 ok if the role was updated or a 400 bad request if it wasn't.
     */
    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<Role> updateRole(@RequestBody RoleDTO role) {
        Role updatedRole = roleService.updateRole(Role.generateRole(role));
        if (updatedRole != null) {
            return ResponseEntity.ok().body(updatedRole);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Set the role of a user.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/set">https://klimu.eus/RestAPI/role/set</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Produces: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param username The username of the user.
     * @param roleName The name of the role.
     * @return A 200 ok if the role was updated or a 400 bad request if it wasn't.
     */
    @PutMapping(value = "/set")
    public ResponseEntity<Object> setUserRole(
            @RequestParam String username,
            @RequestParam String roleName
    ) {
        if (username != null && roleName != null) {
            roleService.addRoleToUser(username, roleName);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * <p>Delete a request based on its ID.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/delete/{id}">https://klimu.eus/RestAPI/role/delete/{id}</a></p>
     *
     * <ul>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param id The ID of the role that is going to be deleted.
     * @return A 200 ok if the role was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteRoleById(@PathVariable long id) {
        if (id > 0) {
            roleService.deleteRole(roleService.getRole(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * <p>Delete a role from the database.</p>
     *
     * <p><a href="https://klimu.eus/RestAPI/role/delete">https://klimu.eus/RestAPI/role/delete</a></p>
     *
     * <ul>
     *     <li>Consumes: application/json, application/xml</li>
     *     <li>Requires: Access and Refresh tokens as headers.</li>
     * </ul>
     *
     * @param role The role that is going to be deleted.
     * @return A 200 ok if the role was deleted or a 400 bad request if it wasn't.
     */
    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteRole(@RequestBody RoleDTO role) {
        if (role != null && roleService.getRole(role.getId()) != null) {
            roleService.deleteRole(Role.generateRole(role));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
