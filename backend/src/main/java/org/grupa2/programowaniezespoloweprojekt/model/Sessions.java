package org.grupa2.programowaniezespoloweprojekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Prosta klasa Entity reprezentująca sesje. Odzwierciedla strukturę tabeli sessions z bazy danych.
 */
@Entity
@Data
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private Integer user_id;
    private LocalDateTime expiration_datetime;

    public Sessions(String uuid, Integer user_id, LocalDateTime expiration_datetime) {
        this.uuid = uuid;
        this.user_id = user_id;
        this.expiration_datetime = expiration_datetime;
    }

    public Sessions() {
    }
}
