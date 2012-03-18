package Controllers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.util.Transaction;

import Services.Fonctions;
import Vues.Vue;
import Vues.VueJoueur;
import Vues.VueMenuStarWars;
import Vues.VueObjet;
import Vues.VuePartie;
import Vues.VuePlateau;

import torque.generated.CaracteristiquesPeer;
import torque.generated.Objets;
import torque.generated.ObjetsParties;
import torque.generated.ObjetsPartiesPeer;
import torque.generated.ObjetsPeer;
import torque.generated.ObjetsVaisseaux;
import torque.generated.ObjetsVaisseauxPeer;
import torque.generated.Parties;
import torque.generated.PartiesPeer;
import torque.generated.PartiesVaisseaux;
import torque.generated.TypesPeer;
import torque.generated.Vaisseaux;
import torque.generated.VaisseauxPeer;


public class Controller {
	protected static Connection conn, connTransaction;
	private final static String TORQUE_PROPS = new String("torque-3.3//Torque.properties");
	
	private final int nb_joueurs = 2;
	private List<Integer> ordreTour;
	private int dimension = 5;
	private int nb_objets = 3;
	private Vaisseaux gagnant;
	
	// Vaisseau et partie en cours
	protected List<Vaisseaux> vaisseaux;
	protected List<PartiesVaisseaux> partieV;
	protected List<Objets> objets;
	protected Parties partie;
	protected List<ObjetsParties> objetsP;

	// Les vues
	private VueMenuStarWars	_vueMenuStarwars;
	private VuePartie		_vuePartie;
	private VueJoueur		_vueJoueur;
	private VuePlateau		_vuePlateau;
	private VueObjet		_vueObjet;
	
	public Controller() {
		this.resetJeu();
		this.resetVues();
	}
	
	/**
	 * Menu principal et choix d'action (nouvelle partie, charger ou nouvel objet)
	 */
	public void menuPrincipal() {
		int action = 0;
		do {
			this.resetVues();
			this.resetJeu();
			this.setVueMenuStarWars();
			action = this._vueMenuStarwars.menu();
			switch (action) {
				case 1:		// On veut créer une partie
					int a = this.nouvellePartie();
					if (a != Vue.QUITTER)
						this.jouer();
					break;
				case 2:		// On veut charger une partie et ses vaisseaux
					this.chargerPartie();
					if (this.partie != null)
						this.jouer();
					break;
				case 3:		// Créer un objet
					this.creerObjet();
					break;
			}
			if (gagnant != null) {
				this.gagner();
			}
		} while (action != Vue.QUITTER);
	}
	
