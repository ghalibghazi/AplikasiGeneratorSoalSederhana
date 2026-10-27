# Aplikasi Generator Soal Sederhana

Aplikasi berbasis Android untuk membantu proses pembuatan, pengelolaan, dan penggunaan soal secara lebih praktis. Proyek ini dikembangkan menggunakan Kotlin dengan Android Studio sebagai lingkungan pengembangan.

##  Tentang Proyek

Merupakan proyek aplikasi Android yang dirancang untuk mendukung kegiatan penyusunan soal dan pengelolaan materi pembelajaran. Aplikasi ini memiliki beberapa bagian untuk mengelola tulisan, soal, dan siswa, serta menyediakan fitur kuis.

Proyek ini juga menggunakan pustaka Google Generative AI dan iText untuk mendukung fungsionalitas aplikasi.

##  Latar Belakang Proyek

Pentingnya Pendidikan dan berkembangnya Teknologi yang salah satunya adalah Artificial Intelligence (AI) dapat berkolaborasi satu sama lain menciptakan sebuah tekologi yang dapat membantu tenaga pendidik dalam membuat soal yang berkualitas dan memberikan infomasi mengenai hasil belajar peserta didik yang dapat digunakan dalam mengukur perkembangan dan kemajuan yang telah dicapai oleh peserta didik. 

Maka dikembangkan sebuah aplikasi yang dirancang khusus untuk membantu guru membuat soal yang mendorong pengembangan keterampilan berpikir kritis, analitis, dan kreatif pada siswa sehingga proses pembelajaran menjadi lebih bermakna dan interaktif.

##  Fitur

Berdasarkan struktur kode yang tersedia, aplikasi ini mencakup modul-modul berikut:

- **Pengelolaan tulisan** — mengakses dan mengelola materi atau tulisan.
- **Pembuatan soal** — menyediakan modul untuk proses pembuatan soal.
- **Pengelolaan soal** — mengakses dan mengedit soal.
- **Daftar tulisan** — mengelola atau melihat data tulisan.
- **Fitur kuis** — menyediakan aktivitas kuis untuk siswa.
- **Pengelolaan siswa** — menyediakan modul untuk mengakses data siswa.
- **Login dan navigasi** — menyediakan halaman login, beranda, dan pengaturan.
- **Dukungan AI dan PDF** — menggunakan pustaka Google Generative AI dan iText sebagai bagian dari teknologi proyek.

*Catatan: Ketersediaan dan detail setiap fitur bergantung pada implementasi kode aplikasi.*

##  Teknologi yang Digunakan

| Teknologi | Kegunaan |
|---|---|
| Kotlin | Bahasa pemrograman utama |
| Android Studio | Lingkungan pengembangan Android |
| Gradle (Kotlin DSL) | Sistem build dan pengelolaan dependensi |
| Android SDK | Platform pengembangan aplikasi Android |
| Jetpack Compose | Toolkit antarmuka pengguna |
| AndroidX | Komponen pendukung pengembangan Android |
| Google Generative AI | Integrasi layanan AI generatif |
| OkHttp | Komunikasi HTTP |
| Volley | Pengelolaan permintaan jaringan |
| Room | Dukungan pengelolaan basis data lokal |
| iText | Pustaka pengolahan dokumen PDF |

##  Persyaratan

Sebelum menjalankan proyek, pastikan kamu telah menyiapkan:

- Android Studio dengan dukungan Kotlin.
- Android SDK yang sesuai dengan konfigurasi proyek.
- JDK yang kompatibel dengan versi Gradle proyek.
- Koneksi internet jika fitur aplikasi yang digunakan memerlukannya.

Konfigurasi proyek saat ini menggunakan `compileSdk 34` dan `minSdk 24`.

## 📁 Struktur Proyek

Struktur utama repository:

```text
AplikasiGeneratorSoalSederhana/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           ├── res/
│           └── AndroidManifest.xml
├── gradle/
│   └── libs.versions.toml
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── .gitignore
```

Keterangan:

- `app/` — modul utama aplikasi Android.
- `app/src/main/java/` — kode sumber aplikasi.
- `app/src/main/res/` — resource aplikasi, seperti layout, gambar, dan konfigurasi antarmuka.
- `AndroidManifest.xml` — deklarasi komponen aplikasi dan permission.
- `gradle/libs.versions.toml` — katalog versi dan dependensi.
- `build.gradle.kts` — konfigurasi build Gradle.
- `settings.gradle.kts` — konfigurasi proyek dan modul.
- `gradlew` dan `gradlew.bat` — Gradle Wrapper untuk Linux/macOS dan Windows.

## 🧪 Pengujian

Proyek telah menyediakan dependensi untuk pengujian unit dan pengujian instrumentasi Android.

Pengujian dapat dijalankan melalui Android Studio atau menggunakan Gradle Wrapper:

```bash
./gradlew test
```

Untuk Windows:

```bat
gradlew.bat test
```

##  Pengembangan Selanjutnya

Beberapa hal yang dapat dikembangkan lebih lanjut:

- Penyempurnaan proses pembuatan dan pengelolaan soal.
- Peningkatan pengalaman pengguna dan desain antarmuka.
- Pengembangan fitur kuis dan pengelolaan siswa.
- Penyempurnaan ekspor dokumen dan pengelolaan data.
- Penambahan dokumentasi penggunaan dan pengujian otomatis.

##  Pengembang

**Ghalib Ghazi**

- GitHub: [@ghalibghazi](https://github.com/ghalibghazi)
- Repository: [AplikasiGeneratorSoalSederhana](https://github.com/ghalibghazi/AplikasiGeneratorSoalSederhana)

## 📄 Lisensi

Belum ada informasi lisensi yang dicantumkan dalam README ini. Jika ingin membagikan atau mengizinkan penggunaan ulang proyek secara terbuka, tambahkan file `LICENSE` dengan lisensi yang sesuai.

---
