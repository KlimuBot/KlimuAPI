package eus.klimu.users.api;

import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.service.implementation.UserServiceImp;
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

    private final UserServiceImp userService;

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable long id) {
        if (id > 0) {
            return ResponseEntity.ok().body(userService.getUser(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/{username}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
        if (username != null) {
            return ResponseEntity.ok().body(userService.getUser(username));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        String msg = userService.checkUser(user);
        if (msg.equals("OK")) {
            AppUser newUser = userService.saveUser(user);
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/create").toUriString())
            ).body(newUser);
        } else {
            return ResponseEntity.badRequest().header("error", msg).build();
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
                        .header("error", user.getUsername() + " => " + msg).build();
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
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user) {
        String msg = userService.checkUser(user);
        if (!msg.equals("OK")) {
            return ResponseEntity.badRequest().header("error", msg).build();
        }
        return ResponseEntity.ok().body(userService.updateUser(user));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        if (id > 0) {
            userService.deleteUser(userService.getUser(id));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteUser(@RequestBody AppUser user) {
        if (user != null) {
            userService.deleteUser(user);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
