package org.grupa2.programowaniezespoloweprojekt.controller;

import org.grupa2.programowaniezespoloweprojekt.model.Reservations;
import org.grupa2.programowaniezespoloweprojekt.model.ReservationRequest;
import org.grupa2.programowaniezespoloweprojekt.service.ReservationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter; 

/**
 * Kontroler Spring MVC wystawiający endpointy REST.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ReservationsController {

    @Autowired
    private ReservationsService reservationsService;

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservations>> getReservations(@RequestParam("roomId") Optional<Integer> roomId) {
        HttpHeaders responseHeaders = new HttpHeaders();
        
        if (roomId.isPresent()) {
            return ResponseEntity.ok()
            .headers(responseHeaders)
            .body(reservationsService.getReservationsByRoomId(roomId.get()));
        } else {
            return ResponseEntity.ok()
            .headers(responseHeaders)
            .body(reservationsService.getAllReservations());

        }
    }

    @GetMapping("/reservations/accept/{id}")
    public ResponseEntity<Reservations> acceptReservation(@PathVariable Integer id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        return ResponseEntity.ok()
        .headers(responseHeaders)
        .body(reservationsService.acceptReservation(id));
    }

    @GetMapping("/reservations/reject/{id}")
    public ResponseEntity<Reservations> rejectReservation(@PathVariable Integer id){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");


        return ResponseEntity.ok()
        .headers(responseHeaders)
        .body(reservationsService.rejectReservation(id));
    }

    @GetMapping("/reservations/notaccepted")
    public ResponseEntity<List<Reservations>> getAllNotAcceptedReservations() {

        HttpHeaders responseHeaders = new HttpHeaders();

        return ResponseEntity.ok()
        .headers(responseHeaders)
        .body(reservationsService.getAllNotAcceptedReservations());
    }

    @PostMapping("/reservations/add")
    public ResponseEntity<String> addNewReservation(@RequestBody ReservationRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        if (isValid(request)) {
            Reservations reservation = new Reservations();

            String start = request.getStart().split("\\.")[0];
            String end = request.getEnd().split("\\.")[0];
            String Res_time = request.getReservation_datetime().split("\\.")[0];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

            LocalDateTime startDate = LocalDateTime.parse(start, formatter);
            LocalDateTime endDate = LocalDateTime.parse(end, formatter);
            LocalDateTime Reservation_datetime = LocalDateTime.parse(Res_time, formatter);
            

            reservation.setStart(startDate);
            reservation.setEnd(endDate);
            reservation.setName(request.getName());
            reservation.setRoom_id(request.getRoom_id());
            reservation.setLecturer_mail(request.getLecturer_mail());
            reservation.setLecturer_first_name(request.getLecturer_first_name());
            reservation.setLecturer_last_name(request.getLecturer_last_name());
            reservation.setParticipants_count(request.getParticipants_count());
            reservation.setReservation_datetime(Reservation_datetime);
            reservation.setAccepted(0);

            reservationsService.addReservation(reservation);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body("Prośba o rezerwację utworzona pomyślnie, czeka na zatwierdzenie przez administratora");
        } else {
            return ResponseEntity.badRequest()
                    .headers(responseHeaders)
                    .body("Zły format wprowadzonych danych w jednym lub więcej pól.");
        }
    }

            private boolean isValid(ReservationRequest request) {
        String lecturerEmail = request.getLecturer_mail();
        if (lecturerEmail == null || !lecturerEmail.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            return false;
        }

        String lecturerFirstName = request.getLecturer_first_name();
        if (lecturerFirstName == null || lecturerFirstName.isEmpty()) {
            return false;
        }

        String lecturerLastName = request.getLecturer_last_name();
        if (lecturerLastName == null || lecturerLastName.isEmpty()) {
            return false;
        }

        String name = request.getName();
        if (name == null || name.isEmpty()) {
            return false;
        }

        Integer participantsCount = request.getParticipants_count();
        if (participantsCount == null || participantsCount <= 0) {
            return false;
        }

        return true;
    }
}
