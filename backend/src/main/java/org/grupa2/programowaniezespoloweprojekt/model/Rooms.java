package org.grupa2.programowaniezespoloweprojekt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Prosta klasa Entity reprezentująca pokój. Odzwierciedla strukturę tabeli rooms z bazy danych.
 */
@Entity
@Data
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String capacity;
}
