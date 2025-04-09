package jbar.service_core.User.Controller;

import jbar.service_core.User.Model.UserDTO;
import jbar.service_core.Util.Response.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<Message> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping("/waiters")
    public ResponseEntity<Message> getAllWaiters() {
        return userService.getAllWaiters();
    }

    @PostMapping
    public ResponseEntity<Message> createUser(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Message> changeUserStatus(@PathVariable Integer id) {
        return userService.changeStatus(id);
    }
}
