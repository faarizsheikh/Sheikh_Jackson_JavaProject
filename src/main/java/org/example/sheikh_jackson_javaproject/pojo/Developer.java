// Developer.java:

package org.example.sheikh_jackson_javaproject.pojo;

public class Developer {
    private final int id;
    private final String name;

    public Developer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    @Override
    public String toString() { return name; }
}
