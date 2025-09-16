# Manajemen-Daftar-Film_Post-test-2
## Jemis Movid (2409116070)

## 1. Package model, Class Film/Movie

Di bagian ini kita bikin blueprint dari objek Film. Setiap film punya atribut judul, genre, tahunRilis, dan rating.
Semua atribut ini kita kasih modifier private, supaya gak bisa diutak-atik langsung dari luar class, melainkan lewat method khusus.
Package ini juga fokusnya dibuat untuk data model-objek yang dipakai di program.

Constructor
```java
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
```
Constructor ini akan otomatis jalan sewaktu kita bikin objek baru. Misalnya saat user nambah film, data yang diinput langsung masuk ke sini. 
Keyword this dipakai biar jelas kalau yang dituju itu atribut dalam class.

scatter & getter
``` java
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
```
Getter buat ngambil nilai, setter buat ngubah. Hal ini berlaku juga buat genre, tahun rilis, dan rating.

method toString():
``` java
 @Override
    public String toString() {
        return String.format("ID:%d | %s (%d) | %s | Rating: %.1f", id, title, year, genre, rating);
    }
}
```
Ini bikin data film tampil dengan format teks yang rapi. Jadi pas ditampilkan, user langsung bisa baca detail film dengan mudah.

## 2. Package service, Class MovieService

Nah, bagian ini yang ngurus semua logika CRUD. Dengan menyimpan semua data film dalam sebuah ArrayList:
``` java
public class MovieService {
    private final List<Movie> movies = new ArrayList<>();
    private int nextId = 1;
```

