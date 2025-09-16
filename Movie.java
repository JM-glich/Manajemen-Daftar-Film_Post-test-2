// Nama: Jemis Movid
// NIM: 2409116070

package model;

/**
 * Model class untuk menyimpan atribut Movie.
 */
public class Movie {
    // properties (access modifiers: private)
    private int id;
    private String title;
    private String genre;
    private int year;
    private double rating; // 0.0 - 10.0

    // constructor
    public Movie(int id, String title, String genre, int year, double rating) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    // getters & setters (encapsulation)
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
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format("ID:%d | %s (%d) | %s | Rating: %.1f", id, title, year, genre, rating);
    }
}


