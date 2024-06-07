package org.grupa2.programowaniezespoloweprojekt.service;

import org.grupa2.programowaniezespoloweprojekt.model.Rooms;
import org.grupa2.programowaniezespoloweprojekt.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Klasa Service zawierająca logikę biznesową związaną z tabelą rooms.
 */
@Service
public class RoomsService {
    @Autowired
    private RoomsRepository roomsRepository;

    public List<Rooms> getAllRooms() {
        return (List<Rooms>) roomsRepository.findAll();
    }

    public Rooms getRoomById(Integer id) {
        Optional<Rooms> optionalRoom = roomsRepository.findById(id);
        return optionalRoom.orElse(null);
    }

    // Tu dodawać w razie potrzeby inne metody logiki biznesowej

}
