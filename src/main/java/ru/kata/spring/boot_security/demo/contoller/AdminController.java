package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
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
    public String showAllUsers(Model model, Principal principal) {
        List<User> users = userServiceImpl.findAll();
        List<Role> rolesList = rolesServiceImpl.findAll();
        User loggedUser = userServiceImpl.findByEmail(principal.getName());
        User newUser = new User();

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("rolesList", rolesList);
        model.addAttribute("users", users);
        model.addAttribute("newUser", newUser);

        return "admin_dash";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.deleteById(id);
        return "redirect:/admin/";
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }
}
