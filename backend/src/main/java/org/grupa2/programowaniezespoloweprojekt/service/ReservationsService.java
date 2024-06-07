package org.grupa2.programowaniezespoloweprojekt.service;

import org.grupa2.programowaniezespoloweprojekt.model.Reservations;
import org.grupa2.programowaniezespoloweprojekt.repository.ReservationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Klasa Service zawierająca logikę biznesową związaną z tabelą reservations.
 */
@Service
public class ReservationsService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ReservationsRepository reservationsRepository;

    public List<Reservations> getAllReservations() {
        return (List<Reservations>) reservationsRepository.findAll();
    }

    public List<Reservations> getReservationsByRoomId(Integer roomId) {
        return (List<Reservations>) reservationsRepository.findByRoomId(roomId);
    }

    public List<Reservations> getAllNotAcceptedReservations() {
        return (List<Reservations>) reservationsRepository.findByAccepted(0);
    }

   public Reservations acceptReservation(int id) {
        Optional<Reservations> optionalReservation = reservationsRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservations reservation = optionalReservation.get();
            reservation.setAccepted(1);
            
            List<Reservations> notAcceptedReservations = getAllNotAcceptedReservations();
            for (Reservations res : notAcceptedReservations) {
                if (Objects.equals(res.getRoom_id(), reservation.getRoom_id())){
                    if ( res.getStart().isBefore(reservation.getEnd()) && res.getEnd().isAfter(reservation.getStart()) ) {
                        deleteReservation(res);
                        if (!Objects.equals(res.getId(), reservation.getId())) {
                            emailService.sendMail(
                                    res.getLecturer_mail(),
                                    "Rezerwacja odrzucona",
                                    "Twoja rezerwacja sali w serwisie Rezerwacje PZG2 została odrzucona.\n" +
                                            "Rezerwacja zawierała następujące dane:\n" +
                                            "Sala: " + res.getRoom_id() + "\n" +
                                            "Data wykonania rezerwacji: " + res.getReservation_datetime() + "\n" +
                                            "Czas rozpoczęcia rezerwacji: " + res.getStart() + "\n" +
                                            "Czas zakończenia rezerwacji: " + res.getEnd() + "\n\n" +
                                            "Przepraszamy za niedogodność i zachęcamy do złożenia rezerwacji w innym terminie."
                            );
                        }
                    }
                }
            }

            emailService.sendMail(
                    reservation.getLecturer_mail(),
                    "Rezerwacja zaakceptowana",
                    "Twoja rezerwacja sali w serwisie Rezerwacje PZG2 została zaakceptowana.\n" +
                            "Rezerwacja zawiera następujące dane:\n" +
                            "Sala: " + reservation.getRoom_id() + "\n" +
                            "Data wykonania rezerwacji: " + reservation.getReservation_datetime() + "\n" +
                            "Czas rozpoczęcia rezerwacji: " + reservation.getStart() + "\n" +
                            "Czas zakończenia rezerwacji: " + reservation.getEnd()
            );
            return reservationsRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("Rezerwacja o podanym identyfikatorze nie istnieje: " + id);
        }
    }

    public Reservations rejectReservation(int id) {
        Optional<Reservations> optionalReservation = reservationsRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservations reservation = optionalReservation.get();
            reservationsRepository.delete(reservation);

            emailService.sendMail(
                    reservation.getLecturer_mail(),
                    "Rezerwacja odrzucona",
                    "Twoja rezerwacja sali w serwisie Rezerwacje PZG2 została odrzucona.\n" +
                            "Rezerwacja zawierała następujące dane:\n" +
                            "Sala: " + reservation.getRoom_id() + "\n" +
                            "Data wykonania rezerwacji: " + reservation.getReservation_datetime() + "\n" +
                            "Czas rozpoczęcia rezerwacji: " + reservation.getStart() + "\n" +
                            "Czas zakończenia rezerwacji: " + reservation.getEnd() + "\n\n" +
                            "Przepraszamy za niedogodność i zachęcamy do złożenia rezerwacji w innym terminie."
            );

            return reservation;
        } else {
            throw new IllegalArgumentException("Rezerwacja o podanym identyfikatorze nie istnieje: " + id);
        }
    }

    public void addReservation(Reservations reservation) {
        reservationsRepository.save(reservation);
    }

    public void deleteReservation(Reservations reservation) {
        reservationsRepository.deleteById(reservation.getId());
    }

    public void deleteUsosReservationsForMonth(String lastName, LocalDateTime datetimeFrom, LocalDateTime datetimeTo){
        List<Reservations> reservationsToDelete =
                reservationsRepository.findByLecturerLastNameAndStartBetween(lastName, datetimeFrom, datetimeTo);
        reservationsRepository.deleteAll(reservationsToDelete);
    }

}
