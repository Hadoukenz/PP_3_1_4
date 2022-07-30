package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/fetch_user")
public class UserRest {

    private final UserServiceImpl userServiceImpl;


    @Autowired
    public UserRest(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @CrossOrigin
    @GetMapping("/authed_user")
    public User authenticatedUser(Principal principal) {
        return userServiceImpl.findByEmail(principal.getName());
    }
}