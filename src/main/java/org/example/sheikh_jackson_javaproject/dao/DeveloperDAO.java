// DeveloperDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.pojo.Developer;

/**
 * Data Access Object (DAO) interface for managing Developer entities.
 * This interface defines database access operations for retrieving developer data,
 * ensuring separation between persistence logic and application logic.
 * Design Choice: Provides abstraction over data retrieval to allow
 * flexible implementation (e.g., database, mock data, or API-based sources).
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public interface DeveloperDAO {

    /**
     * Retrieves all developers from the data source.
     *
     * @return list of all Developer objects; empty list if no records exist
     */
    ArrayList<Developer> getAllDevelopers();
}