	//////////////////////////////////////////////////////
	// POUR LES PARTIES
	//////////////////////////////////////////////////////
	/**
	 * Créer une nouvelle partie, crée également les vaisseaux associés
	 * @return action != Vue.QUITTER la partie est créée, sinon tout est annulé
	 */
	public int nouvellePartie() {
		this.resetVues();
		this.setVuePartie();
		this.partie = this._vuePartie.nouvellePartie();
		
		this.resetVues();
		this.setVueJoueur();
		Controller.beginTransaction();
		
		this.nouveauTour(true);		// Tirage au sort de l'ordre de passage des joueurs
		partie.setTour(this.ordreTour.get(0));
		this.placeObjets();
		try {
			partie.save(connTransaction);
			for (ObjetsParties op : objetsP) {
				try {
					op.save(connTransaction);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (TorqueException e1) {
			e1.printStackTrace();
		}
		
		int action = 0;
		for (int i = 1; i <= this.nb_joueurs; i++) {
			Vaisseaux v = null;
			PartiesVaisseaux pv = null;
			
			action = this._vueJoueur.menuJoueur(i, VaisseauxPeer.doSelectAll().size());
			
			switch (action) {
				case 1: 			// Créer un vaisseau
					v = this._vueJoueur.nouveauVaisseau(i);
					pv = this._vueJoueur.setCaracs(i);
				try {
					v.save(connTransaction);
				} catch (TorqueException e2) {
					e2.printStackTrace();
				}
					break;
				case 2: 			// Utiliser un vaisseau existant
					v = this._vueJoueur.chargerVaisseau(i, VaisseauxPeer.doSelectAllNotSelected(this.vaisseaux));
					if (v != null) {	// Si le joeur n'a pas quitter
						pv = this._vueJoueur.setCaracs(i);
						try {
							pv.setNomPartie(this.partie.getNom());
							pv.setNomVaisseau(v.getNom());
						} catch (TorqueException e1) {
							e1.printStackTrace();
						}
					} else {
						Controller.rollBack();
						this.resetJeu();
						i = this.nb_joueurs+1;
						v = null;
						pv = null;
						action = Vue.QUITTER;
					}
					break;
				case Vue.QUITTER:
					Controller.rollBack();
					this.resetJeu();
					i = this.nb_joueurs+1;
					v = null;
					pv = null;
					break;
			}
			
			if (action != Vue.QUITTER) {
				// TODO Faire une fonction pour trouver les coordonnées
				if (i == 1) {
					pv.setCoordX(2);
					pv.setCoordY(0);
				}
				else if (i == 2) {
					pv.setCoordX(2);
					pv.setCoordY(4);
				}
				
				this.vaisseaux.add(v);
				this.partieV.add(pv);
				try {
					pv.setNomPartie(partie.getNom());
					pv.setNomVaisseau(v.getNom());
					pv.setNumJoueur(i);
					pv.save(connTransaction);
				} catch (TorqueException e) {
					e.printStackTrace();
				}
			}
		} // Fin sélection vaisseaux joueurs
		
		// Si n'a pas quitter à un moment (création ou chargement d'un vaisseau)
		if (action != Vue.QUITTER) {
			try {
				Controller.commitTransaction();
			} catch (Exception e2) {
				e2.printStackTrace();
				Controller.rollBack();
			}
		}
		return action;
	}
	/**
	 * Charger une partie (partie et éléments associés : vaisseaux, objets...)
	 */
	@SuppressWarnings("unchecked")
	public void chargerPartie() {
		this.resetVues();
		this.setVuePartie();
		try {
			List<Parties> lp = PartiesPeer.doSelectAll();
			if (lp.size() > 0) {
				this.partie = this._vuePartie.chargerPartie(lp);
				if (this.partie != null) {
					this.partieV = this.partie.getPartiesVaisseauxsOrdered();
					this.objetsP = this.partie.getObjetsPartiess();
					for (PartiesVaisseaux v : this.partieV)
						this.vaisseaux.add(v.getVaisseaux());
					for (ObjetsParties op : this.objetsP)
						this.objets.add(op.getObjets());
					this.nouveauTour(true);
				}
			} else {
				System.out.println("Aucune partie n'a été créée");
			}
			
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Partie en cours : présente le menu, le plateau et détermine les actions du joueur
	 */
	public void jouer() {
		this.resetVues();
		this.setVuePlateau();
		this.setVueJoueur();
		int action = Vue.QUITTER;
		int numJoueur = partie.getNumjoueur();
		int joueurActif = this.ordreTour.get(numJoueur);
		
		do {
			this._vuePlateau.afficher(partieV, vaisseaux, objetsP);
			action = this._vuePlateau.menu(joueurActif);
			
			switch(action) {
				case 1:		// deplacer ==> loi de reussite dans la fonction deplacer() car retrait de PA différent suivant le cas
					if (this.memeCase(this.getJoueur(joueurActif).getCoordX(), this.getJoueur(joueurActif).getCoordY(), this.getJoueur(joueurActif).getNomVaisseau())
							&& this.getJoueur(joueurActif).getPa() < 4)
						System.out.println("Vous ne pouvez pas vous déplacer, vous devez avoir au moins 4 points d'action");
					else
					{
						int deplct = this._vuePlateau.deplacement(joueurActif);
						this.deplacer(joueurActif, deplct);
					}
					break;
				case 2:		// Attaquer
					if (!this.memeCase(this.getJoueur(joueurActif).getCoordX(), this.getJoueur(joueurActif).getCoordY(), this.getJoueur(joueurActif).getNomVaisseau())) {
						System.out.println("Vous ne pouvez pas attaquer, vous devez être sur la même case qu'un adversaire");
					} else {
						if (this.getJoueur(joueurActif).getPa() < 4)
							System.out.println("Vous n'avez pas assez de point d'action");
						else {
							this.attaquer(joueurActif);
							if (gagnant != null) {
								//fin de la partie
								action = Vue.QUITTER;
							}
						}
					}
					break;
				case 3:		// Ramasser
					if (!this.objetPresent(this.getJoueur(joueurActif).getCoordX(), this.getJoueur(joueurActif).getCoordY())) {
						System.out.println("Vous ne pouvez pas ramasser d'objet, il n'y en a pas sur votre case");
					} else {
						this.ramasserObjet(joueurActif);
					}
					break;
				case 4:		// Utiliser
					break;
				case 5:		// Equiper
					if (this.getJoueur(joueurActif).getPa() < 2)
						System.out.println("Vous n'avez pas assez de point d'action");
					else
					{
						if (this.armesDansEquipement(joueurActif))
							this.equiperArme(joueurActif);
						else
							System.out.println("Vous n'avez pas d'armes dans votre équipement");
					}
					break;
				case 6:		// Lister les objets
					this.listerObjets(joueurActif);
					break;
				case 7:		// fin tour
					if (numJoueur == nb_joueurs-1) { //fin du tour global
						this.nouveauTour(false);
						numJoueur = 0;
					}
					else
						numJoueur++;
					partie.setTour(joueurActif);
					partie.setNumjoueur(numJoueur);
					joueurActif = this.ordreTour.get(numJoueur);
					break;
				case 0:		// Quitter
					action = Vue.QUITTER;
					this.sauverPartie();
					break;
			}
		} while (action != Vue.QUITTER);
	}
	/**
	 * Déplacement d'un jouer
	 * @param numJoueur numéro du joueur en déplacement
	 * @param deplct Direction du déplacement : 8 => haut, 2 => bas, 4 => gauche, 6 => droite 
	 */
	private void deplacer(int numJoueur, int deplct) {
		if (memeCase(getJoueur(numJoueur).getCoordX(), getJoueur(numJoueur).getCoordY(), getJoueur(numJoueur).getNomVaisseau())) {
			getJoueur(numJoueur).setPa(getJoueur(numJoueur).getPa()-4);
		} else {
			getJoueur(numJoueur).setPa(getJoueur(numJoueur).getPa()-1);
		}
		if (this.loiReussite()) {
			switch (deplct) {
				case 8:
					getJoueur(numJoueur).setCoordY(getJoueur(numJoueur).getCoordY()-1);
					break;
				case 2:
					getJoueur(numJoueur).setCoordY(getJoueur(numJoueur).getCoordY()+1);
					break;
				case 4:
					getJoueur(numJoueur).setCoordX(getJoueur(numJoueur).getCoordX()-1);
					break;
				case 6:
					getJoueur(numJoueur).setCoordX(getJoueur(numJoueur).getCoordX()+1);
					break;
				default:
					break;
			}
		} else {
			System.out.println(" /!\\ Echec critique /!\\ vous n'avez plus de poussière intergalactique !");
		}
	}
	/**
	 * Attaquer un autre joueur
	 * @param numJoueur numéro du joueur attaquant
	 */
	public void attaquer (int numJoueur) {
		int v = this._vueJoueur.attaquer(this.partieV, getJoueur(numJoueur).getCoordX(), getJoueur(numJoueur).getCoordY(), getJoueur(numJoueur).getNomVaisseau());
		PartiesVaisseaux atq = this.getJoueur(numJoueur);	// Détermine l'attaquant
		PartiesVaisseaux def = this.getJoueur(v-1);			// Détermine le défenseur
		if (def.getNomVaisseau().equals(atq.getNomVaisseau())) {	// si l'attaquant s'attaque lui meme
			System.out.println("Vous ne pouvez pas vous attaquer vous même");
		} else {
			atq.setPa(this.getJoueur(numJoueur).getPa()-4);
			// loi de reussite
			if (this.loiReussite()) {
				Random r = new Random();
				int ptsDef = 0, ptsAtq = 0;
				for (int i = 0; i < def.getChamp(); i++)
					ptsDef += r.nextInt(3)+1;
				for (int i = 0; i < atq.getAttaque(); i++)
					ptsAtq += r.nextInt(3)+1;
				for (ObjetsVaisseaux ov : this.getArmesEquipees(numJoueur)) {
					Objets o;
					try {
						o = ov.getObjets();
						ptsAtq += o.getPoints();
					} catch (TorqueException e) {
						e.printStackTrace();
					}
				}
				
				// Si le défenseur n'a pas paré l'attaque
				if (ptsDef < ptsAtq) {
					int ptsDeg = 0;
					for (int i = 0; i < atq.getDegats(); i++)
						ptsDeg+= r.nextInt(3)+1;
					System.out.println("Points Def :" +ptsDef);
					System.out.println("Points Atq :" +ptsAtq);
					System.out.println("Points Deg :" +ptsDeg);
					def.setEnergie(def.getEnergie()-ptsDeg);
					System.out.println("Touché !");
					// Si le défenseur n'a plus d'énergie
					if (def.getEnergie() <= 0) {
						try {
							gagnant = atq.getVaisseaux();
						} catch (TorqueException e) {
							e.printStackTrace();
						}
					}
				}
				else
					System.out.println("Votre ennemi est trop fort pour vous...");
			}
			else
				System.out.println(" /!\\ Echec critique /!\\ votre fusil à proton a surchauffé !");
		}
	}
	/**
	 * Affiche un message au joueur gagnant 
	 */
	public void gagner () {
		this._vueJoueur.gagner(this.gagnant.getNom(), partie.getNom());
		PartiesPeer.doDeletePartie(this.partie);
		this.resetJeu();
	}
	/**
	 * Permet de savoir s'il y a un autre vaisseau sur la case
	 * @param coordX Abscisse du joueur
	 * @param coordY Ordonnée du joueur
	 * @param nomVaisseau Nom du vaisseau
	 * @return vrai s'il y a un autre joueur que nomVaisseau, faux sinon
	 */
	public boolean memeCase(int coordX, int coordY, String nomVaisseau) {
		boolean mmcase = false;
		for (PartiesVaisseaux pv : partieV) {
			if (pv.getCoordX() == coordX && pv.getCoordY() == coordY && pv.getNomVaisseau() != (nomVaisseau)) {
				mmcase = true;
			}
		}
		return mmcase;
	}
	/**
	 * Permet de savoir si une action a échoée ou non
	 * @return 80% de réussite
	 */
	public boolean loiReussite() {
		Random r = new Random();
		int i = r.nextInt(100)+1;
		if (i <= 80)
			return true;
		else
			return false;
	}
	/**
	 * Enregistre la parte et les éléments associés (vaisseaux, objets...)
	 */
	private void sauverPartie() {
		try {
			Controller.beginTransaction();
			partie.save(connTransaction);
			for (PartiesVaisseaux pv : partieV)
				pv.save(connTransaction);
			for (ObjetsParties op : objetsP)
				op.save(connTransaction);
			Controller.commitTransaction();
			System.out.print("Partie enregistrée et fin de la partie");
		} catch (TorqueException e) {
			Controller.rollBack();
			System.out.println("La partie n'a pas pu être sauvegardée");
			e.printStackTrace();
		}
	}
	/**
	 * Défini aléatoirement l'ordre de passage des joueurs
	 * @param nouvPartie Si vrai, on ne remet pas à jour les points d'action
	 */
	public void nouveauTour(boolean nouvPartie) {
		Random r = new Random();
		this.ordreTour.clear();
		int joueur;
		if (!nouvPartie)
			joueur = r.nextInt(nb_joueurs);
		else
			joueur = partie.getTour();
		
		for (int i = 0; i < nb_joueurs; i++) {
			if (!nouvPartie) {
				this.getJoueur(i).setPa(6);
				for (ObjetsVaisseaux o : this.getObjetsEquipe(i)) {
					o.setDureeRestante(o.getDureeRestante()-1);
					if (o.getDureeRestante() == 0) {
						try {
							this.getJoueur(i).getObjetsVaisseauxs().remove(o);
							ObjetsVaisseauxPeer.doDelete(o);
						} catch (TorqueException e) {
							e.printStackTrace();
						}
					}
				}
			}
			this.ordreTour.add(joueur);
			if (joueur != nb_joueurs-1)
				joueur++;
			else
				joueur = 0;
		}
	}
	
	//////////////////////////////////////////////////////
	// POUR LES OBJETS
	//////////////////////////////////////////////////////
	/**
	 * Créer un objet
	 */
	public void creerObjet () {
		this.resetVues();
		this.setVueObjet();
		Objets o = this._vueObjet.creer(CaracteristiquesPeer.doSelectAll(), TypesPeer.doSelectAll());
		try {
			o.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Placer tire des objets aléotoirement et les place sur la grille aléatoirement
	 */
	private void placeObjets() {
		//ici on ajoute un certain nombre d'objets présents dans la base de façon aleatoire dans le liste d'objets
		List<Objets> obj;
		Random r = new Random();
		obj = ObjetsPeer.doSelectAll();
		
		if (obj.size() != 0) {
			int nb_obj = obj.size();
			for (int i = 0 ; i < nb_objets; i++) {
				if (nb_obj != 0) {
					int nombre = r.nextInt(nb_obj);
					Objets o = obj.get(nombre);
					ObjetsParties objp = new ObjetsParties(
							o.getNom(),
							this.partie.getNom(),
							r.nextInt(dimension),
							r.nextInt(dimension)
							);
					objetsP.add(objp);
					try {
						objets.add(objp.getObjets());
					} catch (TorqueException e) {
						e.printStackTrace();
					}
					obj.remove(nombre);
					nb_obj--;
				}
			}
		}
	}
	/**
	 * Ramasser un objet
	 * Met à jour l'équipement du joueur et ses points d'action
	 * @param numJoueur numéro du joueur
	 */
	public void ramasserObjet (int numJoueur) {
		int c = this._vueJoueur.ramasserObjet(getJoueur(numJoueur).getCoordX(), getJoueur(numJoueur).getCoordY());
		ObjetsParties objetRamasse = this.getObjetPartie(c);
		
		ObjetsVaisseaux objV = new ObjetsVaisseaux(this.getJoueur(numJoueur).getNomVaisseau(), partie.getNom(), objetRamasse.getNomObjet());
		this.getJoueur(numJoueur).setPa(this.getJoueur(numJoueur).getPa()-1);
		try {
			if (this.loiReussite()) {
				this.getJoueur(numJoueur).addObjetsVaisseaux(objV);
				this.objetsP.remove(c);
				this.objets.remove(objetRamasse.getObjets());
				ObjetsPartiesPeer.doDelete(objetRamasse);
			} else {
				System.out.println(" /!\\ Echec critique /!\\ panne hydrolique imminente !");
			}
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Lister les objets pour un joueur (action du menu en cours du jeu)
	 * @param numJoueur numéro du joueur
	 */
	@SuppressWarnings("unchecked")
	public void listerObjets(int numJoueur) {
		try {
			this._vueJoueur.listerObjets(getJoueur(numJoueur).getObjetsVaisseauxs());
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Equiper une arme pour un joueur
	 * Met à jour les armes équipées ou non, leur durée restante et les points d'action
	 * @param numJoueur numéro du joueur
	 */
	@SuppressWarnings("unchecked")
	public void equiperArme(int numJoueur) {
		try {
			List<ObjetsVaisseaux> objets = getJoueur(numJoueur).getObjetsVaisseauxs();
			int arme = this._vueJoueur.equiperArme(this.getArmes(numJoueur));
			getJoueur(numJoueur).setPa(getJoueur(numJoueur).getPa()-2);
			if (this.loiReussite()) {
				objets.get(arme).setEquipe(true);
				objets.get(arme).setDureeRestante(objets.get(arme).getObjets().getDuree());
				StringBuffer liaison = new StringBuffer(" de ");
				StringBuffer typeArme = new StringBuffer(objets.get(arme).getObjets().getType());
				String tours = " tours.";
				char voyelles[] = {'a', 'e', 'i', 'o', 'u', 'y'};
				
				if (objets.get(arme).getObjets().getDuree() == 1)
					tours = " tour.";
				if (Fonctions.in_array(typeArme.charAt(0), voyelles)) {
					liaison.setCharAt(2, '\'');
					liaison.deleteCharAt(3);
				}
				
				System.out.print("Vous avez équiper " + objets.get(arme).getObjets().getNom());
				System.out.print(" vous points " + liaison + objets.get(arme).getObjets().getCarac());
				System.out.print(" son maintenant de " + this.getNouveauxPoints(numJoueur, objets.get(arme).getObjets().getCarac()));
				System.out.println(" pour " + objets.get(arme).getObjets().getDuree() + tours);
			}
			else
				System.out.println("/!\\ Echec critique /!\\ Vous avez cassé une pièce !");
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Savoir s'il y a un objet sur la case (x, y)
	 * @param x Abscice de la case
	 * @param y Ordonnée de la case
	 * @return Vrai s'il y a un objet, faux sinon
	 */
	private boolean objetPresent(int x, int y) {
		boolean r = false;
		for (ObjetsParties op : this.objetsP) {
			if (op.getCoordX() == x && op.getCoordY() == y)
				r = true;
		}
		return r;
	}
	/**
	 * Savoir si le joueur a des armes dans son équipement
	 * @param numJoueur numéro du joueur
	 * @return vrai si je le joueur a au moins une arme dans son équipement, faux sinon
	 */
	private boolean armesDansEquipement(int numJoueur) {
		return this.getArmes(numJoueur).size() > 0;
	}
	/**
	 * Retourne la liste des armes d'un joueur
	 * @param vaisseau Vaisseau concerné
	 * @return Liste des armes
	 */
	@SuppressWarnings("unchecked")
	private List<ObjetsVaisseaux> getArmes(PartiesVaisseaux vaisseau) {
		List<ObjetsVaisseaux> armes = new ArrayList<ObjetsVaisseaux>();
		try {
			List<ObjetsVaisseaux> objV = vaisseau.getObjetsVaisseauxs();
			for (ObjetsVaisseaux o : objV) {
				if (o.getObjets().getType().equalsIgnoreCase("arme"))
					armes.add(o);
			}
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		
		return armes;
	}
	/**
	 * Retourne la liste des armes d'un joueur en jeu
	 * @param numJoueur numéro du joueur
	 * @return liste des armes
	 */
	public List<ObjetsVaisseaux> getArmes(int numJoueur) {
		return this.getArmes(this.getJoueur(numJoueur));
	}
	/**
	 * Retourne la liste des armes équipées d'un joueur en jeu
	 * @param vaisseau Vaisseau concerné
	 * @return Liste des armes équipées
	 */
	@SuppressWarnings("unchecked")
	private List<ObjetsVaisseaux> getArmesEquipees(PartiesVaisseaux vaisseau) {
		List<ObjetsVaisseaux> armes = new ArrayList<ObjetsVaisseaux>();
		try {
			List<ObjetsVaisseaux> objV = vaisseau.getObjetsVaisseauxs();
			for (ObjetsVaisseaux o : objV) {
				if (o.getObjets().getType().equalsIgnoreCase("arme") && o.getEquipe())
					armes.add(o);
			}
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		
		return armes;
	}
	/**
	 * Retourne les objets equipés qui sont des armes d'un joueur de la partie
	 * @param numJoueur numéro du joueur concerné
	 * @return Liste des armes équipées
	 */
	public List<ObjetsVaisseaux> getArmesEquipees(int numJoueur) {
		return this.getArmesEquipees(this.getJoueur(numJoueur));
	}
	
	/**
	 * Retourne les objets équipes d'un joueur en jeu
	 * @param vaisseau Vaisseau concerné
	 * @return Liste des objets équipés
	 */
	@SuppressWarnings("unchecked")
	private List<ObjetsVaisseaux> getObjetsEquipe(PartiesVaisseaux vaisseau) {
		List<ObjetsVaisseaux> objets = new ArrayList<ObjetsVaisseaux>();
		try {
			List<ObjetsVaisseaux> objV = vaisseau.getObjetsVaisseauxs();
			for (ObjetsVaisseaux o : objV) {
				if (o.getEquipe())
					objets.add(o);
			}
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		
		return objets;
	}
	/**
	 * Retourne les objets équipes d'un joueur en jeu
	 * @param numJoueur numero du joueur
	 * @return list des objets equipes
	 */
	public List<ObjetsVaisseaux> getObjetsEquipe(int numJoueur) {
		return this.getObjetsEquipe(this.getJoueur(numJoueur));
	}
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////
	// POUR LA BD
	//////////////////////////////////////////////////////
	/**
	 * Connexion à la base de donnée
	 */
	public static void connexion() throws SQLException, TorqueException {
		Torque.init(TORQUE_PROPS);
		String url = "jdbc:postgresql://postgres-info/base5a00";
		conn = DriverManager.getConnection(url, "user5a00", "p00");
	}
	/**
	 * Fermer la connexion à la base de donnée
	 */
	public static void finConnexion() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Commencer une transaction
	 */
	protected static void beginTransaction() {
		try {
			connTransaction = Transaction.begin();
		} catch (TorqueException e2) {
			e2.printStackTrace();
		}
	}
	/**
	 * Comitter la transaction
	 */
	protected static void commitTransaction() {
		try {
			Transaction.commit(connTransaction);
		} catch (Exception e) {
			Controller.rollBack();
			e.printStackTrace();
		}
	}
	/**
	 * Annuler la transaction
	 */
	protected static void rollBack() {
		try {
			Transaction.rollback(connTransaction);
		} catch (TorqueException e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * Réinitialise les variables du jeu
	 */
	private void resetJeu() {
		this.vaisseaux.clear();
		this.partieV.clear();
		this.objets.clear();
		this.objetsP.clear();
		this.ordreTour.clear();
		this.partie = new Parties();
		this.gagnant		= null;
	}
	
	//////////////////////////////////////////////////////
	// POUR LES VUES
	//////////////////////////////////////////////////////
	public void setVueMenuStarWars() {
		this._vueMenuStarwars = new VueMenuStarWars(this);
	}
	
	public void setVuePartie() {
		this._vuePartie = new VuePartie(this);
	}
	public void setVueJoueur() {
		this._vueJoueur = new VueJoueur(this);
	}
	public void setVuePlateau() {
		this._vuePlateau = new VuePlateau(this, this.dimension);
	}
	public void setVueObjet() {
		this._vueObjet = new VueObjet(this);
	}
	/**
	 * Réinitialise les vues
	 */
	public void resetVues() {
		this._vueMenuStarwars	= null;
		this._vuePartie			= null;
		this._vueJoueur			= null;
		this._vuePlateau		= null;
		this._vueObjet			= null;
	}
	/**
	 * Récupère un joueur dans la partie en cours
	 * @param numJoueur numéro du joueur
	 * @return PartiesVaisseaux numJoueur
	 */
	public PartiesVaisseaux getJoueur(int numJoueur) {
		return this.partieV.get(numJoueur);
	}
	/**
	 * Récupère un objet présent dans la partie en cours
	 * @param numObjet numéro de l'objet
	 * @return ObjetsParties numObjet
	 */
	public ObjetsParties getObjetPartie(int numObjet) {
		return this.objetsP.get(numObjet);
	}
	/**
	 * Retourne tous les objets de la partie en cours
	 * Accessible depuis les vues
	 * @return List<ObjetsParties>
	 */
	public List<ObjetsParties> getObjetsParties() {
		return this.objetsP;
	}
	/**
	 * Retourne un objet de la partie en cours
	 * @param numObjet numéro de l'objet
	 * @return Objets
	 */
	public Objets getObjet(int numObjet) {
		return this.objets.get(numObjet);
	}
	/**
	 * Retourne le nombre de points de la caractéristique du joueur donné
	 * @param numJoueur numéro du joueur
	 * @param carac caractéristique à récupérer
	 * @return le nombre de points pour la caractéristique
	 */
	private int getNouveauxPoints (int numJoueur, String carac) {
		int pts = 0;
		if (carac.equals("attaque"))
			pts = this.getJoueur(numJoueur).getAttaqueImproved();
		else if (carac.equals("degats"))
			pts = this.getJoueur(numJoueur).getDegatsImproved();
		else if (carac.equals("champ"))
			pts = this.getJoueur(numJoueur).getChampImproved();
		else if (carac.equals("energie"))
			pts = this.getJoueur(numJoueur).getEnergieImproved();
		return pts;
	}
	
}
