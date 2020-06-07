package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.model.Role;
import spring.model.User;
import spring.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping(value = "all", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> loginJsp() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "addUser")
    public ResponseEntity<User> addUser(@ModelAttribute User user) throws SQLException {
        Role role = userService.getUserRole(user.getRoles().get(0).getRole());
        List<Role> list = new ArrayList<>();
        list.add(role);
        user.setRoles(list);
        userService.add(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "getUserById")
    public ResponseEntity<User> addUser(@RequestParam Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping(value = "deleteUser")
    public ResponseEntity deleteUser(@RequestParam Long id) {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "updateUser")
    public ResponseEntity<User> updateUser(@ModelAttribute User user) {
        Role role = userService.getUserRole(user.getRoles().get(0).getRole());
        User userId = userService.getUserById(user.getId());
        List<Role> list = userId.getRoles();
        if (!userId.getRoles().contains(role)) {
            list.add(role);
        }
        user.setRoles(list);
        if (!user.getRoles().get(0).getRole().isEmpty()) {
            userService.updateUser(user);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
