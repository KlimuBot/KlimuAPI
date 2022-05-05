package eus.klimu.home.api;

import eus.klimu.users.domain.model.AppUser;
import eus.klimu.users.domain.service.implementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserServiceImp services;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok(services.getUserList());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<AppUser> getUserById(@PathVariable int id) {
        return ResponseEntity.ok().body(this.services.getUserById(id));
    }

    @PostMapping("/addUser")
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user) {
        return ResponseEntity.ok(this.services.saveUser(user));
    }

    @PostMapping("/addUsers")
    public ResponseEntity<List<AppUser>> addUsers(@RequestBody List<AppUser> list) {
        return ResponseEntity.ok(this.services.createUserList(list));
    }

    @PutMapping("/updateUsers/")
    public ResponseEntity<AppUser> updateUser(@RequestBody AppUser user) {
        return ResponseEntity.ok().body(this.services.updateUserById(user));
    }

    @DeleteMapping("/deleteUsers/{id}")
    public HttpStatus deleteUser(@PathVariable int id) {
        this.services.deleteUserById(id);
        return HttpStatus.OK;
    }
}