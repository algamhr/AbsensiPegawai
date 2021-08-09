# AbsensiPegawai

Creating an android employee attendance system software with Java which consists of incoming attendance, work attendance, and home attendance where employees can attend attendance via an android smartphone by activating GPS and special encryption tokens for work attendance at certain hours. With Rest API to connect MySQL database with Android Studio.


# Panduan Instalasi

1. Download via zip di github.
2. Folder REST letakkan pada server File Manager. Contoh
    
    AbsensiPegawai/REST/

3. File job.php letakkan pada server File Manager. Contoh

    AbsensiPegawai/job.php

4. File database di upload di dalam mysql.


# Panduan code token & timezone

Untuk code token, code token akan di dapatkan melalui cron job. Cara kerjanya adalah dengan menjalankan script job.php disetiap jam nya. 
Kemudian, untuk timezone sudah di atur dalam file savemasuk.php, savekerja.php, dan savekeluar.php. Pada tiga file tersebut, terdapat code sebagai berikut.

  date_default_timezone_set("Asia/Jakarta");

Untuk timezone sudah diatur di dalam file rest api. Lalu, untuk mengatur jam absensi seperti absensi masuk, absensi kerja, dan absensi pulang, maka bisa diatur pada setiap tiga file yang telah disediakan. Untuk mengatur absensi kerja, penerimaan token secara default adalah 30 menit setelah token diterima untuk melakukan absensi kerja. Untuk menggantinya, silahkan diatur pada file job.php.
