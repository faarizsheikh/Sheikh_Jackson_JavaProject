package org.example.sheikh_jackson_javaproject.tables;

import org.example.sheikh_jackson_javaproject.dao.GameDAO;
import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.pojo.Game;
import java.sql.*;
import java.util.ArrayList;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

public class GameTable implements GameDAO {
    private static GameTable instance;
    Database db = Database.getInstance();
    ArrayList<Game> games;

    private GameTable() {}

    @Override
    public ArrayList<Game> getAllGames() {
        String query = "SELECT * FROM " + TABLE_GAME;
        games = new ArrayList<>();

        try {
            Statement getGames = db.getConnection().createStatement();
            ResultSet rs = getGames.executeQuery(query);
            while (rs.next()) {
                games.add(new Game(
                        rs.getInt(GAME_COLUMN_ID),
                        rs.getString(GAME_COLUMN_TITLE),
                        rs.getString(GAME_COLUMN_DEVELOPER),
                        rs.getInt(GAME_COLUMN_YEAR),
                        rs.getString(GAME_COLUMN_GENRE),
                        rs.getString(GAME_COLUMN_PLATFORM),
                        rs.getString(GAME_COLUMN_IMAGE)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    @Override
    public Game getGame(int id) {
        String query = "SELECT * FROM " + TABLE_GAME + " WHERE " + GAME_COLUMN_ID + " = " + id;

        try {
            Statement getGame = db.getConnection().createStatement();
            ResultSet rs = getGame.executeQuery(query);

            if (rs.next()) {
                return new Game(
                        rs.getInt(GAME_COLUMN_ID),
                        rs.getString(GAME_COLUMN_TITLE),
                        rs.getString(GAME_COLUMN_DEVELOPER),
                        rs.getInt(GAME_COLUMN_YEAR),
                        rs.getString(GAME_COLUMN_GENRE),
                        rs.getString(GAME_COLUMN_PLATFORM),
                        rs.getString(GAME_COLUMN_IMAGE)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GameTable getInstance() {
        if (instance == null) {
            instance = new GameTable();
        }
        return instance;
    }
}