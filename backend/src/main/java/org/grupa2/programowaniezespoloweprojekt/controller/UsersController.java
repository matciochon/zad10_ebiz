package org.grupa2.programowaniezespoloweprojekt.controller;

import org.grupa2.programowaniezespoloweprojekt.model.Users;
import org.grupa2.programowaniezespoloweprojekt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Kontroler Spring MVC wystawiający endpointy REST.
 */
@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }
}
