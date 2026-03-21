// DBConst.java:

package org.example.sheikh_jackson_javaproject.database;

/**
 * Centralized database schema definitions including table names,
 * column names, constraints, and SQL creation statements.
 * This class prevents hardcoding of SQL strings throughout the application,
 * improving maintainability and reducing duplication.
 * Design Choice: Centralizing schema constants ensures consistency
 * across database operations and simplifies future modifications.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class DBConst {

    /**
     * Utility class - should not be instantiated.
     */
    private DBConst() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Game table schema constants.
     * Includes column names and foreign key references used in the "games" table.
     */
    public static final String TABLE_GAME = "games";

    /** Primary key column for games table. */
    public static final String GAME_COLUMN_ID = "id";

    /** Title of the game. */
    public static final String GAME_COLUMN_TITLE = "title";

    /** Release year of the game. */
    public static final String GAME_COLUMN_YEAR = "release_year";

    /** Genre/category of the game. */
    public static final String GAME_COLUMN_GENRE = "genre";

    /** URL/path to game image resource. */
    public static final String GAME_COLUMN_IMAGE = "image_url";

    /** References developer ID from developers table. */
    public static final String GAME_COLUMN_DEV_ID = "developer_id";

    /** References platform ID from platforms table. */
    public static final String GAME_COLUMN_PLAT_ID = "platform_id";

    /**
     * Developer table schema constants.
     */
    public static final String TABLE_DEVELOPER = "developers";

    /** Primary key for developers table. */
    public static final String DEV_COLUMN_ID = "id";

    /** Name of the developer or studio. */
    public static final String DEV_COLUMN_NAME = "name";

    /**
     * Platform table schema constants.
     */
    public static final String TABLE_PLATFORM = "platforms";

    /** Primary key for platforms table. */
    public static final String PLAT_COLUMN_ID = "id";

    /** Name of the platforms or studio. */
    public static final String PLAT_COLUMN_NAME = "platform_name";

    /** Maximum length for game title field. */
    public static final int MAX_TITLE = 200;

    /** Maximum length for genre field. */
    public static final int MAX_GENRE = 1000;

    /** Maximum length for image URL field. */
    public static final int MAX_URL = 5000;

    /**
     * SQL table creation statement for developers table.
     * Executed during database initialization if table does not exist.
     */
    public static final String CREATE_TABLE_DEVELOPER =
            "CREATE TABLE " + TABLE_DEVELOPER + " (" +
                    DEV_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    DEV_COLUMN_NAME + " VARCHAR(100), " +
                    "PRIMARY KEY(" + DEV_COLUMN_ID + "))";

    /**
     * SQL table creation statement for platforms table.
     * Defines schema for supported gaming platforms.
     */
    public static final String CREATE_TABLE_PLATFORM =
            "CREATE TABLE " + TABLE_PLATFORM + " (" +
                    PLAT_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    PLAT_COLUMN_NAME + " VARCHAR(75), " +
                    "PRIMARY KEY(" + PLAT_COLUMN_ID + "))";

    /**
     * SQL table creation statement for games table.
     * Defines the main entity table including foreign key relationships
     * to developers and platforms.
     */
    public static final String CREATE_TABLE_GAMES =
            "CREATE TABLE " + TABLE_GAME + " (" +
                    GAME_COLUMN_ID + " int NOT NULL AUTO_INCREMENT, " +
                    GAME_COLUMN_TITLE + " VARCHAR(200), " +
                    GAME_COLUMN_YEAR + " int, " +
                    GAME_COLUMN_GENRE + " VARCHAR(1000), " +
                    GAME_COLUMN_IMAGE + " VARCHAR(5000), " +
                    GAME_COLUMN_DEV_ID + " int, " +
                    GAME_COLUMN_PLAT_ID + " int, " +
                    "PRIMARY KEY(" + GAME_COLUMN_ID + "))";
}
