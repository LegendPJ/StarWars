-- Parties (Nom, Tour, NumJoueur, Dimension)
insert into parties VALUES ('Partie de M. Brunet Manquat', 0, 0, 7);
insert into parties values ('Partie de M. Martin', 0, 0, 5);
insert into parties values ('Champ de Bataille', 0, 0, 7);

-- Vaisseaux(Nom, Type)
insert into vaisseaux values ('Faucon Millenium', '[-]');
insert into vaisseaux values ('Francis', '(-)');
insert into vaisseaux values ('Philippe', '{-}');
insert into vaisseaux values ('Vaisseau Imperial', '|-|');
insert into vaisseaux values ('Xwing', '(-)');

-- Objets(Nom, Points, Carac, Duree, Type)
insert into objets values ('Fusil laser', 10, 'degat', 2, 'arme');
insert into objets values ('Nuage d''''étoiles', 10, 'champ', 2, 'bonus');
insert into objets values ('Nuage de poussière', 5, 'champ', 1, 'bonus');
insert into objets values ('Canon à protons', 15, 'degat', 3, 'arme');
insert into objets values ('Batterie à combustion', 15, 'energie', 1, 'bonus');
insert into objets values ('Pile à combustion', 5, 'energie', 1, 'bonus');

-- ObjetsParties(NomPartie, NomObjet, CoordX, CoordY)
insert into objets_parties values ('Partie de M. Brunet Manquat', 'Fusil laser', 5, 3);
insert into objets_parties values ('Partie de M. Brunet Manquat', 'Nuage d''''étoiles', 2, 2);
insert into objets_parties values ('Partie de M. Brunet Manquat', 'Batterie à combustion', 6, 0);

insert into objets_parties values ('Champ de Bataille', 'Fusil laser', 4, 3);
insert into objets_parties values ('Champ de Bataille', 'Nuage de poussière', 0, 2);

-- PartiesVaisseaux(NomPartie, NomVaisseau, CoordX, CoordY, Attaque, Degats, Champ, Energie, Pa, NumJoueur)
insert into parties_vaisseaux values ('Partie de M. Brunet Manquat', 'Faucon Millenium', 4, 3, 10, 10, 10, 100, 6, 1);
insert into parties_vaisseaux values ('Partie de M. Brunet Manquat', 'Francis', 6, 1, 10, 10, 10, 100, 6, 2);

insert into parties_vaisseaux values ('Partie de M. Martin', 'Philippe', 1, 3, 10, 10, 10, 100, 6, 1);
insert into parties_vaisseaux values ('Partie de M. Martin', 'Francis', 4, 1, 10, 10, 10, 100, 6, 2);

insert into parties_vaisseaux values ('Champ de Bataille', 'Vaisseau Imperial', 6, 2, 10, 10, 10, 100, 3, 1);
insert into parties_vaisseaux values ('Champ de Bataille', 'Xwing', 5, 5, 10, 10, 10, 100, 6, 2);

-- ObjetsVaisseaux(NomVaisseau, NomPartie, NomObjet, Equipe, DureeRestante)
insert into objets_vaisseaux values ('Vaisseau Imperial', 'Champ de Bataille', 'Batterie à combustion', 0, 1);

--TODO PARTIE TRUQUEE !!!!!!!!!!!!
insert into parties ('Fin du Monde', 0, 0, 5)
insert into parties_vaisseaux values ('Fin du Monde', 'Faucon Millenium', 3, 2, 17, 17, 3, 30, 6, 1);
insert into parties_vaisseaux values ('Fin du Monde', 'Vaisseau Imperial', 3, 3, 17, 17, 3, 30, 6, 2);
insert into objets_vaisseaux values ('Fin du Monde', 'Faucon Millenium', 'Canon à protons', 1, 2);
insert into objets_vaisseaux values ('Fin du Monde', 'Faucon Millenium', 'Fusil laser', 1, 2);
insert into objets_vaisseaux values ('Fin du Monde', 'Vaisseau Imperial', 'Pile à combustion', 1, 1);