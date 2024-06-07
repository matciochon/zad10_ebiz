package org.grupa2.programowaniezespoloweprojekt.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleItem {
    private String roomId;
    private String startTime;
    private String endTime;
    private String namePL;
    private String nameEN;

    public ScheduleItem(String roomId, String startTime, String endTime, String namePL, String nameEN) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.namePL = namePL;
        this.nameEN = nameEN;
    }
}
