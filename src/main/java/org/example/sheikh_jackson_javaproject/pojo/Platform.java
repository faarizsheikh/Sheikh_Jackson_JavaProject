// Platform.java:

package org.example.sheikh_jackson_javaproject.pojo;

/**
 * Represents a Platform entity in the system.
 * This class stores basic platform information such as ID and name.
 * Design Choice: Immutable model class used to ensure data integrity
 * once a Platform object is created.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Platform {

    /** Unique identifier of the platform */
    private final int id;

    /** Name of the platform */
    private final String name;

    /**
     * Constructs a Platform object.
     *
     * @param id unique platform identifier
     * @param name platform name
     */
    public Platform(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets platform ID.
     *
     * @return platform ID
     */
    public int getId() { return id; }

    /**
     * Returns platform name as string representation.
     *
     * @return platform name
     */
    @Override
    public String toString() { return name; }
}
