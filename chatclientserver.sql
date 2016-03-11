-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2016 at 06:14 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `chatclientserver`
--

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE IF NOT EXISTS `clients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(10) DEFAULT NULL,
  `ip_address` varchar(20) DEFAULT NULL,
  `port` varchar(6) DEFAULT NULL,
  `is_online` varchar(1) DEFAULT '0',
  `gport` varchar(7) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`id`, `username`, `password`, `ip_address`, `port`, `is_online`, `gport`) VALUES
(1, 'test', 'test', '192.168.56.1', '8871', '0', '8872'),
(2, 'silver', 'silver', '192.168.56.1', '8873', '0', '8875'),
(3, 'aditya', 'aditya', '192.168.56.1', '8867', '0', '8872'),
(4, 'sunny', 'sunny', '192.168.56.1', '8868', '0', '8870'),
(5, 'scar', 'scar', NULL, NULL, '0', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `friend_list`
--

CREATE TABLE IF NOT EXISTS `friend_list` (
  `friend1` varchar(20) DEFAULT NULL,
  `friend2` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friend_list`
--

INSERT INTO `friend_list` (`friend1`, `friend2`) VALUES
('silver', 'aditya'),
('aditya', 'silver'),
('aditya', 'test'),
('test', 'aditya'),
('silver', 'test'),
('test', 'silver'),
('aditya', 'sunny'),
('sunny', 'aditya'),
('aditya', 'scar'),
('scar', 'aditya');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
