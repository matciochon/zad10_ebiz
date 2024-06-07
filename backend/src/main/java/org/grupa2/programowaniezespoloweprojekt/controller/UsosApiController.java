package org.grupa2.programowaniezespoloweprojekt.controller;

import org.grupa2.programowaniezespoloweprojekt.service.UsosApiService;
import org.grupa2.programowaniezespoloweprojekt.util.ScheduleItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsosApiController {

    @Autowired
    private UsosApiService usosApiService;

    @GetMapping("/usos")
    public ResponseEntity<Void> getUsos(@RequestParam("month") int month,
                                        @RequestParam("year") int year
    ) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        List<ScheduleItem> scheduleItemList = usosApiService.fetchDataForMonth( month, year);
        usosApiService.rejectCollidingReservations(scheduleItemList);
        usosApiService.insertMonthIntoDb(scheduleItemList, month, year);

        return ResponseEntity.ok().headers(responseHeaders).body(null);
    }

}
