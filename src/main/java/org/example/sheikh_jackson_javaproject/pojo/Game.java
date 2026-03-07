// Game.java:

package org.example.sheikh_jackson_javaproject.pojo;

public class Game {
    private int id;
    private String title;
    private String developer;
    private int year;
    private String genre;
    private String platform;
    private String imageUrl;

    public Game(int id, String title, String developer, int year, String genre, String platform, String imageUrl) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.year = year;
        this.genre = genre;
        this.platform = platform;
        this.imageUrl = imageUrl;
    }

    // ACCESSORS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() { return title + " (" + platform + ")"; }
}
