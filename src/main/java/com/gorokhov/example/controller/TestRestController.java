package com.gorokhov.example.controller;

import com.gorokhov.example.domain.User;
import com.gorokhov.example.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestRestController {

    private final UserService userService;

    public TestRestController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/user")
    public String user() {
        return "Hi user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hi admin";
    }

    @GetMapping("/listuser")
    public @ResponseBody List<User> listUser() {
        return userService.findAll();
    }

}
