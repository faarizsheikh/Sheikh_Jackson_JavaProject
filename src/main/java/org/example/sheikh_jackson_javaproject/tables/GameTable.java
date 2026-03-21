// GameTable.java:

package org.example.sheikh_jackson_javaproject.tables;

import java.sql.*;
import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.dao.GameDAO;
import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

/**
 * Data Access Object implementation for Game entities.
 * This class handles all database operations related to games,
 * including retrieval, insertion, updating, and deletion.
 * Design Choices:
 * Implements the DAO pattern with a Singleton instance and uses
 * PreparedStatements to ensure secure SQL execution.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class GameTable implements GameDAO {

    /**
     * Singleton instance of the Database connection handler used
     * to execute all SQL operations in this DAO.
     */
    private final Database db = Database.getInstance();

    /**
     * Singleton instance of GameTable to ensure only one DAO
     * object manages game-related database operations.
     */
    private static GameTable instance;

    /**
     * Private constructor to prevent instantiation.
     */
    private GameTable() {}

    /**
     * Returns the single instance of GameTable.
     *
     * @return GameTable instance
     */
    public static GameTable getInstance() {
        if (instance == null) {
            instance = new GameTable();
            Log.info("GameTable singleton created.");
        }
        return instance;
    }

    /**
     * Retrieves all games from the database, including associated developer
     * and platform information using SQL joins.
     *
     * @return list of Game objects; empty list if no records are found
     */
    @Override
    public ArrayList<Game> getAllGames() {
        String query =
                "SELECT g.*, d." + DEV_COLUMN_NAME + ", p." + PLAT_COLUMN_NAME +
                        " FROM " + TABLE_GAME + " g " +
                        " JOIN " + TABLE_DEVELOPER + " d ON g." + GAME_COLUMN_DEV_ID + " = d." + DEV_COLUMN_ID +
                        " JOIN " + TABLE_PLATFORM + " p ON g." + GAME_COLUMN_PLAT_ID + " = p." + PLAT_COLUMN_ID;

        ArrayList<Game> games = new ArrayList<>();

        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                games.add(new Game(
                        rs.getInt(GAME_COLUMN_ID),
                        rs.getString(GAME_COLUMN_TITLE),
                        rs.getString(DEV_COLUMN_NAME),
                        rs.getInt(GAME_COLUMN_YEAR),
                        rs.getString(GAME_COLUMN_GENRE),
                        rs.getString(PLAT_COLUMN_NAME),
                        rs.getString(GAME_COLUMN_IMAGE)
                ));
            }
            Log.info("Loaded " + games.size() + " games from database.");

        } catch (SQLException e) {
            Log.error("Failed to load games.", e);
        }
        return games;
    }

    /**
     * Inserts a new game record into the database.
     *
     * @param game Game object containing the data to be inserted
     */
    public void addGame(Game game) {
        String query =
                "INSERT INTO " + TABLE_GAME +
                        "(" + GAME_COLUMN_TITLE + "," + GAME_COLUMN_YEAR + "," +
                        GAME_COLUMN_GENRE + "," + GAME_COLUMN_IMAGE + "," +
                        GAME_COLUMN_DEV_ID + "," + GAME_COLUMN_PLAT_ID + ") VALUES (?,?,?,?,?,?)";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, game.getTitle());
            pstmt.setInt(2, game.getYear());
            pstmt.setString(3, game.getGenre());
            pstmt.setString(4, game.getImageUrl());
            pstmt.setInt(5, Integer.parseInt(game.getDeveloper()));
            pstmt.setInt(6, Integer.parseInt(game.getPlatform()));
            pstmt.executeUpdate();
            Log.info("Game added: " + game.getTitle());

        } catch (SQLException e) {
            Log.error("Failed to add game: " + game.getTitle(), e);
        }
    }

    /**
     * Updates an existing game record in the database.
     *
     * @param game Game object containing updated values
     */
    @Override
    public void updateGame(Game game) {
        String query =
                "UPDATE " + TABLE_GAME + " SET " +
                        GAME_COLUMN_TITLE + "=?, " +
                        GAME_COLUMN_YEAR + "=?, " +
                        GAME_COLUMN_GENRE + "=?, " +
                        GAME_COLUMN_IMAGE + "=? " +
                        "WHERE " + GAME_COLUMN_ID + "=?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setString(1, game.getTitle());
            pstmt.setInt(2, game.getYear());
            pstmt.setString(3, game.getGenre());
            pstmt.setString(4, game.getImageUrl());
            pstmt.setInt(5, game.getId());
            pstmt.executeUpdate();
            Log.info("Game updated: " + game.getTitle());

        } catch (SQLException e) {
            Log.error("Failed to update game ID=" + game.getId(), e);
        }
    }

    /**
     * Deletes a game from the database using its unique ID.
     *
     * @param id unique identifier of the game to delete
     */
    @Override
    public void deleteGame(int id) {
        String query =
                "DELETE FROM " + TABLE_GAME +
                        " WHERE " + GAME_COLUMN_ID + " = ?";

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            Log.info("Game deleted (ID=" + id + ")");

        } catch (SQLException e) {
            Log.error("Failed to delete game ID=" + id, e);
        }
    }
}
