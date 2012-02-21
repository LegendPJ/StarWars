

CREATE TABLE vaisseaux (
	nom		VARCHAR(40)			PRIMARY KEY,
	attaque	INT(2)				DEFAULT 0,
	degats	INT(2)				DEFAULT 0,
	champ	INT(2)				DEFAULT 0,
	energie	INT(2)				DEFAULT 0,
);

CREATE TABLE objets (
	nom		VARCHAR(40)			PRIMARY KEY,
	points	INT(2)				DEFAULT 0,
	carac	ENUM ('attaque', 'degats', 'champ', 'energie')	NOT NULL,
);

CREATE TABLE objets_vaisseaux (
	nom_vaisseau	VARCHAR(40),
	nom_objet		VARCHAR(40),
	FOREIGN KEY		(nom_vaisseau)	REFERENCES	vaisseaux(nom),
	FOREIGN KEY		(nom_objet)		REFERENCES	objets(nom),
	PRIMARY KEY (nom_vaisseau, nom_objet),
);


