
-----------------------------------------------------------------------------
-- vaisseaux
-----------------------------------------------------------------------------
DROP TABLE vaisseaux CASCADE;



CREATE TABLE vaisseaux
(
    nom VARCHAR(40) NOT NULL,
    type VARCHAR(3) NOT NULL,
    PRIMARY KEY (nom)
);

COMMENT ON TABLE vaisseaux IS 'vaisseaux';

COMMENT ON COLUMN vaisseaux.nom IS 'Nom du vaisseau';
COMMENT ON COLUMN vaisseaux.type IS 'Type du vaisseau';


-----------------------------------------------------------------------------
-- caracteristiques
-----------------------------------------------------------------------------
DROP TABLE caracteristiques CASCADE;



CREATE TABLE caracteristiques
(
    nom VARCHAR(60) NOT NULL,
    PRIMARY KEY (nom)
);

COMMENT ON TABLE caracteristiques IS 'caracteristiques';

COMMENT ON COLUMN caracteristiques.nom IS '...';


-----------------------------------------------------------------------------
-- objets
-----------------------------------------------------------------------------
DROP TABLE objets CASCADE;



CREATE TABLE objets
(
    nom VARCHAR(40) NOT NULL,
    points INTEGER default 0 NOT NULL,
    carac VARCHAR default '0' NOT NULL,
    PRIMARY KEY (nom)
);

COMMENT ON TABLE objets IS 'objets';

COMMENT ON COLUMN objets.nom IS 'Nom de lobjet';
COMMENT ON COLUMN objets.points IS 'points de gain';
COMMENT ON COLUMN objets.carac IS 'caracteritique améliorée';


-----------------------------------------------------------------------------
-- objets_vaisseaux
-----------------------------------------------------------------------------
DROP TABLE objets_vaisseaux CASCADE;



CREATE TABLE objets_vaisseaux
(
    nom_vaisseau VARCHAR(40) NOT NULL,
    nom_objet VARCHAR(40) NOT NULL,
    PRIMARY KEY (nom_vaisseau,nom_objet)
);

COMMENT ON TABLE objets_vaisseaux IS 'objets_vaisseaux';

COMMENT ON COLUMN objets_vaisseaux.nom_vaisseau IS 'Nom du vaisseau';
COMMENT ON COLUMN objets_vaisseaux.nom_objet IS 'Nom de lobjet';


-----------------------------------------------------------------------------
-- parties
-----------------------------------------------------------------------------
DROP TABLE parties CASCADE;



CREATE TABLE parties
(
    id INTEGER NOT NULL,
    nom VARCHAR(40) NOT NULL,
    tour VARCHAR(40) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE parties IS 'parties';

COMMENT ON COLUMN parties.id IS 'Une partie';
COMMENT ON COLUMN parties.nom IS 'Nom de la partie';
COMMENT ON COLUMN parties.tour IS 'a qui le tour';


-----------------------------------------------------------------------------
-- parties_vaisseaux
-----------------------------------------------------------------------------
DROP TABLE parties_vaisseaux CASCADE;



CREATE TABLE parties_vaisseaux
(
    id_partie INTEGER NOT NULL,
    nom_vaisseau VARCHAR(40) NOT NULL,
    coord_x INTEGER NOT NULL,
    coord_y INTEGER NOT NULL,
    attaque INTEGER default 0 NOT NULL,
    degats INTEGER default 0 NOT NULL,
    champ INTEGER default 0 NOT NULL,
    energie INTEGER default 0 NOT NULL,
    pa INTEGER default 6 NOT NULL,
    PRIMARY KEY (id_partie)
);

COMMENT ON TABLE parties_vaisseaux IS 'parties_vaisseaux';

COMMENT ON COLUMN parties_vaisseaux.id_partie IS 'Une partie';
COMMENT ON COLUMN parties_vaisseaux.nom_vaisseau IS 'Nom du vaisseau';
COMMENT ON COLUMN parties_vaisseaux.coord_x IS 'coordonnées en X';
COMMENT ON COLUMN parties_vaisseaux.coord_y IS 'coordonnées en Y';
COMMENT ON COLUMN parties_vaisseaux.attaque IS 'attaque du vaisseau';
COMMENT ON COLUMN parties_vaisseaux.degats IS 'degats du vaisseau';
COMMENT ON COLUMN parties_vaisseaux.champ IS 'champ du vaisseau';
COMMENT ON COLUMN parties_vaisseaux.energie IS 'energie du vaisseau';
COMMENT ON COLUMN parties_vaisseaux.pa IS 'points daction du vaisseau';


-----------------------------------------------------------------------------
-- objets_parties
-----------------------------------------------------------------------------
DROP TABLE objets_parties CASCADE;



CREATE TABLE objets_parties
(
    id_partie INTEGER NOT NULL,
    nom_objet VARCHAR(40) NOT NULL,
    coord_x INTEGER NOT NULL,
    coord_y INTEGER NOT NULL,
    PRIMARY KEY (id_partie)
);

COMMENT ON TABLE objets_parties IS 'objets_parties';

COMMENT ON COLUMN objets_parties.id_partie IS 'Une partie';
COMMENT ON COLUMN objets_parties.nom_objet IS 'Nom de lobjet';
COMMENT ON COLUMN objets_parties.coord_x IS 'coordonnées en X';
COMMENT ON COLUMN objets_parties.coord_y IS 'coordonnées en Y';


----------------------------------------------------------------------
-- objets_parties
----------------------------------------------------------------------



----------------------------------------------------------------------
-- vaisseaux
----------------------------------------------------------------------



----------------------------------------------------------------------
-- caracteristiques
----------------------------------------------------------------------


ALTER TABLE objets
    ADD CONSTRAINT objets_FK_1 FOREIGN KEY (carac)
    REFERENCES caracteristiques (nom)
;

----------------------------------------------------------------------
-- objets
----------------------------------------------------------------------


ALTER TABLE objets_vaisseaux
    ADD CONSTRAINT objets_vaisseaux_FK_1 FOREIGN KEY (nom_vaisseau)
    REFERENCES vaisseaux (nom)
;
ALTER TABLE objets_vaisseaux
    ADD CONSTRAINT objets_vaisseaux_FK_2 FOREIGN KEY (nom_objet)
    REFERENCES objets (nom)
;

----------------------------------------------------------------------
-- objets_vaisseaux
----------------------------------------------------------------------


ALTER TABLE parties
    ADD CONSTRAINT parties_FK_1 FOREIGN KEY (tour)
    REFERENCES vaisseaux (nom)
;

----------------------------------------------------------------------
-- parties
----------------------------------------------------------------------


ALTER TABLE parties_vaisseaux
    ADD CONSTRAINT parties_vaisseaux_FK_1 FOREIGN KEY (id_partie)
    REFERENCES parties (id)
;
ALTER TABLE parties_vaisseaux
    ADD CONSTRAINT parties_vaisseaux_FK_2 FOREIGN KEY (nom_vaisseau)
    REFERENCES vaisseaux (nom)
;

----------------------------------------------------------------------
-- parties_vaisseaux
----------------------------------------------------------------------


ALTER TABLE objets_parties
    ADD CONSTRAINT objets_parties_FK_1 FOREIGN KEY (id_partie)
    REFERENCES parties (id)
;
ALTER TABLE objets_parties
    ADD CONSTRAINT objets_parties_FK_2 FOREIGN KEY (nom_objet)
    REFERENCES objets (nom)
;
