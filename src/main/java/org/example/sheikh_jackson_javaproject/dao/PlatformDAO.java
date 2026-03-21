// PlatformDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.pojo.Platform;

/**
 * Data Access Object (DAO) interface for managing Platform entities.
 * This interface defines operations for retrieving platform data,
 * ensuring separation between application logic and data persistence.
 * Design Choice: Provides abstraction over data access to allow
 * flexible implementations (e.g., database-driven or mock data sources).
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public interface PlatformDAO {

    /**
     * Retrieves all platforms from the data source.
     *
     * @return list of all Platform objects; empty list if no records exist
     */
    ArrayList<Platform> getAllPlatforms();
}
