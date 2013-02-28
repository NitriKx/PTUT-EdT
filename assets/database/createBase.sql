CREATE TABLE timetable 
(
		idTT				int NOT NULL AUTO_INCREMENT,
	 	dateDebut			date,
	 	dateFin				date,
		CONSTRAINT PRIMARY KEY(idTT)
) ENGINE=InnoDB;

CREATE TABLE lesson 
(
		idLesson			int NOT NULL AUTO_INCREMENT,
		libelle				varhar(30),
	 	dateDebut			date,
	 	dateFin				date,
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
	 	dateMessage			date,
		CONSTRAINT PRIMARY KEY(idMessage)
) ENGINE=InnoDB;