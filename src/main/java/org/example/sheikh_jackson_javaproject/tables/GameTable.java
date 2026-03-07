// GameTable.java:

package org.example.sheikh_jackson_javaproject.tables;

import org.example.sheikh_jackson_javaproject.dao.*;
import org.example.sheikh_jackson_javaproject.database.*;
import org.example.sheikh_jackson_javaproject.pojo.*;
import java.sql.*;
import java.util.ArrayList;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

public class GameTable implements GameDAO {
    private static GameTable instance;
    Database db = Database.getInstance();
    ArrayList<Game> games;

    private GameTable() {}

    public static GameTable getInstance() {
        if (instance == null) {
            instance = new GameTable();
        }
        return instance;
    }

    public ArrayList<Developer> getAllDevelopers() {
        ArrayList<Developer> devs = new ArrayList<>();

        try {
            ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT * FROM " + TABLE_DEVELOPER);
            while (rs.next()) {
                devs.add(new Developer(rs.getInt(DEV_COLUMN_ID), rs.getString(DEV_COLUMN_NAME)));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return devs;
    }

    public ArrayList<Platform> getAllPlatforms() {
        ArrayList<Platform> plats = new ArrayList<>();

        try {
            ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT * FROM " + TABLE_PLATFORM);
            while (rs.next()) {
                plats.add(new Platform(rs.getInt(PLAT_COLUMN_ID), rs.getString(PLAT_COLUMN_NAME)));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return plats;
    }

    public void addGame(Game game) {
        String query = "INSERT INTO " + TABLE_GAME + "(" +
                GAME_COLUMN_TITLE + "," + GAME_COLUMN_YEAR + "," +
                GAME_COLUMN_GENRE + "," + GAME_COLUMN_IMAGE + "," +
                GAME_COLUMN_DEV_ID + "," + GAME_COLUMN_PLAT_ID + ") VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = db.getConnection().prepareStatement(query);
            pstmt.setString(1, game.getTitle());
            pstmt.setInt(2, game.getYear());
            pstmt.setString(3, game.getGenre());
            pstmt.setString(4, game.getImageUrl());
            pstmt.setInt(5, Integer.parseInt(game.getDeveloper())); // devId
            pstmt.setInt(6, Integer.parseInt(game.getPlatform()));  // platId
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

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
                        rs.getString(GAME_COLUMN_DEV_ID),
                        rs.getInt(GAME_COLUMN_YEAR),
                        rs.getString(GAME_COLUMN_GENRE),
                        rs.getString(GAME_COLUMN_PLAT_ID),
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
                        rs.getString(GAME_COLUMN_DEV_ID),
                        rs.getInt(GAME_COLUMN_YEAR),
                        rs.getString(GAME_COLUMN_GENRE),
                        rs.getString(GAME_COLUMN_PLAT_ID),
                        rs.getString(GAME_COLUMN_IMAGE)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}