package eus.klimu.users.api;

import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.service.definition.UserService;
import eus.klimu.users.domain.service.implementation.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private final UserServiceImp userService;

    @GetMapping("/{username}")
    public ResponseEntity<AppUser> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUser(username));
    }

    @PostMapping("/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user) {
        AppUser newUser = userService.saveUser(user);
        if (user != null) {
            return ResponseEntity.created(
                    // Specify where has the object been created.
                    URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/save").toUriString())
            ).body(newUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/addUsers")
    public ResponseEntity<List<AppUser>> addUsers(@RequestBody List<AppUser> list) {
        return ResponseEntity.ok(userService.createUserList(list));
    }

    @PutMapping("/updateUsers/")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user) {
        return ResponseEntity.ok().body(userService.updateUserById(user));
    }

    @DeleteMapping("/deleteUsers/{id}")
    public HttpStatus deleteUser(@PathVariable int id) {
        this.userService.deleteUserById(id);
        return HttpStatus.OK;
    }

}
