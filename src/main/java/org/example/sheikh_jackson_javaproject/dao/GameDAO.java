// GameDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.pojo.*;

/**
 * Interface defining data access operations for Game objects.

 * Design Choice:
 * Uses DAO pattern to separate database logic from business logic.

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public interface GameDAO {

    /**
     * Retrieves all games from the data source.

     * @return list of Game objects
     */
    ArrayList<Game> getAllGames();

    /**
     * Deletes a game by its ID.

     * @param id game ID
     */
    void deleteGame(int id);

    /**
     * Updates an existing game.

     * @param game Game object containing updated data
     */
    void updateGame(Game game);
}
