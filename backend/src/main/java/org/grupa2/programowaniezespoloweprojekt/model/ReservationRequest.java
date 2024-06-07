package org.grupa2.programowaniezespoloweprojekt.model;

import lombok.Data;

@Data
public class ReservationRequest {
    private String start;
    private String end;
    private String name;
    private int room_id;
    private String lecturer_mail;
    private String lecturer_first_name;
    private String lecturer_last_name;
    private int participants_count;
    private String reservation_datetime;
}
