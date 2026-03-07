package org.example.sheikh_jackson_javaproject.database;

public class DBConst {
    public static final String TABLE_GAME = "games";
    public static final String GAME_COLUMN_ID = "id";
    public static final String GAME_COLUMN_TITLE = "title";
    public static final String GAME_COLUMN_DEVELOPER = "developer";
    public static final String GAME_COLUMN_YEAR = "release_year";
    public static final String GAME_COLUMN_GENRE = "genre";
    public static final String GAME_COLUMN_PLATFORM = "platform";
    public static final String GAME_COLUMN_IMAGE = "image_url";

    public static final String CREATE_TABLE_GAMES =
            "CREATE TABLE " + TABLE_GAME + " (" +
                    GAME_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    GAME_COLUMN_TITLE + " VARCHAR(100), " +
                    GAME_COLUMN_DEVELOPER + " VARCHAR(100), " +
                    GAME_COLUMN_YEAR + " int, " +
                    GAME_COLUMN_GENRE + " VARCHAR(50), " +
                    GAME_COLUMN_PLATFORM + " VARCHAR(50), " +
                    GAME_COLUMN_IMAGE + " VARCHAR(255), " +
                    "PRIMARY KEY(" + GAME_COLUMN_ID + "))";
}