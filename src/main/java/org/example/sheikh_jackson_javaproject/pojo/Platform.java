// Platform.java:

package org.example.sheikh_jackson_javaproject.pojo;

/**
 * Represents a Platform entity.

 * Design Choice:
 * Simple model class for storing platform data.

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Platform {

    private final int id;
    private final String name;

    /**
     * Constructs a Platform object.

     * @param id platform ID
     * @param name platform name
     */
    public Platform(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets platform ID.

     * @return platform ID
     */
    public int getId() { return id; }

    /**
     * Returns platform name as string.

     * @return platform name
     */
    @Override
    public String toString() { return name; }
}
