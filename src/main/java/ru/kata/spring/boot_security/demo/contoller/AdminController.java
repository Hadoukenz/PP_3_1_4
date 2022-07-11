package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final RolesServiceImpl rolesServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RolesServiceImpl rolesServiceImpl, PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.rolesServiceImpl = rolesServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String showAllUsers(Model model) {
        List<User> users = userServiceImpl.findAll();

        model.addAttribute("users", users);

        return "admin_dash";
    }

    @GetMapping("/create_user")
    public String createUserForm(@ModelAttribute("newcomer") User user, Model model) {
        List<Role> rolesList = rolesServiceImpl.findAll();
        model.addAttribute("rolesList", rolesList);
        return "create_user";
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }

    @GetMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.deleteById(id);
        return "redirect:/admin/";
    }

    @GetMapping("/update_user/{id}")
    public String updateUserForm(Model model, @PathVariable("id") Long id) {
        User user = userServiceImpl.findById(id);
        model.addAttribute("edit", user);


        List<Role> rolesList = rolesServiceImpl.findAll();
        model.addAttribute("rolesList", rolesList);
        return "update_user";
    }

    @PostMapping("/update_user")
    public String updateUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }
}
