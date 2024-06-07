package org.grupa2.programowaniezespoloweprojekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Prosta klasa Entity reprezentująca rezerwację. Odzwierciedla strukturę tabeli reservations z bazy danych.
 */
@Entity
@Data
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer room_id;
    private LocalDateTime start;
    private LocalDateTime end;
    private String name;
    private String lecturer_mail;
    private String lecturer_first_name;
    private String lecturer_last_name;
    private Integer participants_count;
    private LocalDateTime reservation_datetime;
    private Integer accepted;
}
