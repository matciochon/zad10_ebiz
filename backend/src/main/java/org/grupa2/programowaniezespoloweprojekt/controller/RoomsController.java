package org.grupa2.programowaniezespoloweprojekt.controller;

import org.grupa2.programowaniezespoloweprojekt.model.Rooms;
import org.grupa2.programowaniezespoloweprojekt.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Kontroler Spring MVC wystawiający endpointy REST.
 */
@RestController
public class RoomsController {
    @Autowired
    private RoomsService roomsService;

    @GetMapping("/rooms")
    public ResponseEntity<List<Rooms>> getAllRooms() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
        .headers(responseHeaders)
        .body(roomsService.getAllRooms());
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Rooms> getRoomById(@PathVariable Integer id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        Rooms room = roomsService.getRoomById(id);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(room);
    }

}
