CREATE TABLE groupe
(
		idGroupe			varchar(9) NOT NULL, -- "YYYY-4-2B" / ANNEE-SEMESTRE-GROUPE
		CONSTRAINT PRIMARY KEY(idGroupe)
) ENGINE=InnoDB;

CREATE TABLE timetable 
(
		idTT				int NOT NULL AUTO_INCREMENT,
	 	dateDebut			DATETIME,
	 	dateFin				DATETIME,
		groupe				varchar(9),
		CONSTRAINT PRIMARY KEY(idTT),
		CONSTRAINT FOREIGN KEY(groupe) REFERENCES groupe(idGroupe)
) ENGINE=InnoDB;

CREATE TABLE lesson 
(
		idLesson			int NOT NULL AUTO_INCREMENT,
		libelle				varchar(30),
	 	dateDebut			DATETIME,
	 	dateFin				DATETIME,
		intervenant			varchar(30),
		emplacement			varchar(30),
		idTT				int NOT NULL,
		CONSTRAINT PRIMARY KEY(idLesson),
		CONSTRAINT FOREIGN KEY(idTT) REFERENCES timetable(idTT)
) ENGINE=InnoDB;

CREATE TABLE message
(
		idMessage			int NOT NULL AUTO_INCREMENT,
	 	libelle				varchar(100),
	 	dateMessage			DATETIME ,
		CONSTRAINT PRIMARY KEY(idMessage)
) ENGINE=InnoDB;