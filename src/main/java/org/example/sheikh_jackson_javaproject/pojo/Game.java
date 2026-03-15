// Game.java:

package org.example.sheikh_jackson_javaproject.pojo;

public class Game {

    private final int id;
    private int year;
    private final String developer;
    private String genre;
    private String imageUrl;
    private final String platform;
    private String title;

    public Game(int id, String title, String developer, int year, String genre, String platform, String imageUrl) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.year = year;
        this.genre = genre;
        this.platform = platform;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDeveloper() {
        return developer;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) by %s", title, year, developer);
    }
}
