package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/fetch_adm")
public class AdminRest {

    private final UserServiceImpl userServiceImpl;
    private final RolesServiceImpl rolesServiceImpl;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminRest(UserServiceImpl userServiceImpl, RolesServiceImpl rolesServiceImpl,
                             PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.rolesServiceImpl = rolesServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @CrossOrigin
    @GetMapping("/authed_user")
    public User authenticatedUser(Principal principal) {
        return userServiceImpl.findByEmail(principal.getName());
    }

    @CrossOrigin
    @GetMapping("/roles")
    public List<Role> rolesList() {
        return rolesServiceImpl.findAll();
    }

    @CrossOrigin
    @GetMapping("/")
    public List<User> showAllUsers() {
        return userServiceImpl.findAll();
    }

    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @CrossOrigin
    @PatchMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public User findUser(@PathVariable("id") Long id) {
        if (userServiceImpl.findById(id).isPresent()) {
            return userServiceImpl.findById(id).get();
        } else {
            throw new RuntimeException();
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userServiceImpl.deleteById(id);
    }
}