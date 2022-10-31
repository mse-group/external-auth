package com.mse.externalauth.service;

import com.mse.externalauth.model.RestResult;
import com.mse.externalauth.model.Token;
import com.mse.externalauth.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author special.fy
 */
@Service
public class UserService {

    private Map<String, String> users = new ConcurrentHashMap<>();

    private Map<String, String> tokens = new ConcurrentHashMap<>();

    public RestResult<String> signup(User user) {
        if (users.containsKey(user.getUsername())) {
            return RestResult.failResult(HttpStatus.BAD_REQUEST.value(), "username is already defined");
        }

        users.put(user.getUsername(), user.getPassword());
        return RestResult.successResult("signup success");
    }

    public RestResult<Token> login(User user) {
        if (!users.containsKey(user.getUsername())) {
            return RestResult.failResult(HttpStatus.BAD_REQUEST.value(), "username is not found");
        }

        if (!user.getPassword().equals(users.get(user.getUsername()))) {
            return RestResult.failResult(HttpStatus.BAD_REQUEST.value(), "password is wrong");
        }

        String token = UUID.randomUUID().toString();
        tokens.put(token, "");
        return RestResult.successResult(Token.builder().token(token).build(), "login success");
    }

    public RestResult<String> check(String token) {
        if (StringUtils.isEmpty(token)) {
            return RestResult.failResult(HttpStatus.UNAUTHORIZED.value(), "missing token");
        }

        if (!tokens.containsKey(token)) {
            return RestResult.failResult(HttpStatus.FORBIDDEN.value(), "token is invalid");
        }

        return RestResult.success("validate success");
    }
}
