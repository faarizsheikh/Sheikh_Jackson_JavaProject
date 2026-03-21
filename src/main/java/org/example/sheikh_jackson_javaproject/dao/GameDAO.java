// GameDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.pojo.Game;

/**
 * Data Access Object (DAO) interface for managing Game entities.
 * This interface defines standard database operations for Game objects,
 * separating persistence logic from business logic.
 * Design Choice: Implements the DAO pattern to ensure loose coupling
 * between the application and the data layer.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public interface GameDAO {

    /**
     * Retrieves all games from the data source.
     *
     * @return list of all Game objects; empty list if none exist
     */
    ArrayList<Game> getAllGames();

    /**
     * Deletes a game by its unique identifier.
     *
     * @param id the ID of the game to delete
     */
    void deleteGame(int id);

    /**
     * Updates an existing game record in the data source.
     *
     * @param game Game object containing updated field values
     */
    void updateGame(Game game);
}
