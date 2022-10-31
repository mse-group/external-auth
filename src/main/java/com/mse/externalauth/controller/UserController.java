package com.mse.externalauth.controller;

import com.mse.externalauth.model.RestResult;
import com.mse.externalauth.model.Token;
import com.mse.externalauth.model.User;
import com.mse.externalauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author special.fy
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/signup")
    public RestResult<String> signup(@RequestBody User user) {
        return userService.signup(user);
    }

    @PostMapping("/login")
    public RestResult<Token> login(@RequestBody User user) {
        return userService.login(user);
    }
}
