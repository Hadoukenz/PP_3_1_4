package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/")
    public String showAllUsers(Model model) {
        List<User> users = userServiceImpl.findAll();
        model.addAttribute("users", users);
        return "admin_dash";
    }

    @GetMapping("/create_user")
    public String createUserForm(@ModelAttribute("newcomer") User user) {
        return "create_user";
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute User user) {
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
        return "update_user";
    }

    @PostMapping("/update_user")
    public String updateUser(User user) {
        userServiceImpl.saveUser(user);
        return "redirect:/admin/";
    }
}
