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
import java.util.List;

@Controller
@RequestMapping("/admin/")
public class AllUsersController {
    @Autowired
    UserService userService;

    @GetMapping(value = "all")
    public String loginJsp(Model model, Authentication authentication) {
        model.addAttribute("messages", userService.listUsers());
        model.addAttribute("user", authentication.getPrincipal());
        model.addAttribute("newUser", new User());
        return "users";
    }

    @GetMapping(value = "addU")
    public String add(){
        return "add";
    }

    @GetMapping(value = "getUpdate")
    public String getUpdate(@RequestParam("id") String id,
                            Model model) {
        User user = userService.getUserById(Long.parseLong(id));
        model.addAttribute("user", user);
        return "update";
    }

    @PostMapping(value = "add")
    public String addUser(@ModelAttribute User user,
                          @RequestParam("selectRole") String role) throws IOException, SQLException {
        List<Role> list = new ArrayList<>();
        list.add(new Role(role.toUpperCase()));
        user.setRoles(list);
        userService.add(user);
        return "redirect:/admin/all";
    }

    @PostMapping(value = "update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("selectRole") String role) throws IOException {
        List<Role> list = new ArrayList<>();
        list.add(new Role(role.toUpperCase()));
        user.setRoles(list);
        userService.updateUser(user);
        return "redirect:/admin/all";
    }

    @GetMapping(value = "delete")
    public String deleteUser(@RequestParam("id") Long id) throws IOException {
        User user = userService.getUserById(id);
        userService.deleteUser(user);
        return "redirect:/admin/all";
    }

    @PostMapping(value = "updateRole")
    public String updateRole(@RequestParam("selectRole") String role,
                             @RequestParam("id") Long id) throws IOException {
        userService.updateUserRole(role, id);
        return "redirect:/admin/all";
    }
}
