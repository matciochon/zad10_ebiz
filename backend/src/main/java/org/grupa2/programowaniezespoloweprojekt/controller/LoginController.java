package org.grupa2.programowaniezespoloweprojekt.controller;

import org.grupa2.programowaniezespoloweprojekt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<List<String>> login(@RequestBody MultiValueMap<String, String> formData) {
        String login = formData.toSingleValueMap().get("login");
        String password = formData.toSingleValueMap().get("password");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
        .headers(responseHeaders)
        .body(List.of(usersService.login(login, password)));
    }

}
