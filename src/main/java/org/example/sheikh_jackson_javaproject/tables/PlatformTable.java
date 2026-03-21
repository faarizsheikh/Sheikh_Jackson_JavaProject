// PlatformTable.java:

package org.example.sheikh_jackson_javaproject.tables;

import java.sql.*;
import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.dao.PlatformDAO;
import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.pojo.Platform;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

/**
 * Data Access Object implementation for Platform entities.
 * This class handles database operations related to platforms,
 * including retrieval of all platform records.
 * Design Choices:
 * Implements the DAO pattern with a Singleton instance to ensure
 * controlled and consistent database access.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class PlatformTable implements PlatformDAO {

    /**
     * Singleton instance of the Database connection handler used
     * for executing SQL queries related to Platform data.
     */
    private final Database db = Database.getInstance();

    /**
     * Singleton instance of PlatformTable to ensure a single
     * access point for platform-related database operations.
     */
    private static PlatformTable instance;

    /**
     * Returns the singleton instance of PlatformTable.
     *
     * @return PlatformTable singleton instance
     */
    public static PlatformTable getInstance(){
        if(instance == null){
            instance = new PlatformTable();
            Log.info("PlatformTable singleton created.");
        }
        return instance;
    }

    /**
     * Retrieves all platforms from the database.
     *
     * @return list of Platform objects; empty list if no records are found
     */
    @Override
    public ArrayList<Platform> getAllPlatforms() {
        ArrayList<Platform> plats = new ArrayList<>();

        try {
            ResultSet rs = db.getConnection()
                    .createStatement()
                    .executeQuery("SELECT * FROM " + TABLE_PLATFORM);

            while(rs.next()){
                plats.add(new Platform(
                        rs.getInt(PLAT_COLUMN_ID),
                        rs.getString(PLAT_COLUMN_NAME)
                ));
            }
            Log.info("Loaded " + plats.size() + " platforms.");

        } catch(SQLException e){
            Log.error("Failed to load platforms.", e);
        }
        return plats;
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private PlatformTable(){}
}
