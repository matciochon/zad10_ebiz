package org.grupa2.programowaniezespoloweprojekt.repository;

import java.util.List;

import org.grupa2.programowaniezespoloweprojekt.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    @Query("SELECT u.id FROM Users u WHERE u.login = :login AND u.password = :password")
    List<Integer> findIdByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
