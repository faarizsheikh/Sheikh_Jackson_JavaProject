// PlatformTable.java:

package org.example.sheikh_jackson_javaproject.tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.dao.PlatformDAO;
import org.example.sheikh_jackson_javaproject.database.Database;
import org.example.sheikh_jackson_javaproject.pojo.Platform;
import org.example.sheikh_jackson_javaproject.utils.Log;
import static org.example.sheikh_jackson_javaproject.database.DBConst.*;

public class PlatformTable implements PlatformDAO {

    private static PlatformTable instance;
    private final Database db = Database.getInstance();

    private PlatformTable(){}

    public static PlatformTable getInstance(){
        if(instance == null){
            instance = new PlatformTable();
            Log.info("PlatformTable singleton created.");
        }
        return instance;
    }

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
}
