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


/* REMPLISSAGE DE LA TABLE RESERVATION */
INSERT INTO RESERVATION(idChambre,idClient,dateDebut,dateFin,nombrePlaces,booleenPaiementEffectue) VALUES (1,2,'2018-11-02','2018-11-09',2,0);
INSERT INTO RESERVATION(idChambre,idClient,dateDebut,dateFin,nombrePlaces,booleenPaiementEffectue) VALUES (2,1,'2018-11-02','2018-11-09',5,1);
INSERT INTO RESERVATION(idChambre,idClient,dateDebut,dateFin,nombrePlaces,booleenPaiementEffectue) VALUES (3,4,'2018-11-02','2018-11-05',1,1);
INSERT INTO RESERVATION(idChambre,idClient,dateDebut,dateFin,nombrePlaces,booleenPaiementEffectue) VALUES (4,3,'2018-11-03','2018-11-09',3,0);