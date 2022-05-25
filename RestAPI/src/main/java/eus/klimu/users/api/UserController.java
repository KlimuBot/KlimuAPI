package eus.klimu.users.api;

import com.auth0.jwt.exceptions.JWTVerificationException;
import eus.klimu.notification.domain.service.definition.UserNotificationService;
import eus.klimu.security.TokenManagement;
import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.model.AppUserDTO;
import eus.klimu.users.domain.service.definition.RoleService;
import eus.klimu.users.domain.service.definition.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private static final String ERROR_HEADER = "errorMsg";

    private final UserService userService;
    private final RoleService roleService;
    private final TokenManagement tokenManagement;
    private final UserNotificationService userNotificationService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUser(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(userService.getUser(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/username/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
        if (username != null) {
            return ResponseEntity.ok().body(userService.getUser(username));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/from-token/{token}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUserFromToken(@PathVariable String token) {
        try {
            String username = tokenManagement.getUserFromToken(token).getUsername();
            AppUser user = userService.getUser(username);

            if (user != null) {
                return ResponseEntity.ok().body(user);
            }
        } catch (JWTVerificationException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<AppUser> createUser(@RequestBody AppUserDTO user) {
        AppUser newUser = AppUser.generateAppUser(user, roleService, userNotificationService);
        String msg = userService.checkUser(newUser);

        if (msg.equals("OK")) {
            newUser = userService.saveUser(newUser);
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/create").toUriString())
            ).body(newUser);
        } else {
            return ResponseEntity.badRequest().header(ERROR_HEADER, msg).build();
        }
    }

    @PostMapping(
            value = "/create/all",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<AppUser>> createAllUsers(@RequestBody List<AppUser> users) {
        String msg;

        for (AppUser user : users) {
            msg = userService.checkUser(user);
            if (!msg.equals("OK")) {
                return ResponseEntity.badRequest()
                        .header(ERROR_HEADER, user.getUsername() + " => " + msg).build();
            }
        }
        return ResponseEntity.created(
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/create/all").toUriString())
        ).body(userService.saveAllUsers(users));
    }

    @PutMapping(
            value = "/update",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUserDTO user) {
        AppUser appUser = AppUser.generateAppUser(user, roleService, userNotificationService);
        String msg = userService.checkUser(appUser);

        if (!msg.equals("OK")) {
            return ResponseEntity.badRequest().header(ERROR_HEADER, msg).build();
        }
        return ResponseEntity.ok().body(userService.updateUser(appUser));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable int id) {
        if (id > 0) {
            userService.deleteUser(userService.getUser(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Object> deleteUser(@RequestBody AppUserDTO user) {
        if (user != null && userService.getUser(user.getId()) != null) {
            userService.deleteUser(AppUser.generateAppUser(user, roleService, userNotificationService));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
