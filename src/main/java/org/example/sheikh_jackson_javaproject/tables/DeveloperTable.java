// DeveloperTable.java:

package org.example.sheikh_jackson_javaproject.tables;

import java.sql.*;
import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.dao.DeveloperDAO;
import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.pojo.Developer;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

/**
 * Data Access Object implementation for Developer entities.
 * This class handles database operations related to developers,
 * including retrieval of all developer records.
 * Design Choices:
 * Implements the DAO pattern with a Singleton instance to ensure
 * controlled access to database operations.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class DeveloperTable implements DeveloperDAO {

    /**
     * Singleton instance of the Database connection handler used
     * for executing SQL queries related to Developer data.
     */
    private final Database db = Database.getInstance();

    /**
     * Singleton instance of DeveloperTable to ensure a single
     * access point for developer-related database operations.
     */
    private static DeveloperTable instance;

    /**
     * Returns the singleton instance of DeveloperTable.
     *
     * @return DeveloperTable singleton instance
     */
    public static DeveloperTable getInstance(){
        if(instance == null){
            instance = new DeveloperTable();
            Log.info("DeveloperTable singleton created.");
        }
        return instance;
    }

    /**
     * Retrieves all developers from the database.
     *
     * @return list of Developer objects; empty list if no records are found
     */
    @Override
    public ArrayList<Developer> getAllDevelopers() {
        ArrayList<Developer> devs = new ArrayList<>();

        try {
            ResultSet rs = db.getConnection()
                    .createStatement()
                    .executeQuery("SELECT * FROM " + TABLE_DEVELOPER);

            while(rs.next()){
                devs.add(new Developer(
                        rs.getInt(DEV_COLUMN_ID),
                        rs.getString(DEV_COLUMN_NAME)
                ));
            }
            Log.info("Loaded " + devs.size() + " developers.");

        } catch(SQLException e){
            Log.error("Failed to load developers.", e);
        }
        return devs;
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private DeveloperTable(){}
}
