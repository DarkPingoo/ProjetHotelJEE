/*TRUNCATE TABLE CLIENT;*/
/*TRUNCATE TABLE CHAMBRE;*/
/*TRUNCATE TABLE RESERVATION;*/
--
-- Utilisation de la base de données Hotel
--
USE Hotel;

/* REMPLISSAGE DE LA TABLE CLIENT */
INSERT INTO CLIENT(nom,prenom,numTelephone) VALUES('Merand','Celine','0615275045');
INSERT INTO CLIENT(nom,prenom,numTelephone) VALUES('Merson','Jean','0659871256');
INSERT INTO CLIENT(nom,prenom,numTelephone) VALUES('Pereira','Quentin','0159862015');
INSERT INTO CLIENT(nom,prenom,numTelephone) VALUES('Le Gacque','Tristan','0620152020');

/* REMPLISSAGE DE LA TABLE CHAMBRE */
INSERT INTO CHAMBRE(typeChambre,nombrePlaceLit,PrixJournalier,etage) VALUES('Taudis',1,20,2);
INSERT INTO CHAMBRE(typeChambre,nombrePlaceLit,PrixJournalier,etage) VALUES('Suite Royale',5,150,3);
INSERT INTO CHAMBRE(typeChambre,nombrePlaceLit,PrixJournalier,etage) VALUES('Affaire',2,80,3);
INSERT INTO CHAMBRE(typeChambre,nombrePlaceLit,PrixJournalier,etage) VALUES('Familiale',3,79,1);
INSERT INTO CHAMBRE(typeChambre,nombrePlaceLit,PrixJournalier,etage) VALUES('Taudis',2,25,4);


/* REMPLISSAGE DE LA TABLE RESERVATION */

INSERT INTO `RESERVATION` (`idReservation`, `idChambre`, `idClient`, `dateDebut`, `dateFin`, `nombrePlaces`, `booleenPaiementEffectue`) VALUES
  (1, 1, 2, '2018-11-02', '2018-11-09', 2, 1),
  (2, 2, 1, '2018-11-02', '2018-11-09', 5, 1),
  (3, 3, 4, '2018-11-02', '2018-11-05', 1, 1),
  (4, 4, 3, '2018-11-03', '2018-11-09', 3, 1),
  (5, 4, 3, '2018-05-05', '2018-05-09', 4, 1),
  (13, 4, 2, '2018-10-16', '2018-10-19', 5, 1),
  (15, 2, 1, '2018-03-23', '2018-03-28', 1, 0),
  (16, 1, 2, '2018-01-03', '2018-01-07', 2, 0);