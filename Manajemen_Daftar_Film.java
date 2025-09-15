package com.mycompany.manajemen_daftar_film;

import model.Movie;
import service.MovieService;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point program. Menu CLI sederhana.
 */
public class Manajemen_Daftar_Film {
    private final MovieService service = new MovieService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Manajemen_Daftar_Film().run();
    }

    private void run() {
        // seed sample supaya tampak saat demo
        service.seedSample();

        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listMovies();
                case "2" -> addMovie();
                case "3" -> updateMovie();
                case "4" -> deleteMovie();
                case "5" -> searchMovie();
                case "0" -> {
                    System.out.println("Bye 👋");
                    return;
                }
                default -> System.out.println("Pilihan tidak dikenal. Coba lagi.");
            }
            System.out.println("\n--- Tekan Enter untuk kembali ke menu ---");
            scanner.nextLine();
        }
    }

    private void showMenu() {
        System.out.println("=== Manajemen Daftar Film ===");
        System.out.println("1. Lihat semua film");
        System.out.println("2. Tambah film");
        System.out.println("3. Update film");
        System.out.println("4. Hapus film");
        System.out.println("5. Cari film (by title)");
        System.out.println("0. Keluar");
        System.out.print("Pilih > ");
    }

    private void listMovies() {
        List<Movie> all = service.getAll();
        System.out.println("\n-- Daftar Film --");
        if (all.isEmpty()) {
            System.out.println("Belum ada film.");
            return;
        }
        all.forEach(System.out::println);
    }

    private void addMovie() {
        System.out.println("\n-- Tambah Film --");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Genre: ");
        String genre = scanner.nextLine();
        int year = readInt("Year (e.g. 2020): ");
        double rating = readDouble("Rating (0.0 - 10.0): ");

        var errs = MovieService.validateInput(title, genre, year, rating);
        if (!errs.isEmpty()) {
            System.out.println("Input error:");
            errs.forEach(e -> System.out.println(" - " + e));
            return;
        }
        Movie m = service.create(title, genre, year, rating);
        System.out.println("Berhasil menambah film: " + m);
    }

    private void updateMovie() {
        System.out.println("\n-- Update Film --");
        int id = readInt("Masukkan ID film yang ingin di-update: ");
        var opt = service.findById(id);
        if (opt.isEmpty()) {
            System.out.println("Film dengan ID tersebut tidak ditemukan.");
            return;
        }
        Movie m = opt.get();
        System.out.println("Current : " + m);
        System.out.print("New title (enter = keep): ");
        String title = scanner.nextLine();
        System.out.print("New genre (enter = keep): ");
        String genre = scanner.nextLine();
        Integer year = readIntNullable("New year (enter = keep): ");
        Double rating = readDoubleNullable("New rating (enter = keep): ");

        // basic validation for provided fields
        if (title != null && !title.isBlank() && (title.length() < 1)) {
            System.out.println("Title invalid.");
            return;
        }
        if (year != null && (year < 1888 || year > 2100)) {
            System.out.println("Year invalid.");
            return;
        }
        if (rating != null && (rating < 0.0 || rating > 10.0)) {
            System.out.println("Rating invalid.");
            return;
        }

        boolean ok = service.update(id,
                title.isBlank() ? null : title,
                genre.isBlank() ? null : genre,
                year,
                rating);
        if (ok) System.out.println("Update berhasil.");
        else System.out.println("Update gagal.");
    }

    private void deleteMovie() {
        System.out.println("\n-- Hapus Film --");
        int id = readInt("Masukkan ID film yang ingin dihapus: ");
        boolean ok = service.delete(id);
        System.out.println(ok ? "Hapus berhasil." : "Hapus gagal. ID tidak ditemukan.");
    }

    private void searchMovie() {
        System.out.println("\n-- Cari Film --");
        System.out.print("Masukkan kata kunci title: ");
        String q = scanner.nextLine();
        var list = service.searchByTitle(q);
        if (list.isEmpty()) System.out.println("Tidak ditemukan.");
        else list.forEach(System.out::println);
    }

    // helpers for safe parsing
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String ln = scanner.nextLine().trim();
            try {
                return Integer.parseInt(ln);
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka. Coba lagi.");
            }
        }
    }

    private Integer readIntNullable(String prompt) {
        System.out.print(prompt);
        String ln = scanner.nextLine().trim();
        if (ln.isBlank()) return null;
        try {
            return Integer.parseInt(ln);
        } catch (NumberFormatException e) {
            System.out.println("Format salah — dianggap kosong.");
            return null;
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String ln = scanner.nextLine().trim();
            try {
                return Double.parseDouble(ln);
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka (contoh: 7.5). Coba lagi.");
            }
        }
    }

    private Double readDoubleNullable(String prompt) {
        System.out.print(prompt);
        String ln = scanner.nextLine().trim();
        if (ln.isBlank()) return null;
        try {
            return Double.parseDouble(ln);
        } catch (NumberFormatException e) {
            System.out.println("Format salah — dianggap kosong.");
            return null;
        }
    }
}
