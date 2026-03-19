// PlatformDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.pojo.Platform;

/**
 * Interface defining data access operations for Platform objects.
 * Design Choice:
 * Provides abstraction for retrieving platform data.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public interface PlatformDAO {

    /**
     * Retrieves all platforms from the data source.
     *
     * @return list of Platform objects
     */
    ArrayList<Platform> getAllPlatforms();
}
