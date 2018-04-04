--
-- Options par defaut
--
SET FOREIGN_KEY_CHECKS=0;
SET default_storage_engine=INNODB;
SET character_set_client = utf8;

--
-- Créer la base de donnée
--
CREATE DATABASE IF NOT EXISTS Hotel
DEFAULT CHARSET=utf8
COLLATE=utf8_unicode_ci;
USE Hotel;

--
-- Créer un utilisateur 'user' avec un mot de passe 'user'
--
CREATE USER 'user'@'localhost' IDENTIFIED  BY 'user';
GRANT ALL PRIVILEGES TO 'user'@'localhost';
FLUSH PRIVILEGES;

--
-- Effacer les anciennes tables
--
DROP TABLE IF EXISTS RESERVATION;
DROP TABLE IF EXISTS CLIENT;
DROP TABLE IF EXISTS CHAMBRE;
SET FOREIGN_KEY_CHECKS=1;







--

-- Base de données :  `Hotel`

--



-- --------------------------------------------------------



--

-- Structure de la table `CHAMBRE`

--



CREATE TABLE `CHAMBRE` (

 `idChambre` smallint(6) NOT NULL,

 `typeChambre` enum('Taudis','Suite Royale','Familiale','Affaire') DEFAULT NULL,

 `nombrePlaceLit` smallint(6) DEFAULT NULL,

 `prixJournalier` smallint(6) DEFAULT NULL,

 `etage` tinyint(4) DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--

-- Contenu de la table `CHAMBRE`

--



INSERT INTO `CHAMBRE` (`idChambre`, `typeChambre`, `nombrePlaceLit`, `prixJournalier`, `etage`) VALUES

 (1, 'Taudis', 1, 20, 2),

 (2, 'Suite Royale', 5, 150, 3),

 (3, 'Affaire', 2, 80, 3),

 (4, 'Familiale', 3, 79, 1),

 (5, 'Taudis', 2, 25, 4);



-- --------------------------------------------------------



--

-- Structure de la table `CLIENT`

--



CREATE TABLE `CLIENT` (

 `idClient` smallint(6) NOT NULL,

 `nom` varchar(20) DEFAULT NULL,

 `prenom` varchar(20) DEFAULT NULL,

 `numTelephone` char(10) DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--

-- Contenu de la table `CLIENT`

--



INSERT INTO `CLIENT` (`idClient`, `nom`, `prenom`, `numTelephone`) VALUES

 (1, 'Merand', 'Celine', '0615275045'),

 (2, 'Merson', 'Jean', '0659871256'),

 (3, 'Pereira', 'Quentin', '0159862015'),

 (4, 'Le Gacque', 'Tristan', '0620152020');



-- --------------------------------------------------------



--

-- Structure de la table `RESERVATION`

--



CREATE TABLE `RESERVATION` (

 `idReservation` int(11) NOT NULL,

 `idChambre` smallint(6) DEFAULT NULL,

 `idClient` smallint(6) DEFAULT NULL,

 `dateDebut` date DEFAULT NULL,

 `dateFin` date DEFAULT NULL,

 `nombrePlaces` smallint(6) DEFAULT NULL,

 `booleenPaiementEffectue` tinyint(1) DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--

-- Contenu de la table `RESERVATION`

--



INSERT INTO `RESERVATION` (`idReservation`, `idChambre`, `idClient`, `dateDebut`, `dateFin`, `nombrePlaces`, `booleenPaiementEffectue`) VALUES

 (1, 1, 2, '2018-11-02', '2018-11-09', 2, 1),

 (2, 2, 1, '2018-11-02', '2018-11-09', 5, 1),

 (3, 3, 4, '2018-11-02', '2018-11-05', 1, 1),

 (4, 4, 3, '2018-11-03', '2018-11-09', 3, 1),

 (5, 4, 3, '2018-05-05', '2018-05-09', 4, 1),

 (13, 4, 2, '2018-10-16', '2018-10-19', 5, 1),

 (15, 2, 1, '2018-03-23', '2018-03-28', 1, 0),

 (16, 1, 2, '2018-01-03', '2018-01-07', 2, 0);



--

-- Index pour les tables exportées

--



--

-- Index pour la table `CHAMBRE`

--

ALTER TABLE `CHAMBRE`

 ADD PRIMARY KEY (`idChambre`);



--

-- Index pour la table `CLIENT`

--

ALTER TABLE `CLIENT`

 ADD PRIMARY KEY (`idClient`);



--

-- Index pour la table `RESERVATION`

--

ALTER TABLE `RESERVATION`

 ADD PRIMARY KEY (`idReservation`),

 ADD KEY `idChambre` (`idChambre`),

 ADD KEY `idClient` (`idClient`);



--

-- AUTO_INCREMENT pour les tables exportées

--



--

-- AUTO_INCREMENT pour la table `CHAMBRE`

--

ALTER TABLE `CHAMBRE`

 MODIFY `idChambre` smallint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--

-- AUTO_INCREMENT pour la table `CLIENT`

--

ALTER TABLE `CLIENT`

 MODIFY `idClient` smallint(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--

-- AUTO_INCREMENT pour la table `RESERVATION`

--

ALTER TABLE `RESERVATION`

 MODIFY `idReservation` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--

-- Contraintes pour les tables exportées

--



--

-- Contraintes pour la table `RESERVATION`

--

ALTER TABLE `RESERVATION`

 ADD CONSTRAINT `RESERVATION_ibfk_1` FOREIGN KEY (`idChambre`) REFERENCES `CHAMBRE` (`idChambre`) ON DELETE SET NULL,

 ADD CONSTRAINT `RESERVATION_ibfk_2` FOREIGN KEY (`idClient`) REFERENCES `CLIENT` (`idClient`) ON DELETE SET NULL;