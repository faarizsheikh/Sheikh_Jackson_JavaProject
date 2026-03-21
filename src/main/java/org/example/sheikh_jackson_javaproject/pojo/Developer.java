// Developer.java:

package org.example.sheikh_jackson_javaproject.pojo;

/**
 * Represents a Developer entity in the system.
 * This class stores basic developer information such as ID and name.
 * Design Choice: Immutable model class used to ensure data integrity
 * once a Developer object is created.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Developer {

    private final int id;
    private final String name;

    /**
     * Constructs a Developer object.
     *
     * @param id unique developer identifier
     * @param name developer name
     */
    public Developer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets developer ID.
     *
     * @return developer ID
     */
    public int getId() { return id; }

    /**
     * Returns developer name as string representation.
     *
     * @return developer name
     */
    @Override
    public String toString() { return name; }
}
