package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/rest")
public class RestfulController {

    private final UserServiceImpl userServiceImpl;
    private final RolesServiceImpl rolesServiceImpl;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RestfulController(UserServiceImpl userServiceImpl, RolesServiceImpl rolesServiceImpl,
                             PasswordEncoder passwordEncoder) {
        this.userServiceImpl = userServiceImpl;
        this.rolesServiceImpl = rolesServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/authed_user")
    public User authenticatedUser(Principal principal) {
        return userServiceImpl.findByEmail(principal.getName());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> rolesList() {
        return ResponseEntity.ok(rolesServiceImpl.findAll());
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userServiceImpl.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userServiceImpl.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") Long id) {
        if (userServiceImpl.findById(id).isPresent()) {
            return ResponseEntity.ok(userServiceImpl.findById(id).get());
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        if (userServiceImpl.findById(id).isPresent()) {
            userServiceImpl.deleteById(id);
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

//POST
//{
//    "name":"5",
//        "lastname":"5",
//        "password":"5",
//        "age":5,
//        "email":"5@mail.com",
//        "roles":[
//    {
//        "id":2,
//            "name":"ROLE_ADMIN",
//            "users":null,
//            "authority":"ROLE_ADMIN"
//    },
//    {
//        "id":1,
//            "name":"ROLE_USER",
//            "users":null,
//            "authority":"ROLE_USER"
//    }
//   ]
//}

//PATCH
//{
//    "id":25,
//        "name":"2",
//        "lastname":"2",
//        "password":"2",
//        "age":126,
//        "email":"2@mail.com",
//        "roles":[
//    {
//        "id":2,
//            "name":"ROLE_ADMIN",
//            "users":null,
//            "authority":"ROLE_ADMIN"
//    },
//    {
//        "id":1,
//            "name":"ROLE_USER",
//            "users":null,
//            "authority":"ROLE_USER"
//    }
//   ]
//}

}