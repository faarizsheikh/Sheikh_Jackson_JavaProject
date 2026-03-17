// DeveloperTable.java:

package org.example.sheikh_jackson_javaproject.tables;

import java.sql.*;
import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.dao.DeveloperDAO;
import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.pojo.Developer;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

public class DeveloperTable implements DeveloperDAO {

    private final Database db = Database.getInstance();
    private static DeveloperTable instance;

    private DeveloperTable(){}

    public static DeveloperTable getInstance(){
        if(instance == null){
            instance = new DeveloperTable();
            Log.info("DeveloperTable singleton created.");
        }
        return instance;
    }

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
}
