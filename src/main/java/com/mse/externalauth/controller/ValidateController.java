package com.mse.externalauth.controller;

import com.mse.externalauth.model.RestResult;
import com.mse.externalauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author special.fy
 */
@RestController
public class ValidateController {

    @Autowired
    UserService userService;

    /**
     * Token resides in http header authorization.
     * You can update this header in your case.
     *
     * This validation base on the http status code.
     * The validation is passed when the http status code is 200.
     * The validation is not passed when the http status code is 4xx.
     *
     * @param token
     * @return
     */
    @RequestMapping("/validate-token/**")
    public RestResult<String> validateTokenByHTTPStatusCode(
            @RequestHeader(value = "authorization", required = false) String token) {
        return userService.check(token);
    }


    /**
     * Token resides in http header authorization.
     * You can update this header in your case.
     *
     * In some user case, the http status code of response is always 200.
     * So we introduce built-in http header x-mse-external-authz-check-result to help
     * these users to indicate whether the validation is passed.
     * The validation is passed when the http header x-mse-external-authz-check-result is true.
     * The validation is not passed when the http header x-mse-external-authz-check-result is false.
     *
     * @param token
     * @return
     */
    @RequestMapping("/custom-validate-token/**")
    public ResponseEntity<RestResult<String>> validateTokenByHTTPHeader(
            @RequestHeader(value = "authorization", required = false) String token) {
        RestResult<String> result = userService.check(token);
        HttpHeaders headers = new HttpHeaders();
        if (result.getStatus() != HttpStatus.OK.value()) {
            headers.add("x-mse-external-authz-check-result", "false");
            return ResponseEntity.ok().headers(headers).body(result);
        }

        headers.add("x-mse-external-authz-check-result", "true");
        return ResponseEntity.ok().headers(headers).body(result);

    }
}
