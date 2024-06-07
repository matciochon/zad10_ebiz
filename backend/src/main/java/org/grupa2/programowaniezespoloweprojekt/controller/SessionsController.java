package org.grupa2.programowaniezespoloweprojekt.controller;

import org.grupa2.programowaniezespoloweprojekt.model.Sessions;
import org.grupa2.programowaniezespoloweprojekt.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Kontroler Spring MVC wystawiający endpointy REST.
 */
@RestController
public class SessionsController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/sessions")
    public List<Sessions> getAllSessions() {
        return usersService.getAllSessions();
    }

    @GetMapping("/session")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Integer>> checkSession(@RequestHeader("Authorization") Optional<String> uuid) {
        
        Integer user_id = usersService.checkSession(uuid.get());
        if (uuid.isEmpty() || user_id < 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(List.of(-1));
        }
        
        return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(List.of(user_id));
    }
}
