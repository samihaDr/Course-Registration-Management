-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 25 août 2021 à 23:50
-- Version du serveur :  10.4.18-MariaDB
-- Version de PHP : 8.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `tgpr-2122-g01`
--
drop database if exists `tgpr-2122-g01`;
CREATE DATABASE IF NOT EXISTS `tgpr-2122-g01` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `tgpr-2122-g01`;

-- --------------------------------------------------------

--
-- Structure de la table `answers`
--

CREATE TABLE `answers` (
  `test` int(11) NOT NULL,
  `option` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `answers`
--

INSERT INTO `answers` (`test`, `option`) VALUES
(1, 5),
(1, 10),
(14, 37);

-- --------------------------------------------------------

--
-- Structure de la table `courses`
--

CREATE TABLE `courses` (
  `id` int(11) NOT NULL,
  `code` char(4) NOT NULL,
  `description` varchar(128) NOT NULL,
  `capacity` int(11) NOT NULL,
  `teacher` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `courses`
--

INSERT INTO `courses` (`id`, `code`, `description`, `capacity`, `teacher`) VALUES
(1000, 'PRBD', 'Projet de développement de base de données', 3, 'ben'),
(1174, 'PRID', 'Projet d\'intégration et de développement', 3, 'ben'),
(1234, 'BD01', 'Initiation aux bases de données', 3, 'marc'),
(1658, 'TGPR', 'Techniques de gestion de projets', 3, 'marc'),
(1659, 'TGPR', 'Techniques de gestion de projets', 5, 'ben'),
(1662, 'PRWB', 'Projet de développement web', 3, 'ben'),
(1925, 'MAP4', 'Mathématiques appliquées à l\'informatique', 3, 'marc');

-- --------------------------------------------------------

--
-- Structure de la table `options`
--

CREATE TABLE `options` (
  `id` int(11) NOT NULL,
  `title` varchar(256) NOT NULL,
  `correct` tinyint(1) NOT NULL,
  `question` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `options`
--

INSERT INTO `options` (`id`, `title`, `correct`, `question`) VALUES
(3, 'rouge', 0, 1),
(4, 'vert', 0, 1),
(5, 'blanc', 1, 1),
(10, '12.742 km', 1, 2),
(11, '8.742 km', 0, 2),
(12, '6.742 km', 0, 2),
(13, '14.742 km', 0, 2),
(14, 'pi*R', 0, 3),
(15, '2*pi*R^2', 0, 3),
(16, '2*pi*R', 1, 3),
(17, 'pi*R^2', 0, 3),
(18, 'une proposition qui est toujours vraie', 1, 4),
(19, 'une proposition qui est toujours fausse', 0, 4),
(20, 'une proposition qui est parfois vraie', 0, 4),
(21, 'l\'étude des blagues de Toto', 0, 4),
(22, '15', 1, 5),
(23, '12,001', 0, 5),
(24, '-3', 0, 5),
(25, '1/2', 0, 5),
(26, '0', 1, 5),
(27, 'vrai', 0, 6),
(28, 'faux', 1, 6),
(29, 'Une algue', 0, 7),
(30, 'Une salade', 0, 7),
(31, 'Un chou marin', 1, 7),
(32, 'Hulk qui se noie', 0, 7),
(33, '2,54', 1, 8),
(34, '25,4', 0, 8),
(35, '1,54', 0, 8),
(36, '15,4', 0, 8),
(37, 'H2O', 1, 9),
(38, 'HO2', 0, 9),
(39, 'O2H', 0, 9);

-- --------------------------------------------------------

--
-- Structure de la table `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `title` varchar(128) NOT NULL,
  `type` char(3) NOT NULL,
  `quiz` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `questions`
--

INSERT INTO `questions` (`id`, `title`, `type`, `quiz`) VALUES
(1, 'Quelle est la couleur du cheval blanc de Napoléon ?', 'QCM', 1),
(2, 'Quelle est le diamètre de la terre ?', 'QCM', 1),
(3, 'Quelle formule donne le périmètre du cercle ?', 'QCM', 2),
(4, 'Qu\'est-ce qu\'une tautologie ?', 'QCM', 2),
(5, 'Parmi ces nombres, lesquels sont des nombres naturels ?', 'QRM', 2),
(6, 'L\'implication est un opérateur logique commutatif ?', 'QCM', 2),
(7, 'Qu\'est-ce qui est vert et qui vit sous l\'eau ?', 'QCM', 4),
(8, 'Combien de centimètres dans un inch ?', 'QCM', 4),
(9, 'Quelle est la formule chimique de la molécule d\'eau ?', 'QCM', 4);

-- --------------------------------------------------------

--
-- Structure de la table `quizzes`
--

CREATE TABLE `quizzes` (
  `id` int(11) NOT NULL,
  `title` varchar(128) NOT NULL,
  `start` date NOT NULL,
  `finish` date NOT NULL,
  `course` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `quizzes`
--

INSERT INTO `quizzes` (`id`, `title`, `start`, `finish`, `course`) VALUES
(1, 'Quiz1', '2021-09-15', '2021-11-30', 1659),
(2, 'Quiz2', '2021-10-15', '2021-10-30', 1659),
(4, 'Quiz3', '2021-09-21', '2021-09-22', 1659);

-- --------------------------------------------------------

--
-- Structure de la table `registrations`
--

CREATE TABLE `registrations` (
  `course` int(11) NOT NULL,
  `student` varchar(128) NOT NULL,
  `active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `registrations`
--

INSERT INTO `registrations` (`course`, `student`, `active`) VALUES
(1000, 'bob', 0),
(1000, 'harry', 1),
(1174, 'bibi', 1),
(1174, 'bob', 0),
(1174, 'guest', 0),
(1234, 'bob', 0),
(1658, 'bob', 1),
(1659, 'bibi', 1),
(1659, 'bob', 1),
(1659, 'caro', 1),
(1659, 'fred', 1),
(1659, 'guest', 0),
(1662, 'harry', 0),
(1925, 'bob', 1);

-- --------------------------------------------------------

--
-- Structure de la table `tests`
--

CREATE TABLE `tests` (
  `id` int(11) NOT NULL,
  `student` varchar(128) NOT NULL,
  `quiz` int(11) NOT NULL,
  `start` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `tests`
--

INSERT INTO `tests` (`id`, `student`, `quiz`, `start`) VALUES
(1, 'bob', 1, '2021-09-20 23:12:10'),
(14, 'bob', 4, '2021-09-21 21:29:16');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `pseudo` varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
  `fullname` text DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `role` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`pseudo`, `password`, `fullname`, `birthdate`, `role`) VALUES
('admin', 'admin', 'Mr. Admin', NULL, 2),
('ben', 'ben', 'Benoît Penelle', '1970-07-01', 1),
('bibi', 'bibi', 'Bibi the Robot', NULL, 0),
('bob', 'bob', 'Bob l\'éponge', NULL, 0),
('boris', 'boris', 'Boris Verhaegen', NULL, 1),
('bruno', 'bruno', 'Bruno Lacroix', NULL, 1),
('caro', 'caro', 'Caroline', NULL, 0),
('fred', 'fred', 'Frédéric', NULL, 0),
('guest', 'guest', 'Invité', NULL, 0),
('harry', 'harry', 'Harry Cover', NULL, 0),
('marc', 'marc', 'Marc Michel', NULL, 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`test`,`option`),
  ADD KEY `option` (`option`);

--
-- Index pour la table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_course_teacher` (`teacher`);

--
-- Index pour la table `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_question_option` (`question`);

--
-- Index pour la table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_quiz_question` (`quiz`);

--
-- Index pour la table `quizzes`
--
ALTER TABLE `quizzes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_quiz_course` (`course`);

--
-- Index pour la table `registrations`
--
ALTER TABLE `registrations`
  ADD PRIMARY KEY (`course`,`student`),
  ADD KEY `fk_registration_student` (`student`);

--
-- Index pour la table `tests`
--
ALTER TABLE `tests`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `student` (`student`,`quiz`),
  ADD KEY `quiz` (`quiz`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`pseudo`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `options`
--
ALTER TABLE `options`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT pour la table `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `quizzes`
--
ALTER TABLE `quizzes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `tests`
--
ALTER TABLE `tests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`test`) REFERENCES `tests` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `answers_ibfk_2` FOREIGN KEY (`option`) REFERENCES `options` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `courses`
--
ALTER TABLE `courses`
  ADD CONSTRAINT `fk_course_teacher` FOREIGN KEY (`teacher`) REFERENCES `users` (`pseudo`) ON DELETE CASCADE;

--
-- Contraintes pour la table `options`
--
ALTER TABLE `options`
  ADD CONSTRAINT `fk_question_option` FOREIGN KEY (`question`) REFERENCES `questions` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `fk_quiz_question` FOREIGN KEY (`quiz`) REFERENCES `quizzes` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `quizzes`
--
ALTER TABLE `quizzes`
  ADD CONSTRAINT `fk_quiz_course` FOREIGN KEY (`course`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `registrations`
--
ALTER TABLE `registrations`
  ADD CONSTRAINT `fk_registration_course` FOREIGN KEY (`course`) REFERENCES `courses` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_registration_student` FOREIGN KEY (`student`) REFERENCES `users` (`pseudo`) ON DELETE CASCADE;

--
-- Contraintes pour la table `tests`
--
ALTER TABLE `tests`
  ADD CONSTRAINT `tests_ibfk_1` FOREIGN KEY (`quiz`) REFERENCES `quizzes` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `tests_ibfk_2` FOREIGN KEY (`student`) REFERENCES `users` (`pseudo`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
