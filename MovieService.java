package service;

import model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class berisi logika CRUD + search + validation.
 */
public class MovieService {
    private final List<Movie> movies = new ArrayList<>();
    private int nextId = 1;

    // Tambah 
    public Movie create(String title, String genre, int year, double rating) {
        Movie m = new Movie(nextId++, title.trim(), genre.trim(), year, rating);
        movies.add(m);
        return m;
    }

    // Lihat
    public List<Movie> getAll() {
        return new ArrayList<>(movies);
    }

    // Mencari berdasarkan id
    public Optional<Movie> findById(int id) {
        return movies.stream().filter(m -> m.getId() == id).findFirst();
    }

    // update
    public boolean update(int id, String title, String genre, Integer year, Double rating) {
        Optional<Movie> opt = findById(id);
        if (opt.isEmpty()) return false;
        Movie m = opt.get();
        if (title != null && !title.isBlank()) m.setTitle(title.trim());
        if (genre != null && !genre.isBlank()) m.setGenre(genre.trim());
        if (year != null) m.setYear(year);
        if (rating != null) m.setRating(rating);
        return true;
    }

    // hapus (delete)
    public boolean delete(int id) {
        return movies.removeIf(m -> m.getId() == id);
    }

    // search by title (case-insensitive contains)
    public List<Movie> searchByTitle(String q) {
        if (q == null || q.isBlank()) return new ArrayList<>();
        String key = q.toLowerCase();
        return movies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }

    // validation method
    public static List<String> validateInput(String title, String genre, int year, double rating) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.isBlank()) errors.add("Title tidak boleh kosong.");
        if (genre == null || genre.isBlank()) errors.add("Genre tidak boleh kosong.");
        if (year < 1888 || year > 2100) errors.add("Year tidak valid (1888-2100).");
        if (rating < 0.0 || rating > 10.0) errors.add("Rating harus di antara 0.0 - 10.0.");
        return errors;
    }

    // seed sample data (optional)
    public void seedSample() {
        create("Interstellar", "Sci-Fi", 2014, 8.6);
        create("Parasite", "Drama/Thriller", 2019, 8.6);
        create("The Matrix", "Sci-Fi", 1999, 8.7);
    }
}

