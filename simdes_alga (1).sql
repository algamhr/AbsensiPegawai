-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 01, 2021 at 03:41 PM
-- Server version: 10.2.37-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `simdes_alga`
--

-- --------------------------------------------------------

--
-- Table structure for table `absensi`
--

CREATE TABLE `absensi` (
  `id` int(6) NOT NULL,
  `user_id` int(11) NOT NULL,
  `jam` datetime NOT NULL,
  `lokasi_id` int(11) NOT NULL,
  `keterangan` enum('Masuk','Kerja','Pulang') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `absensi`
--

INSERT INTO `absensi` (`id`, `user_id`, `jam`, `lokasi_id`, `keterangan`) VALUES
(1, 2, '2021-02-27 11:20:04', 1, 'Kerja'),
(2, 8, '2021-04-04 11:35:24', 1, 'Kerja'),
(3, 1, '2021-04-04 11:41:27', 6, 'Kerja'),
(4, 11, '2021-04-04 13:23:10', 14, 'Kerja'),
(5, 1, '2021-06-07 07:51:45', 13, 'Masuk'),
(6, 11, '2021-06-07 07:54:06', 13, 'Masuk'),
(7, 11, '2021-06-07 09:01:58', 13, 'Kerja'),
(8, 11, '2021-06-07 10:15:31', 13, 'Kerja'),
(9, 11, '2021-06-07 11:12:46', 1, 'Kerja'),
(10, 11, '2021-06-07 19:39:21', 13, 'Pulang'),
(11, 11, '2021-06-08 08:53:25', 1, 'Masuk'),
(12, 1, '2021-06-08 08:57:20', 1, 'Masuk'),
(13, 11, '2021-06-09 11:01:38', 13, 'Masuk'),
(14, 11, '2021-06-09 11:03:00', 13, 'Kerja'),
(15, 11, '2021-06-27 11:18:16', 13, 'Kerja'),
(16, 11, '2021-06-29 14:12:09', 1, 'Kerja'),
(17, 11, '2021-06-29 14:36:55', 1, 'Pulang'),
(18, 1, '2021-06-29 15:09:06', 5, 'Pulang'),
(20, 2, '2021-06-29 15:13:25', 4, 'Pulang'),
(22, 3, '2021-06-29 15:16:33', 3, 'Pulang'),
(23, 4, '2021-06-29 15:19:23', 2, 'Pulang'),
(24, 11, '2021-06-30 13:23:36', 13, 'Kerja');

-- --------------------------------------------------------

--
-- Table structure for table `lokasi`
--

CREATE TABLE `lokasi` (
  `id` int(6) NOT NULL,
  `lokasi` varchar(50) NOT NULL,
  `area` polygon NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lokasi`
--

INSERT INTO `lokasi` (`id`, `lokasi`, `area`) VALUES
(1, 'FT', 0x000000000103000000010000000500000000000078511c5a40c428aafb16acee3f000080294d1c5a40070a85c011a8ee3fffffff93531c5a40c98a9ed04fa6ee3fffff3f31581c5a40f3245d8998aaee3f00000078511c5a40c428aafb16acee3f),
(2, 'FEKON', 0x0000000001030000000100000005000000ffffbf9f4b1c5a4022bbc0857fa7ee3f00004070461c5a4069646611bba2ee3f000080754c1c5a4096b4d8bab2a1ee3f0000c06c511c5a40d4a10b15d4a5ee3fffffbf9f4b1c5a4022bbc0857fa7ee3f),
(3, 'FKIP', 0x0000000001030000000100000005000000000000fd441c5a400e6a136104a1ee3fffffff213e1c5a40dd3a3492a4a2ee3f00000009391c5a4073ae97dc019eee3f0000c0d83f1c5a40c44b3ca8bb9cee3f000000fd441c5a400e6a136104a1ee3f),
(4, 'FIKP & LAB KIMIA', 0x00000000010300000001000000050000000000a032421c5a4058ed6c650a9dee3f0000602c4c1c5a40604b9c6f2c9aee3f0000e088511c5a4052f9c315c19eee3f0000a0a5471c5a40c7358fcd66a1ee3f0000a032421c5a4058ed6c650a9dee3f),
(5, 'Gedung Rektorat Senggarang', 0x0000000001030000000100000005000000000000d3411c5a40b17971ca7d9cee3f000080c23b1c5a402f7ab4dd619aee3f00000092461c5a40353b617b2197ee3fffff7fa5491c5a40dcf7b59f299aee3f000000d3411c5a40b17971ca7d9cee3f),
(6, 'Gedung Rektorat Dompak', 0x0000000001030000000100000005000000000080c96e1e5a40811fb994d7d8eb3fffff7f7d6f1e5a40975ca4e912d6eb3f0000c033791e5a401344d9bf5ed7eb3f000000d7771e5a40d88bc10a29daeb3f000080c96e1e5a40811fb994d7d8eb3f),
(7, 'Auditorium', 0x0000000001030000000100000005000000ffffff71731e5a40db1c7ec8fcd1eb3f00000026741e5a40c80e5f1d38cfeb3fffffbfba791e5a409d4a6519bfcfeb3f000080fb781e5a4078e202e572d2eb3fffffff71731e5a40db1c7ec8fcd1eb3f),
(8, 'FISIP', 0x00000000010300000001000000050000000000c0bb691e5a40e7ca957540d0eb3f0000c09c6a1e5a4052eaa6e51eceeb3fffffbfd46e1e5a40e34102a29aceeb3f000080e86d1e5a40f39ec790e3d0eb3f0000c0bb691e5a40e7ca957540d0eb3f),
(9, 'FISIP Kelas 1', 0x000000000103000000010000000500000000008040611e5a40af7ff3afffd0eb3f000000de611e5a40f7a634bd3dcfeb3f000080b0691e5a4091f143338fd0eb3f00008056691e5a40d30c552724d2eb3f00008040611e5a40af7ff3afffd0eb3f),
(10, 'FISIP Kelas 2', 0x0000000001030000000100000005000000ffff7ff4611e5a4068e3bf9656d4eb3f0000807b621e5a406aa53241f4d2eb3f0000c072671e5a40ef9f0e9ca2d3eb3f000000ca661e5a40d0b76fd2e8d4eb3fffff7ff4611e5a4068e3bf9656d4eb3f),
(11, 'Kelas Utama Dompak', 0x000000000103000000010000000d000000000080ca8b1e5a40d7e1ba121bd9eb3f00008068841e5a400b7760d85bd8eb3fffff7fef841e5a40333dd682f9d6eb3f0000801f821e5a40571c280689d6eb3fffffbf46841e5a4007eda9e718d2eb3f0000800b871e5a40154baba63ad2eb3f000000d6871e5a402b594870f4d0eb3fffffbf868f1e5a40fbac536802d2eb3f000040bc8e1e5a40725aeaf650d4eb3f000040c2881e5a407c573abbbed3eb3fffff7f92871e5a40d701d1c750d6eb3f0000805f8d1e5a406e36d5c3d7d6eb3f000080ca8b1e5a40d7e1ba121bd9eb3f),
(12, 'Daf Coffe', 0x00000000010300000001000000050000000000e063471e5a407fe670b1be01ed3f00006050441e5a401c941128e302ed3f00008042411e5a40df9993661e01ed3fffff7f99441e5a4011c69051c7ffec3f0000e063471e5a407fe670b1be01ed3f),
(13, 'Rumah', 0x00000000010300000001000000050000000000a012801c5a40b1ff86b90520ee3f000080ad7d1c5a40ec1ddbc2f71eee3f000040be7f1c5a40b1b32bf9401eee3f000040ad811c5a402586742f5a1fee3f0000a012801c5a40b1ff86b90520ee3f),
(14, 'KKB 10', 0x000000000103000000010000000500000000004057a6205a40c8708fbb9177ed3f00008040aa205a403f16815e7073ed3fffff3f6dae205a40424ee5ce4875ed3f000040bcaa205a40bed81eb47278ed3f00004057a6205a40c8708fbb9177ed3f);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(20) NOT NULL,
  `level_id` int(20) NOT NULL,
  `nip` varchar(20) NOT NULL,
  `nidn` varchar(15) NOT NULL,
  `nama` varchar(70) NOT NULL,
  `password` varchar(14) NOT NULL,
  `email` varchar(50) NOT NULL,
  `jenis_kelamin` enum('Laki-laki','Perempuan') NOT NULL,
  `jurusan` varchar(40) NOT NULL,
  `fakultas` varchar(40) NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `fcm_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `level_id`, `nip`, `nidn`, `nama`, `password`, `email`, `jenis_kelamin`, `jurusan`, `fakultas`, `no_hp`, `fcm_token`) VALUES
(1, 2, '199011142019032016', '0014119001', 'Nola Ritha, S.T., M.Cs', 'nola1209', 'nola.ritha@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '085200134361', ''),
(2, 2, '198902222018031001', '0022028903', 'Ferdi Chahyadi, S.Kom., M.Cs', 'ferdi1209', 'ferdi.chahyadi@umrah.ac.id', 'Laki-laki', 'Teknik Informatika', 'Teknik', '082137059571', ''),
(3, 2, '-', '0512048301', 'Eka Suswaini, ST., M.T.', 'eka1209', 'suswaini@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '082172742642', ''),
(4, 2, '-', '0928087304', 'Tekad Matulatan, S.Sos., S.Kom., M.Inf.Tech', 'tekad1209', 'tekad.matulatan@umrah.ac.id', 'Laki-laki', 'Teknik Informatika', 'Teknik', '08194278987', ''),
(5, 2, '198404022014041001', '0002048401', 'Hendra Kurniawan, S.Kom., M.Sc.Eng', 'hendra1209', 'hendra@umrah.ac.id', 'Laki-laki', 'Teknik Informatika', 'Teknik', '085272088484', NULL),
(6, 2, '-', '1028087501', 'Martaleli Bettiza, S.Si., M.Sc.', 'martaleli1209', 'mbettiza@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '081364777280', NULL),
(7, 2, '198302032012122004', '1003028303', 'Nerfita Nikentari, ST., M.Cs.', 'nerfita1209', 'nerfita.nikentari@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '085271323107', NULL),
(8, 2, '-', '0005099001', 'Dwi Amalia Purnamasari, S.T., M.Cs', 'dwi1209', 'dwiamaliaps@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '081231963442', NULL),
(9, 2, '-', '0025038904', 'Muhamad Radzi Rathomi, S.Kom., M.Cs', 'radzi1209', 'radzi@umrah.ac.id', 'Laki-laki', 'Teknik Informatika', 'Teknik', '085272355789', NULL),
(10, 2, '-', '0022068904', 'Alena Uperiati, S.T., M.Cs', 'alena1209', 'alenaup@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '082284070171', NULL),
(11, 1, '199103272019032019', '0027039101', 'Nurul Hayaty, S.T., M.Cs', 'nurul1209', 'nurul.hayaty@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '081275496969', 'e-KBiE6jRUWQxSbUUhYO3w:APA91bGfHOHky00E9BgbXqbTaYFJWzBeanjPzJjdZvD5Xpg2W6VIArwKwYFcyv_xBrEec2lyDr73TL4ZQzEdd_TkD5hIyMADjoiq--9yOS03250bhUNDAiPESaFtgDOOMXwIJ1s7Y92c'),
(12, 2, '-', '0021018401', 'Nurfalinda S.T., M.Cs', 'nurfalinda1209', 'nurfalinda@umrah.ac.id', 'Perempuan', 'Teknik Informatika', 'Teknik', '081365281171', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `userlevel`
--

CREATE TABLE `userlevel` (
  `id` int(6) NOT NULL,
  `level` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userlevel`
--

INSERT INTO `userlevel` (`id`, `level`) VALUES
(1, 'atasan'),
(2, 'pegawai');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `lokasi_id` (`lokasi_id`);

--
-- Indexes for table `lokasi`
--
ALTER TABLE `lokasi`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `level_id` (`level_id`);

--
-- Indexes for table `userlevel`
--
ALTER TABLE `userlevel`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absensi`
--
ALTER TABLE `absensi`
  MODIFY `id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `lokasi`
--
ALTER TABLE `lokasi`
  MODIFY `id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `userlevel`
--
ALTER TABLE `userlevel`
  MODIFY `id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absensi`
--
ALTER TABLE `absensi`
  ADD CONSTRAINT `absensi_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `absensi_ibfk_2` FOREIGN KEY (`lokasi_id`) REFERENCES `lokasi` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`level_id`) REFERENCES `userlevel` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
