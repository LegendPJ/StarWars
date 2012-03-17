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

import Vues.Vue;
import Vues.VueJoueur;
import Vues.VueMenuStarWars;
import Vues.VuePartie;
import Vues.VuePlateau;

import torque.generated.CaracteristiquesPeer;
import torque.generated.Objets;
import torque.generated.ObjetsParties;
import torque.generated.ObjetsPartiesPeer;
import torque.generated.ObjetsPeer;
import torque.generated.ObjetsVaisseaux;
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
	
	public void menuPrincipal() {
		int action = 0;
		do {
			this.resetVues();
			this.resetJeu();
			this.setVueMenuStarWars();
			action = this._vueMenuStarwars.menu();
			switch (action) {
				case 1: // On veut créer une partie
					int a = this.nouvellePartie();
					if (a != Vue.QUITTER)
						this.jouer();
					break;
				case 2: // On veut charger une partie et ses vaisseaux
					this.chargerPartie();
					if (this.partie != null)
						this.jouer();
					break;
				case 3:
					this.creerObjet();
					break;
			}
			if (gagnant != null) {
				this.gagner();
			}
		} while (action != Vue.QUITTER);
		// ici on quitte
	}
	
	//////////////////////////////////////////////////////
	// POUR LES PARTIES
	//////////////////////////////////////////////////////
	public int nouvellePartie() {
		this.resetVues();
		this.setVuePartie();
		this.partie = this._vuePartie.nouvellePartie();
		
		this.resetVues();
		this.setVueJoueur();
		Controller.beginTransaction();
		
		this.nouveauTour(true);
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
		for (int i = 1; i <= nb_joueurs; i++) {
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
						i = nb_joueurs+1;
						v = null;
						pv = null;
						action = Vue.QUITTER;
					}
					break;
				case Vue.QUITTER:
					Controller.rollBack();
					this.resetJeu();
					i = nb_joueurs+1;
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
				
				vaisseaux.add(v);
				partieV.add(pv);
				try {
					pv.setNomPartie(partie.getNom());
					pv.setNomVaisseau(v.getNom());
					pv.setNumJoueur(i);
					pv.save(connTransaction);
					this.nouveauTour(true);
				} catch (TorqueException e) {
					e.printStackTrace();
				}
			}
		} // Fin sélection vaisseaux joueurs
		
		// Si on veut quitter la création de nouvelle partie
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

	@SuppressWarnings("unchecked")
	public void chargerPartie() {
		this.resetVues();
		this.setVuePartie();
		try {
			this.partie = this._vuePartie.chargerPartie(PartiesPeer.doSelectAll());
			if (this.partie != null) {
				this.partieV = this.partie.getPartiesVaisseauxsOrdered();
				this.objetsP = this.partie.getObjetsPartiess();
				for (PartiesVaisseaux v : this.partieV)
					this.vaisseaux.add(v.getVaisseaux());
				for (ObjetsParties op : this.objetsP)
					this.objets.add(op.getObjets());
				this.nouveauTour(true);
			}
			
		} catch (TorqueException e) {
			System.out.println("Aucune partie n'a été créée");
		}
	}
	
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
	
	public void attaquer (int numJoueur) {
		int v = this._vueJoueur.attaquer(this.partieV, getJoueur(numJoueur).getCoordX(), getJoueur(numJoueur).getCoordY(), getJoueur(numJoueur).getNomVaisseau());
		PartiesVaisseaux atq = this.getJoueur(numJoueur);
		PartiesVaisseaux def = this.getJoueur(v-1);
		if (def.getNomVaisseau().equals(atq.getNomVaisseau())) {
			System.out.println("Vous ne pouvez pas vous attaquer vous même");
		} else {
			atq.setPa(this.getJoueur(numJoueur).getPa()-4);
			// loi de reussite
			if (this.loiReussite()) {
				Random r = new Random();
				int ptsDef = 0, ptsAtq = 0;
				for (int i = 0; i < def.getChamp(); i++)
					ptsDef += r.nextInt(3)+1;
				System.out.println("Points Def :" +ptsDef);
				for (int i = 0; i < atq.getAttaque(); i++)
					ptsAtq += r.nextInt(3)+1;
				System.out.println("Points Atq :" +ptsAtq);
				if (ptsDef < ptsAtq)
				{
					int ptsDeg = 0;
					for (int i = 0; i < atq.getDegats(); i++)
						ptsDeg+= r.nextInt(3)+1;
					System.out.println("Points Deg :" +ptsDeg);
					def.setEnergie(def.getEnergie()-ptsDeg);
					System.out.println("Touché !");
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
	
	public void gagner () {
		this._vueJoueur.gagner(this.gagnant.getNom(), partie.getNom());
		PartiesPeer.doDeletePartie(this.partie);
		this.resetJeu();
	}

	public boolean memeCase(int coordX, int coordY, String nomVaisseau) {
		boolean mmcase = false;
		for (PartiesVaisseaux pv : partieV) {
			if (pv.getCoordX() == coordX && pv.getCoordY() == coordY && pv.getNomVaisseau() != (nomVaisseau)) {
				mmcase = true;
			}
		}
		return mmcase;
	}
	
	public boolean loiReussite() {
		Random r = new Random();
		int i = r.nextInt(100)+1;
		if (i <= 80)
			return true;
		else
			return false;
	}

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

	public void nouveauTour(boolean nouvPartie) {
		Random r = new Random();
		int joueur;
		if (!nouvPartie)
			joueur = r.nextInt(nb_joueurs);
		else
			joueur = partie.getTour();
		
		for (int i = 0; i < nb_joueurs; i++) {
			if (!nouvPartie)
				this.partieV.get(i).setPa(6);
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
	
	@SuppressWarnings("unchecked")
	public void listerObjets(int numJoueur) {
		try {
			this._vueJoueur.listerObjets(getJoueur(numJoueur).getObjetsVaisseauxs());
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}
	
	private boolean objetPresent(int x, int y) {
		boolean r = false;
		for (ObjetsParties op : this.objetsP) {
			if (op.getCoordX() == x && op.getCoordY() == y)
				r = true;
		}
		return r;
	}
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////
	// POUR LA BD
	//////////////////////////////////////////////////////
	/**
	 * Connexion à la base de donnée
	 * @throws SQLException 
	 * @throws TorqueException 
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
	
	private void resetJeu() {
		vaisseaux	= new ArrayList<Vaisseaux>();
		partieV		= new ArrayList<PartiesVaisseaux>();
		objets		= new ArrayList<Objets>();
		objetsP		= new ArrayList<ObjetsParties>();
		partie		= new Parties();
		ordreTour	= new ArrayList<Integer>();
		gagnant		= null;
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
	
	public void resetVues() {
		this._vueMenuStarwars	= null;
		this._vuePartie			= null;
		this._vueJoueur			= null;
		this._vuePlateau		= null;
		this._vueObjet			= null;
	}

	public PartiesVaisseaux getJoueur(int numJoueur) {
		return this.partieV.get(numJoueur);
	}
	public ObjetsParties getObjetPartie(int numObjet) {
		return this.objetsP.get(numObjet);
	}
	public List<ObjetsParties> getObjetsParties() {
		return this.objetsP;
	}
	public Objets getObjet(int numObjet) {
		return this.objets.get(numObjet);
	}
	
}
