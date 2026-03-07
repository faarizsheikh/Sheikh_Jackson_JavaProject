// DBConst.java:

package org.example.sheikh_jackson_javaproject.database;

public class DBConst {
    // GAMES TABLE
    public static final String TABLE_GAME = "games";
    public static final String GAME_COLUMN_ID = "id";
    public static final String GAME_COLUMN_TITLE = "title";
    public static final String GAME_COLUMN_YEAR = "release_year";
    public static final String GAME_COLUMN_GENRE = "genre";
    public static final String GAME_COLUMN_IMAGE = "image_url";

    // Foreign Keys
    public static final String GAME_COLUMN_DEV_ID = "developer_id";
    public static final String GAME_COLUMN_PLAT_ID = "platform_id";

    // DEVELOPERS TABLE
    public static final String TABLE_DEVELOPER = "developers";
    public static final String DEV_COLUMN_ID = "id";
    public static final String DEV_COLUMN_NAME = "name";

    // PLATFORMS TABLE
    public static final String TABLE_PLATFORM = "platforms";
    public static final String PLAT_COLUMN_ID = "id";
    public static final String PLAT_COLUMN_NAME = "platform_name";

    // CREATE STATEMENTS
    public static final String CREATE_TABLE_DEVELOPER =
            "CREATE TABLE " + TABLE_DEVELOPER + " (" +
                    DEV_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    DEV_COLUMN_NAME + " VARCHAR(100), " +
                    "PRIMARY KEY(" + DEV_COLUMN_ID + "))";

    public static final String CREATE_TABLE_PLATFORM =
            "CREATE TABLE " + TABLE_PLATFORM + " (" +
                    PLAT_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    PLAT_COLUMN_NAME + " VARCHAR(75), " +
                    "PRIMARY KEY(" + PLAT_COLUMN_ID + "))";

    public static final String CREATE_TABLE_GAMES =
            "CREATE TABLE " + TABLE_GAME + " (" +
                    GAME_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    GAME_COLUMN_TITLE + " VARCHAR(200), " +
                    GAME_COLUMN_YEAR + " int, " +
                    GAME_COLUMN_GENRE + " VARCHAR(50), " +
                    GAME_COLUMN_IMAGE + " VARCHAR(300), " +
                    GAME_COLUMN_DEV_ID + " int, " +
                    GAME_COLUMN_PLAT_ID + " int, " +
                    "PRIMARY KEY(" + GAME_COLUMN_ID + "))";
}