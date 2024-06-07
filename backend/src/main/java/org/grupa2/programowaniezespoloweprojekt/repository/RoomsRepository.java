package org.grupa2.programowaniezespoloweprojekt.repository;

import org.springframework.data.repository.CrudRepository;
import org.grupa2.programowaniezespoloweprojekt.model.Rooms;

/**
 * Automatycznie generuje klassę zawierającą operacje CRUD wykonywane na Entity Rooms.
 */
public interface RoomsRepository extends CrudRepository<Rooms, Integer> {

}
