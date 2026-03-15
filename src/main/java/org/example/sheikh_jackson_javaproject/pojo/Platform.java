// Platform.java:

package org.example.sheikh_jackson_javaproject.pojo;

public class Platform {

    private final int id;
    private final String name;

    public Platform(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    @Override
    public String toString() { return name; }
}
