// DeveloperDAO.java:

package org.example.sheikh_jackson_javaproject.dao;

import java.util.ArrayList;
import org.example.sheikh_jackson_javaproject.pojo.Developer;

/**
 * Interface defining data access operations for Developer objects.

 * Design Choice:
 * Provides abstraction for retrieving developer data.

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public interface DeveloperDAO {

    /**
     * Retrieves all developers from the data source.
     *
     * @return list of Developer objects
     */
    ArrayList<Developer> getAllDevelopers();
}
