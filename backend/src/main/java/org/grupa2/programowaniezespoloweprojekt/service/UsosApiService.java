package org.grupa2.programowaniezespoloweprojekt.service;

import oauth.signpost.OAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.grupa2.programowaniezespoloweprojekt.config.OAuthConfig;
import org.grupa2.programowaniezespoloweprojekt.model.Reservations;
import org.grupa2.programowaniezespoloweprojekt.model.Rooms;
import org.grupa2.programowaniezespoloweprojekt.repository.ReservationsRepository;
import org.grupa2.programowaniezespoloweprojekt.repository.RoomsRepository;
import org.grupa2.programowaniezespoloweprojekt.util.ScheduleItem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UsosApiService {

    @Autowired
    OAuthConfig oAuthConfig;

    @Autowired
    private ReservationsService reservationsService;

    @Autowired
    private RoomsService roomsService;

    private String fetchRoomDataForDate(String roomId, String date, String days) throws Exception {

        String url = "https://apps.usos.uj.edu.pl/services/tt/room";
        url += "?room_id=" + roomId + "&start=" + date + "&days=" + days;

        OAuthConsumer consumer = oAuthConfig.getOAuthConsumer();

        consumer.setTokenWithSecret("", "");
        consumer.setAdditionalParameters(null);
        consumer.setSendEmptyTokens(true);

        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        consumer.sign(request);
        HttpResponse response = client.execute(request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    public void rejectCollidingReservations(List<ScheduleItem> usosItemList) throws Exception {
        List<Reservations> notAcceptedReservationsList = reservationsService.getAllNotAcceptedReservations();

        for (Reservations pendingReservation : notAcceptedReservationsList) {
            for (ScheduleItem usosItem : usosItemList) {
                if (pendingReservation.getRoom_id().toString().equals(usosItem.getRoomId())) {
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime usosItemStartTime = LocalDateTime.parse(usosItem.getStartTime(), dateTimeFormatter);
                    LocalDateTime usosItemEndTime = LocalDateTime.parse(usosItem.getEndTime(), dateTimeFormatter);
                    if (pendingReservation.getStart().isBefore(usosItemEndTime)
                            && pendingReservation.getEnd().isAfter(usosItemStartTime)) {
                        reservationsService.deleteReservation(pendingReservation);
                        break;
                    }
                }
            }
        }
    }

    public List<ScheduleItem> fetchDataForMonth(int month, int year) throws Exception {

        List<ScheduleItem> allScheduleItemsList = new ArrayList<>();
        Iterable<Rooms> rooms = roomsService.getAllRooms();

        for(Rooms room : rooms) {
            List<ScheduleItem> singleRoomList = fetchRoomDataForMonth(room.getId().toString(), month, year);
            allScheduleItemsList.addAll(singleRoomList);
        }

        return allScheduleItemsList;
    }

    public List<ScheduleItem> fetchRoomDataForMonth(String roomId, int month, int year) throws Exception {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        StringBuilder result = new StringBuilder();

        // Weź tyle dni ile pozostało w miesiącu po poprzednim zapytaniu, ale maksymalnie 7
        int daysFetched = 0;
        int daysToFetch = Math.min(endDate.getDayOfMonth() - daysFetched, 7);

        while(daysFetched < endDate.getDayOfMonth()) {
            LocalDate daysFetchedDate = LocalDate.of(year, month, daysFetched + 1);
            result.append(fetchRoomDataForDate(roomId, dateTimeFormatter.format(daysFetchedDate), String.valueOf(daysToFetch)));
            daysFetched += daysToFetch;
            daysToFetch = Math.min(endDate.getDayOfMonth() - daysFetched, 7);
        }

        String jsonString = result.toString()
                .replace("][", ", ");

        List<ScheduleItem> deserializedJson = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(jsonString);

            for (Object obj : jsonArray) {
                JSONObject jsonObj = (JSONObject) obj;

                String startTime = (String) jsonObj.get("start_time");
                String endTime = (String) jsonObj.get("end_time");
                JSONObject nameObj = (JSONObject) jsonObj.get("name");
                String namePL = (String) nameObj.get("pl");
                String nameEN = (String) nameObj.get("en");

                ScheduleItem scheduleItem = new ScheduleItem(roomId, startTime, endTime, namePL, nameEN);
                deserializedJson.add(scheduleItem);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return deserializedJson;
    }

    public void insertScheduleItemIntoDb(ScheduleItem scheduleItem){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Reservations reservation = new Reservations();

        reservation.setRoom_id(Integer.parseInt(scheduleItem.getRoomId()));
        reservation.setStart(LocalDateTime.parse(scheduleItem.getStartTime(), dateTimeFormatter));
        reservation.setEnd(LocalDateTime.parse(scheduleItem.getEndTime(), dateTimeFormatter));
        reservation.setName(scheduleItem.getNamePL());
        reservation.setLecturer_mail("USOS");
        reservation.setLecturer_first_name("USOS");
        reservation.setLecturer_last_name("USOS");
        reservation.setParticipants_count(0);
        reservation.setReservation_datetime(LocalDateTime.now());
        reservation.setAccepted(1);

        reservationsService.addReservation(reservation);
    }

    public void insertMonthIntoDb(List<ScheduleItem> scheduleItems, int month, int year){

        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusDays(1);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Usuwa wszystkie istniejące rekordy z USOSa dla danego miesiąca
        reservationsService.deleteUsosReservationsForMonth(
                "USOS",
                startDate,
                endDate
        );

        // Dodaje do bazy nowe rekordy z listy
        for(ScheduleItem scheduleItem : scheduleItems){
            insertScheduleItemIntoDb(scheduleItem);
        }
    }
}
