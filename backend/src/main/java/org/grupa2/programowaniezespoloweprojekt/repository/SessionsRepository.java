package org.grupa2.programowaniezespoloweprojekt.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.grupa2.programowaniezespoloweprojekt.model.Sessions;

import java.util.List;
/**
 * Automatycznie generuje klassę zawierającą operacje CRUD wykonywane na Entity Sessions.
 */
public interface SessionsRepository extends CrudRepository<Sessions, Integer> {
    
    @Query("SELECT s FROM Sessions s WHERE s.uuid = :uuid")
    List<Sessions> findByUuid(@Param("uuid") String uuid);
}
