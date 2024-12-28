-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 11, 2024 at 08:30 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `DigiLib`
--

-- --------------------------------------------------------

--
-- Table structure for table `Buku`
--

CREATE TABLE `Buku` (
  `idBuku` int(11) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `penulis` varchar(100) DEFAULT NULL,
  `tahunTerbit` int(11) DEFAULT NULL,
  `kategori` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Buku`
--

INSERT INTO `Buku` (`idBuku`, `judul`, `penulis`, `tahunTerbit`, `kategori`) VALUES
(1, 'The Power of Habit', 'Charles Duhigg', 2012, 'Self-Development'),
(2, 'Atomic Habit', 'James Clear', 2018, 'Self-Development'),
(3, 'The Incredible Habits', 'Dewi Indra', 2020, 'Self-Development'),
(4, 'Habit Training', 'Asti Musman', 2024, 'Self-Development'),
(5, 'Sucess Habits', 'Napoleon Hill', 2022, 'Self-Improvement'),
(6, 'The Habit Journal', 'James Clear', 2022, 'Self-Improvement'),
(7, 'The Psychology of Money', 'Housel Morgan', 2020, 'Economics'),
(8, 'The Psychology of Emotion', 'David J. Lieberman', 2023, 'Psychology'),
(9, 'Dark Psychology', 'Novita WD', 2024, 'Psychology'),
(10, 'Crypto Trading Psychology', 'Akademi Crypto', 2024, 'Business & Economy'),
(11, 'Psychology of Winning', 'Dennis Waitley', 2018, 'Psychology'),
(12, 'Food Combining', 'Andang W. Gunawan', 2019, 'Food-Drink-CookBook'),
(13, 'Functional Food', 'Drs Djoko Sutopo, MS', 2019, 'Health'),
(14, 'The Kamogawa Food Detectives', 'Hisashi Kashiwai', 2024, 'Mystery'),
(15, 'Why? Food & Nutrition', 'Alex Media Komputindo', 2017, 'Health'),
(16, 'The Business School', 'Robert T. Kiyosaki', 2023, 'Business & Economy'),
(17, 'Business Made Simple', 'Donald Miller', 2024, 'Business & Economy'),
(18, 'Testing Business Ideas', 'David J. Bland', 2021, 'Business'),
(19, 'Lean Business Improvement', 'Wawang Sukmoro', 2021, 'Business'),
(20, 'Online Business Golden Book', 'Try Prasetyawan', 2022, 'Business');

-- --------------------------------------------------------

--
-- Table structure for table `pengunjungPerpustakaan`
--

CREATE TABLE `pengunjungPerpustakaan` (
  `idPengunjung` int(11) NOT NULL,
  `namaPengunjung` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `noTelp` varchar(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pengunjungPerpustakaan`
--

INSERT INTO `pengunjungPerpustakaan` (`idPengunjung`, `namaPengunjung`, `alamat`, `noTelp`) VALUES
(232310003, 'Luthfi', 'Jl. Mawar No. 3', '08234567890'),
(232310016, 'Azzahra', 'Jl. Dahlia No. 9', '08123456789'),
(232310025, 'Steven', 'Jl. Tulip No. 2', '08345678901');

-- --------------------------------------------------------

--
-- Table structure for table `Status`
--

CREATE TABLE `Status` (
  `idTransaksi` int(11) NOT NULL,
  `idBuku` int(11) NOT NULL,
  `idPengunjung` int(11) DEFAULT NULL,
  `tanggal_pinjam` date DEFAULT NULL,
  `tanggal_kembali` date DEFAULT NULL,
  `status_pinjam` enum('Dipinjam','Terlambat','Belum Dipinjam') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `Status`
--

INSERT INTO `Status` (`idTransaksi`, `idBuku`, `idPengunjung`, `tanggal_pinjam`, `tanggal_kembali`, `status_pinjam`) VALUES
(1, 1, NULL, NULL, NULL, 'Belum Dipinjam'),
(2, 2, NULL, NULL, NULL, 'Belum Dipinjam'),
(3, 3, NULL, NULL, NULL, 'Belum Dipinjam'),
(4, 4, NULL, NULL, NULL, 'Belum Dipinjam'),
(5, 5, NULL, NULL, NULL, 'Belum Dipinjam'),
(6, 6, NULL, NULL, NULL, 'Belum Dipinjam'),
(7, 7, NULL, NULL, NULL, 'Belum Dipinjam'),
(8, 8, NULL, NULL, NULL, 'Belum Dipinjam'),
(9, 9, 232310016, '2024-12-11', '2024-12-17', 'Dipinjam'),
(10, 10, NULL, NULL, NULL, 'Belum Dipinjam'),
(11, 11, NULL, NULL, NULL, 'Belum Dipinjam'),
(12, 12, NULL, NULL, NULL, 'Belum Dipinjam'),
(13, 13, NULL, NULL, NULL, 'Belum Dipinjam'),
(14, 14, NULL, NULL, NULL, 'Belum Dipinjam'),
(15, 15, 232310003, '2024-12-12', '2024-12-18', 'Dipinjam'),
(16, 16, NULL, NULL, NULL, 'Belum Dipinjam'),
(17, 17, NULL, NULL, NULL, 'Belum Dipinjam'),
(18, 18, NULL, NULL, NULL, 'Belum Dipinjam'),
(19, 19, NULL, NULL, NULL, 'Belum Dipinjam'),
(20, 20, 232310025, '2024-12-12', '2024-12-18', 'Dipinjam');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Buku`
--
ALTER TABLE `Buku`
  ADD PRIMARY KEY (`idBuku`);

--
-- Indexes for table `pengunjungPerpustakaan`
--
ALTER TABLE `pengunjungPerpustakaan`
  ADD PRIMARY KEY (`idPengunjung`);

--
-- Indexes for table `Status`
--
ALTER TABLE `Status`
  ADD PRIMARY KEY (`idTransaksi`),
  ADD KEY `idBuku` (`idBuku`),
  ADD KEY `idPengunjung` (`idPengunjung`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Status`
--
ALTER TABLE `Status`
  ADD CONSTRAINT `status_ibfk_1` FOREIGN KEY (`idBuku`) REFERENCES `Buku` (`idBuku`),
  ADD CONSTRAINT `status_ibfk_2` FOREIGN KEY (`idPengunjung`) REFERENCES `pengunjungPerpustakaan` (`idPengunjung`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
