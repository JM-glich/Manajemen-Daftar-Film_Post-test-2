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

Nah, bagian ini yang ngurus semua logika CRUD dengan menyimpan semua data film dalam sebuah ArrayList. 
logika CRUD (Create, Read, Update, Delete) dan fitur tambahan seperti pencarian film. package ini juga bertugas sebagai "Controller" yang mengatur cara data diolah. 
Jadi, service jadi tempat mengelola daftar film menggunakan ArrayList. 
``` java
public class MovieService {
    private final List<Movie> movies = new ArrayList<>();
    private int nextId = 1;
```

Contoh logika CRUD:
Tambah
``` java
    // Tambah 
    public Movie create(String title, String genre, int year, double rating) {
        Movie m = new Movie(nextId++, title.trim(), genre.trim(), year, rating);
        movies.add(m);
        return m;
    }
```
Jadi kalau user input film baru, objeknya langsung dimasukin ke daftarFilm. Hal yang sama berlaku juga dengan fitur logika yang lain, misalnya: 
Lihat daftar film, yang di mana program akan menampilkan semua data film yang ada di dalam arraylist.
``` java
    // Lihat
    public List<Movie> getAll() {
        return new ArrayList<>(movies);
    }
```

Fitur Tambahan (Search)
``` java
    // search by title (case-insensitive contains)
    public List<Movie> searchByTitle(String q) {
        if (q == null || q.isBlank()) return new ArrayList<>();
        String key = q.toLowerCase();
        return movies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }
```
Sebuah fitur tambahan di mana user bisa cari film berdasarkan judul. Keyword dicocokkan dengan contains() biar bisa nemu walau ngetiknya sebagian.

Validasi Input
Lalu kemudian juga ada function/method untuk memvalidasi input agar semisalnya user salah memasukan input tidak akan terjadi error, tetapi sebuah pesan khusus.
``` java
    // validation method
    public static List<String> validateInput(String title, String genre, int year, double rating) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.isBlank()) errors.add("Title tidak boleh kosong.");
        if (genre == null || genre.isBlank()) errors.add("Genre tidak boleh kosong.");
        if (year < 1888 || year > 2100) errors.add("Year tidak valid (1888-2100).");
        if (rating < 0.0 || rating > 10.0) errors.add("Rating harus di antara 0.0 - 10.0.");
        return errors;
    }
```

## 3. Package main, Class Main

Ini adalah bagian yang ngurus menu interaktif buat user. Jadi package ini jadi "View" karena yang muncul langsung ke pengguna.
Kenapa di buat? Karena user interface memang dipisahkan dari logika CRUD dan data model, biar lebih jelas alurnya.

Contoh kode yang ada di dalamnya:
Input User
``` java
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
```
Lalu contoh lainnya adalah bagian yang menampilkan Menu:
``` java
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
```
Jadi dari sini user bisa melihat apa saja fitur yang ada di dalam sistem ini. Untuk alurnya nanti akan dijelaskan lebih lanjut.

Beberapa kode tambahan:
``` java
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
```
Kode di atas adalah kode yang berfungsi untuk memvalidasi input user ketika ingin mengupdate sebuah informasi dari suatu film. Semisalnya di situ tahun rilis tidak boleh < 1888 dan atau > 2100.

# Alur Program
1. Program dimulai dari Main (package main). Pertama kali dijalankan, program menampilkan menu utama ke user.
Menu berisi pilihan: tambah film, lihat daftar film, update, hapus, cari, dan keluar.

   <img width="473" height="268" alt="image" src="https://github.com/user-attachments/assets/f03e6c70-f158-4168-9dc8-4ba2d3785fcd" />

2. Kemudian ketika user memasukan input pada menu, input user akan dibaca dengan Scanner. Program menggunakan percabangan (switch-case) untuk memutuskan aksi berdasarkan input tersebut.

3. Jika memilih opsi 1 (Lihat)

    <img width="730" height="280" alt="image" src="https://github.com/user-attachments/assets/215dae87-df8a-4cce-91d1-1f6c6dce4c9b" />

    Program kemudian akan mencari datanya dari MovieService. Semua data film di dalam ArrayList ditampilkan ke layar dalam bentuk list.
4. Jika memilih opsi 2 (Tambah)

   <img width="716" height="323" alt="image" src="https://github.com/user-attachments/assets/90ff0f9f-f804-4104-8dfe-25a0eae2685c" />

    Program akan meminta data film: judul, genre, tahun rilis, rating. Data ini digunakan untuk membuat objek Film (dari package model). Objek film kemudian dikirim ke MovieService (package service) untuk ditambahkan ke dalam daftar ArrayList<Film>.
5. Jika memilih opsi 3 (Update)

     <img width="733" height="351" alt="image" src="https://github.com/user-attachments/assets/801bc694-65f8-4051-abe3-29dbc499c4f4" />

    Program akan meminta ID film yang ingin diupdate. Jika ditemukan, user diminta memasukkan data baru (judul/genre/tahun/rating). Dan setelah selesai, data akan dikirim ke MovieService.
6. Jika memilih opsi 4 (Hapus)

    <img width="597" height="275" alt="image" src="https://github.com/user-attachments/assets/fa0c2cc6-f568-44f5-a27c-7ebe9ebfa81c" />

    Program akan meminta ID dari film yang ingin dihapus dari daftar. Dan kemudian setelah memasukan input, data film akan langsung dihapus.

    <img width="732" height="269" alt="image" src="https://github.com/user-attachments/assets/3a9dc108-42e7-4af9-84d3-1e8b1680f165" />

7. Jika memilih opsi 5 (Cari)

    <img width="714" height="233" alt="image" src="https://github.com/user-attachments/assets/c11f971f-5ba1-4347-a401-25bb5384d5d7" />

    Program meminta input judul film. Kemudian Method searchMovie() dipanggil untuk mencari apakah film ada dalam daftar. Jika ada → tampilkan detail film. Jika tidak → beri pesan bahwa film tidak ditemukan.
8. Jika memilih opsi 0 (Keluar)

    <img width="551" height="293" alt="image" src="https://github.com/user-attachments/assets/da90426f-4c7e-4578-a5d6-7565707a40b4" />

    Saya rasa ini sudah jelas, yaitu program akan berhenti dan user otomatis keluar dari program.
