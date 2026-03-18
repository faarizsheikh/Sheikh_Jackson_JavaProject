// Developer.java:

package org.example.sheikh_jackson_javaproject.pojo;

/**
 * Represents a Developer entity.

 * Design Choice:
 * Simple model class for storing developer data.

 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Developer {

    private final int id;
    private final String name;

    /**
     * Constructs a Developer object.

     * @param id developer ID
     * @param name developer name
     */
    public Developer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets developer ID.

     * @return developer ID
     */
    public int getId() { return id; }

    /**
     * Returns developer name as string.

     * @return developer name
     */
    @Override
    public String toString() { return name; }
}
