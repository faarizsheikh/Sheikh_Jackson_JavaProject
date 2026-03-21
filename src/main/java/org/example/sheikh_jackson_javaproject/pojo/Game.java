// Game.java:

package org.example.sheikh_jackson_javaproject.pojo;

/**
 * Represents a Game entity in the system.
 * This class stores game-related data such as title, developer,
 * platform, genre, and metadata. It provides controlled access
 * through getters and setters.
 * Design Choice: Encapsulation is used to protect data integrity
 * while allowing controlled modification of mutable fields.
 *
 * @author Faariz Sheikh
 * @version 1.0
 * @date 2026-03-17
 */
public class Game {

    private int year;
    private String genre;
    private String imageUrl;
    private String title;
    private final int id;
    private final String developer;
    private final String platform;

    /**
     * Constructs a Game object with all required attributes.
     *
     * @param id unique game identifier
     * @param title game title
     * @param developer developer or studio name
     * @param year release year of the game
     * @param genre game genre/category
     * @param platform platform name
     * @param imageUrl image URL or path for the game
     */
    public Game(int id, String title, String developer, int year, String genre, String platform, String imageUrl) {
        this.id = id;
        this.title = title;
        this.developer = developer;
        this.year = year;
        this.genre = genre;
        this.platform = platform;
        this.imageUrl = imageUrl;
    }

    /**
     * Gets game ID.
     *
     * @return game ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets game title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets developer name.
     *
     * @return developer
     */
    public String getDeveloper() {
        return developer;
    }

    /**
     * Gets release year.
     *
     * @return year
     */

    public int getYear() {
        return year;
    }

    /**
     * Gets game genre.
     *
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Gets platform name.
     *
     * @return platform
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Gets image URL.
     *
     * @return image URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets game title.
     *
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets release year.
     *
     * @param year new year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Sets genre.
     *
     * @param genre new genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Sets image URL.
     *
     * @param imageUrl new image URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Returns a formatted string representation of the game.
     * Format: "Title (Year) by Developer"
     *
     * @return formatted game summary string
     */
    @Override
    public String toString() {
        return String.format("%s (%d) by %s", title, year, developer);
    }
}
