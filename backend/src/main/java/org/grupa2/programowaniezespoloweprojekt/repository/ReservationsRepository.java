package org.grupa2.programowaniezespoloweprojekt.repository;

import org.grupa2.programowaniezespoloweprojekt.model.Reservations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Automatycznie generuje klassę zawierającą operacje CRUD wykonywane na Entity Reservations.
 */
public interface ReservationsRepository extends CrudRepository<Reservations, Integer> {
    List<Reservations> findByAccepted(Integer accepted);

    @Query("SELECT r FROM Reservations r WHERE r.room_id = :roomId")
    List<Reservations> findByRoomId(@Param("roomId") Integer roomId);

    @Query("SELECT r FROM Reservations r WHERE r.lecturer_last_name = :lastName AND r.start BETWEEN :datetimeFrom AND :datetimeTo")
    List<Reservations> findByLecturerLastNameAndStartBetween(@Param("lastName") String lastName, @Param("datetimeFrom") LocalDateTime datetimeFrom, @Param("datetimeTo") LocalDateTime datetimeTo);

    default void deleteReservations(List<Reservations> reservationsToDelete) {
        deleteAll(reservationsToDelete);
    }
}
