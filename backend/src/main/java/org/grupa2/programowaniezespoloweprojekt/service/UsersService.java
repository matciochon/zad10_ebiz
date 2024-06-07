package org.grupa2.programowaniezespoloweprojekt.service;

import org.grupa2.programowaniezespoloweprojekt.model.Users;
import org.grupa2.programowaniezespoloweprojekt.model.Sessions;
import org.grupa2.programowaniezespoloweprojekt.repository.SessionsRepository;
import org.grupa2.programowaniezespoloweprojekt.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Klasa Service zawierająca logikę biznesową związaną z tabelą users.
 */
@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private SessionsRepository sessionsRepository;

    public List<Users> getAllUsers() {
        return (List<Users>) usersRepository.findAll();
    }

    public String login(String login, String password) {
        List<Integer> users = usersRepository.findIdByLoginAndPassword(login, password);
        if (users.size() == 1) {
            return createSession(users.get(0));
        } else {
            return "";
        }
    }

    private String createSession(Integer userId) {
        LocalDateTime expiration_datetime = LocalDateTime.now().plusDays(1);
        String uuid = UUID.randomUUID().toString();

        Sessions session = new Sessions(uuid, userId, expiration_datetime);

        sessionsRepository.save(session);

        return uuid;
    }

    public List<Sessions> getAllSessions() {
        return (List<Sessions>) sessionsRepository.findAll();
    }

    public Integer checkSession(String uuid) {
        List<Sessions> sessions = sessionsRepository.findByUuid(uuid);
        if (sessions.size() == 1) {

            Sessions sesssion = sessions.get(0);
            if (sesssion.getExpiration_datetime().isAfter(LocalDateTime.now())) {
                return sesssion.getUser_id();
            } else {
                return -1;
            }

        } else {
            return -1;
        }

    } 

    // Tu dodawać w razie potrzeby inne metody logiki biznesowej

}
