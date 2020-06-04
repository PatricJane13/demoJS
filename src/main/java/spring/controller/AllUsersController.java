package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.model.Role;
import spring.model.User;
import spring.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AllUsersController {
    @Autowired
    UserService userService;

    @GetMapping(value = "all")
    public String loginJsp(Model model, Authentication authentication) {
//        model.addAttribute("messages", userService.listUsers());
        model.addAttribute("user", authentication.getPrincipal());
        model.addAttribute("newUser", new User());
        return "users2";
    }

    @PostMapping(value = "add")
    public String addUser(@ModelAttribute User user, @RequestParam String selectRole) throws IOException, SQLException {
        Role role = userService.getUserRole(selectRole);
        List<Role> list = new ArrayList<>();
        list.add(role);
        user.setRoles(list);
        if (!selectRole.isEmpty()) {
            userService.add(user);
        }
        return "redirect:/admin/all";
    }

    @PostMapping(value = "update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam String selectRole) throws IOException {
        Role role = userService.getUserRole(selectRole);
        User userId = userService.getUserById(user.getId());
        List<Role> list = userId.getRoles();
        if (!userId.getRoles().contains(role)) {
            list.add(role);
        }
        user.setRoles(list);
        if (!selectRole.isEmpty()) {
            userService.updateUser(user);
        }
        return "redirect:/admin/all";
    }

    @GetMapping(value = "delete")
    public String deleteUser(@RequestParam("id") Long id) throws IOException {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return "redirect:/admin/all";
    }
}
