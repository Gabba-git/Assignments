-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 29, 2016 at 07:52 PM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cms`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `complaint_id` int(11) NOT NULL,
  `body` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`comment_id`, `user_id`, `complaint_id`, `body`, `created_at`) VALUES
(1, 'ee1130455', 4, 'Are you serious?', '2016-03-29 14:39:10'),
(2, 'ee1130453', 4, 'No, it is very good.', '2016-03-29 15:43:00'),
(3, 'ee1130453', 4, 'No, it is very good.', '2016-03-29 15:43:22');

-- --------------------------------------------------------

--
-- Table structure for table `complaints`
--

CREATE TABLE `complaints` (
  `complaint_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `body` text NOT NULL,
  `upvote` int(11) NOT NULL DEFAULT '0',
  `downvote` int(11) NOT NULL DEFAULT '0',
  `status` int(1) NOT NULL DEFAULT '0',
  `assigned_to` varchar(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `assigned_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tag_category` varchar(20) NOT NULL,
  `tag_hostel` varchar(20) DEFAULT NULL,
  `tag_type` varchar(20) DEFAULT NULL,
  `tag_extra` varchar(20) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `complaints`
--

INSERT INTO `complaints` (`complaint_id`, `title`, `user_id`, `body`, `upvote`, `downvote`, `status`, `assigned_to`, `created_at`, `assigned_at`, `tag_category`, `tag_hostel`, `tag_type`, `tag_extra`, `location`) VALUES
(1, 'Tubelight is not working', 'ee1130451', 'Tubelight is not working in room number D-06. Please fix asap', 0, 0, 0, NULL, '2016-03-27 14:04:20', '2016-03-27 14:04:20', 'Personal', NULL, NULL, NULL, 'D-06'),
(2, 'Fan is not working', 'ee1130451', 'Both fan is not working', 0, 0, 0, NULL, '2016-03-27 14:31:08', '2016-03-27 14:31:08', 'Personal', NULL, NULL, NULL, 'D-06'),
(3, 'Worst rotis ever', 'ee1130451', 'Mess workers need to improve rotis.', 0, 0, 0, NULL, '2016-03-29 12:49:59', '2016-03-29 12:49:59', 'Hostel', 'Karakoram', NULL, NULL, 'Mess, Karakoram'),
(4, 'Lan ban should be raised', 'ee1130451', 'Purpose of lan is to provide extra assistance in study.', 0, 0, 0, NULL, '2016-03-29 12:55:10', '2016-03-29 12:55:10', 'Institute', NULL, NULL, NULL, NULL),
(5, 'aaaaaaa', 'ee1130451', 'agsbshdkjasdn 667', 0, 0, 0, NULL, '2016-03-29 13:25:13', '2016-03-29 13:25:13', 'Hostel', 'Karakoram', NULL, NULL, 'c-56');

-- --------------------------------------------------------

--
-- Table structure for table `complaint_type`
--

CREATE TABLE `complaint_type` (
  `type_id` varchar(20) NOT NULL,
  `info` varchar(255) NOT NULL,
  `worker_type` varchar(20) DEFAULT NULL,
  `type_tag` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `group_hostel`
--

CREATE TABLE `group_hostel` (
  `hostel` varchar(20) NOT NULL,
  `warden` varchar(20) DEFAULT NULL,
  `caretaker` varchar(20) DEFAULT NULL,
  `house_secy` varchar(20) DEFAULT NULL,
  `maint_secy` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `group_hostel`
--

INSERT INTO `group_hostel` (`hostel`, `warden`, `caretaker`, `house_secy`, `maint_secy`) VALUES
('Aravali', NULL, NULL, NULL, NULL),
('Girnar', NULL, NULL, NULL, NULL),
('Himadri', NULL, NULL, NULL, NULL),
('Janskar', NULL, NULL, NULL, NULL),
('Jwalamukhi', NULL, NULL, NULL, NULL),
('Kailash', NULL, NULL, NULL, NULL),
('Karakoram', NULL, NULL, NULL, NULL),
('Kumanu', NULL, NULL, NULL, NULL),
('Nilgiri', NULL, NULL, NULL, NULL),
('Satpura', NULL, NULL, NULL, NULL),
('Shivalik', NULL, NULL, NULL, NULL),
('Udaigiri', NULL, NULL, NULL, NULL),
('Vindyachal', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `institute`
--

CREATE TABLE `institute` (
  `director` varchar(20) DEFAULT NULL,
  `sys_admin` varchar(20) DEFAULT NULL,
  `insti_engineer` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `institute`
--

INSERT INTO `institute` (`director`, `sys_admin`, `insti_engineer`) VALUES
(NULL, 'ee1130451', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `notification_id` int(11) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `complaint_id` int(11) DEFAULT NULL,
  `user_add` varchar(20) DEFAULT NULL,
  `body` text NOT NULL,
  `status` int(1) NOT NULL DEFAULT '0',
  `date_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

CREATE TABLE `tag` (
  `name` varchar(20) NOT NULL,
  `info` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tag`
--

INSERT INTO `tag` (`name`, `info`) VALUES
('Admin', 'Access to All processes'),
('Aravali', 'Aravali hostel'),
('Girnar', 'Girnar hostel'),
('Himadri', 'Himadri hostel'),
('Hostel', 'Hostel Level Complaints'),
('Institute', 'Institute Level complaints'),
('Janskar ', 'Janskar hostel'),
('Jwalamukhi', 'Jwalamukhi hostel tag'),
('Kailash', 'Kailash hostel'),
('Karakoram', 'Karakoram hostel tag'),
('Kumanu', 'Kumanu hostel tag'),
('Nilgiri', 'Niligiri hostel'),
('Personal', 'All Personal Complaints come under this tag'),
('Satpura', 'Satpura hostel tag'),
('Shivalik', 'Shivalik hostel'),
('Udaigiri', 'Udaigiri hostel'),
('Vindyachal', 'Vindyachal Hostel');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` varchar(20) NOT NULL,
  `password` text NOT NULL,
  `first_name` varchar(250) DEFAULT NULL,
  `last_name` varchar(250) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `api_key` varchar(32) NOT NULL,
  `user_type` varchar(20) NOT NULL,
  `hostel` varchar(20) NOT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `address` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `password`, `first_name`, `last_name`, `email`, `api_key`, `user_type`, `hostel`, `phone`, `address`) VALUES
('ee1130451', '$2a$10$803755559a200e498ce25OlDMYpYRvP0dmU9is5OnDQJzQEFreFIu', 'Dharmendra', 'Choudhary', 'ee1130451@iitd.ac.in', '59cb5da4526af429e2ba2b7d3c7130ea', 'Admin', 'Karakoram', '9871518612', NULL),
('ee1130453', '$2a$10$83f9311a0cd26c6b5eeeduc.un4ezyhje87NMAe3/zPIAG937WPky', 'Divyansh', 'Chauhan', 'ee1130453@iitd.ac.in', 'e377e2481815a26b0327127a4daa6909', 'Student', 'Karakoram', '9871518612', 'C-03'),
('ee1130455', '$2a$10$28f797b6cdfd27631fe16ujoOjA2TbSKZgNni9z5OxxUKn.Xi001C', NULL, NULL, 'ee1130455@iitd.ac.in', 'eb045efaa400742b63dd9d74b752a6bb', 'Student', 'Karakoram', NULL, NULL),
('ee1130456', '$2a$10$cac3b15cee3c096956044u0LtPjGmLEj1N3JZI6LTlatQ/nIwJpyK', NULL, NULL, 'ee1130456@iitd.ac.in', 'f2bf2728632cd1199083d1782ab96e7f', 'Student', 'Karakoram', NULL, NULL),
('ee1130457', '$2a$10$60c244889a1d2bfd589e0uIwNNyeq8F8REB9LDZCGDF1WL9zRDpyW', NULL, NULL, 'ee1130457@iitd.ernet.in', '0b5282086b256a33e25c188ca51e8021', 'Student', 'Karakoram', NULL, NULL),
('ee1130468', '$2a$10$3711bdd21f414d82919bfOAMxOdYqiOL4r9MLiP17Hw1UMJoQVklG', NULL, NULL, 'ee1130468@iitd.ernet.in', '90d1dc7884735d7be7cf173c5bdac037', 'Student', 'Karakoram', NULL, NULL),
('tt1130906', '$2a$10$bd00d10d42036b9e72245uFO7CdmlTdtOfLFI9443vQ6hHGX0YL1K', NULL, NULL, 'tt1130906@iitd.ac.in', '6d6fc25693b3a70baea80589ca7a89c8', 'Student', 'Karakoram', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_type`
--

CREATE TABLE `user_type` (
  `name` varchar(20) NOT NULL,
  `user_tag` varchar(20) DEFAULT NULL,
  `user_priority` int(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_type`
--

INSERT INTO `user_type` (`name`, `user_tag`, `user_priority`) VALUES
('Admin', 'Admin', 5),
('Student', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `votes`
--

CREATE TABLE `votes` (
  `vote_id` int(11) NOT NULL,
  `complaint_id` int(11) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  `vote` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `complaint_id` (`complaint_id`);

--
-- Indexes for table `complaints`
--
ALTER TABLE `complaints`
  ADD PRIMARY KEY (`complaint_id`),
  ADD UNIQUE KEY `title` (`title`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `assigned_to` (`assigned_to`),
  ADD KEY `tag_hostel` (`tag_hostel`),
  ADD KEY `tag_type` (`tag_type`),
  ADD KEY `tag_category` (`tag_category`),
  ADD KEY `tag_extra` (`tag_extra`);

--
-- Indexes for table `complaint_type`
--
ALTER TABLE `complaint_type`
  ADD PRIMARY KEY (`type_id`),
  ADD KEY `worker_type` (`worker_type`),
  ADD KEY `type_tag` (`type_tag`);

--
-- Indexes for table `group_hostel`
--
ALTER TABLE `group_hostel`
  ADD PRIMARY KEY (`hostel`),
  ADD KEY `warden` (`warden`),
  ADD KEY `caretaker` (`caretaker`),
  ADD KEY `house_secy` (`house_secy`),
  ADD KEY `maint_secy` (`maint_secy`);

--
-- Indexes for table `institute`
--
ALTER TABLE `institute`
  ADD KEY `director` (`director`),
  ADD KEY `sys_admin` (`sys_admin`),
  ADD KEY `insti_engineer` (`insti_engineer`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `complaint_id` (`complaint_id`),
  ADD KEY `user_add` (`user_add`);

--
-- Indexes for table `tag`
--
ALTER TABLE `tag`
  ADD PRIMARY KEY (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `user_type` (`user_type`),
  ADD KEY `hostel` (`hostel`);

--
-- Indexes for table `user_type`
--
ALTER TABLE `user_type`
  ADD PRIMARY KEY (`name`),
  ADD KEY `user_tag` (`user_tag`);

--
-- Indexes for table `votes`
--
ALTER TABLE `votes`
  ADD PRIMARY KEY (`vote_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `complaint_id` (`complaint_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `complaints`
--
ALTER TABLE `complaints`
  MODIFY `complaint_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `votes`
--
ALTER TABLE `votes`
  MODIFY `vote_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`complaint_id`) REFERENCES `complaints` (`complaint_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `complaints`
--
ALTER TABLE `complaints`
  ADD CONSTRAINT `complaints_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `complaints_ibfk_2` FOREIGN KEY (`assigned_to`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `complaints_ibfk_3` FOREIGN KEY (`tag_hostel`) REFERENCES `group_hostel` (`hostel`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `complaints_ibfk_4` FOREIGN KEY (`tag_category`) REFERENCES `tag` (`name`) ON UPDATE CASCADE,
  ADD CONSTRAINT `complaints_ibfk_5` FOREIGN KEY (`tag_extra`) REFERENCES `tag` (`name`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `complaint_type`
--
ALTER TABLE `complaint_type`
  ADD CONSTRAINT `complaint__type_ibfk_1` FOREIGN KEY (`worker_type`) REFERENCES `user_type` (`name`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `complaints_type_ibfk_2` FOREIGN KEY (`type_tag`) REFERENCES `tag` (`name`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `group_hostel`
--
ALTER TABLE `group_hostel`
  ADD CONSTRAINT `group_hostel_ibfk_1` FOREIGN KEY (`warden`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `group_hostel_ibfk_2` FOREIGN KEY (`caretaker`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `group_hostel_ibfk_3` FOREIGN KEY (`house_secy`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `group_hostel_ibfk_4` FOREIGN KEY (`maint_secy`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `institute`
--
ALTER TABLE `institute`
  ADD CONSTRAINT `institute__ibfk_1` FOREIGN KEY (`director`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `institute__ibfk_2` FOREIGN KEY (`sys_admin`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `institute__ibfk_3` FOREIGN KEY (`sys_admin`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `notifications_ibfk_2` FOREIGN KEY (`user_add`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `notifications_ibfk_3` FOREIGN KEY (`complaint_id`) REFERENCES `comments` (`complaint_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`hostel`) REFERENCES `group_hostel` (`hostel`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users_ibfk_2` FOREIGN KEY (`user_type`) REFERENCES `user_type` (`name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_type`
--
ALTER TABLE `user_type`
  ADD CONSTRAINT `user_type_ibfk_1` FOREIGN KEY (`user_tag`) REFERENCES `tag` (`name`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `votes`
--
ALTER TABLE `votes`
  ADD CONSTRAINT `votes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `votes_ibfk_2` FOREIGN KEY (`complaint_id`) REFERENCES `complaints` (`complaint_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
