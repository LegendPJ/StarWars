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

import torque.generated.Parties;
import torque.generated.PartiesPeer;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;
import torque.generated.VaisseauxPeer;


public class Controller {
	protected static Connection conn, connTransaction;
	private final static String TORQUE_PROPS = new String("torque-3.3//Torque.properties");
	
	private final int nb_joueurs = 2;
	private List<Integer> ordreTour;
	
	// Vaisseau et partie en cours
	protected List<Vaisseaux> vaisseaux;
	protected List<PartiesVaisseaux> partieV;
	protected Parties partie;

	// Les vues
	private VueMenuStarWars	_vueMenuStarwars;
	private VuePartie		_vuePartie;
	private VueJoueur		_vueJoueur;
	private VuePlateau		_vuePlateau;
	
	public Controller() {
		this.resetJeu();
		this.resetVues();
		this.ordreTour = new ArrayList<Integer>();
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
					this.nouvellePartie();
					this.jouer();
					break;
				case 2: // On veut charger une partie et ses vaisseaux
					this.chargerPartie();
					this.jouer();
					break;
			}
		} while (action != Vue.QUITTER);
		// ici on quitte
	}
	
	//////////////////////////////////////////////////////
	// POUR LES PARTIES
	//////////////////////////////////////////////////////
	public void nouvellePartie() {
		this.resetVues();
		this.setVuePartie();
		Parties partie = this._vuePartie.nouvellePartie();
		
		this.resetVues();
		this.setVueJoueur();
		Controller.beginTransaction();
		
		Random r = new Random();
		partie.setTour(r.nextInt(nb_joueurs));
		try {
			partie.save(connTransaction);
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
					pv.setCoordX(4);
					pv.setCoordY(0);
				}
				else if (i == 2) {
					pv.setCoordX(5);
					pv.setCoordY(9);
				}
				
				vaisseaux.add(v);
				partieV.add(pv);
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
		
		// Si on veut quitter la création de nouvelle partie
		if (action != Vue.QUITTER) {
			try {
				Controller.commitTransaction();
			} catch (Exception e2) {
				e2.printStackTrace();
				Controller.rollBack();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void chargerPartie() {
		this.resetVues();
		this.setVuePartie();
		try {
			this.partie = this._vuePartie.chargerPartie(PartiesPeer.doSelectAll());
			
			if (this.partie != null) {
				this.partieV = this.partie.getPartiesVaisseauxs();
				for (PartiesVaisseaux v : this.partieV)
					this.vaisseaux.add(v.getVaisseaux());
			}
			
		} catch (TorqueException e) {
			System.out.println("Aucune partie n'a été créée");
		}
	}
	
	public void jouer() {
		this.resetVues();
		this.setVuePlateau();
		int action = Vue.QUITTER;
		int joueurActif = 0;
		this.nouveauTour();
		do {
			this._vuePlateau.afficher(partieV, vaisseaux);
			action = this._vuePlateau.menu(this.ordreTour.get(joueurActif));
			
			switch(action) {
			case 1:		// deplacer
				int deplct = this._vuePlateau.deplacement(joueurActif);
				this.deplacer(joueurActif, deplct);
				break;
			case 2:		// Attaquer
				break;
			case 3:		// ramasser
				break;
			case 4:		// utilise
				break;
			case 5:		// equiper
				break;
			case 6:		// fin tour
				if (joueurActif == this.ordreTour.size()-1) { //fin du tour global
					this.nouveauTour();
					joueurActif = 0;
				}
				else
					joueurActif++;
				break;
			case 0:		// Quitter
				action = Vue.QUITTER;
				this.sauverPartie();
				break;
			}
		} while (action != Vue.QUITTER);
	}
	
	private void deplacer(int joueurActif, int deplct) {
		switch (deplct) {
		case 8:
			getJoueur(joueurActif).setCoordY(getJoueur(joueurActif).getCoordY()-1);
			break;
		case 2:
			getJoueur(joueurActif).setCoordY(getJoueur(joueurActif).getCoordY()+1);
			break;
		case 4:
			getJoueur(joueurActif).setCoordX(getJoueur(joueurActif).getCoordX()-1);
			break;
		case 6:
			getJoueur(joueurActif).setCoordX(getJoueur(joueurActif).getCoordX()+1);
			break;
		default:
			break;
		}
		System.out.println(getJoueur(joueurActif).getNomVaisseau());
		if (memeCase(getJoueur(joueurActif).getCoordX(), getJoueur(joueurActif).getCoordY(), getJoueur(joueurActif).getNomVaisseau())) {
			getJoueur(joueurActif).setPa(getJoueur(joueurActif).getPa()-4);
		} else {
			getJoueur(joueurActif).setPa(getJoueur(joueurActif).getPa()-1);
		}
	}

	private boolean memeCase(int coordX, int coordY, String nomVaisseau) {
		boolean mmcase = false;
		for (PartiesVaisseaux pv : partieV) {
			if (pv.getCoordX() == coordX && pv.getCoordY() == coordY && !pv.getNomVaisseau().equals(nomVaisseau)) {
				mmcase = true;
			}
		}
		return mmcase;
	}

	private void sauverPartie() {
		// TODO Auto-generated method stub
		System.out.print("Partie enregistrée et fin de la partie");
	}

	public void nouveauTour() {
		Random r = new Random();
		int joueur = r.nextInt(nb_joueurs);
		for (int i = 0; i < nb_joueurs; i++) {
			this.partieV.get(i).setPa(6);
			this.ordreTour.add(joueur);
			if (joueur != nb_joueurs-1)
				joueur++;
			else
				joueur = 0;
			System.out.println(joueur);
		}
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
	
	protected int joueurTour(int joueur) {
			if (joueur < nb_joueurs)
				joueur++;
			else if (joueur == nb_joueurs)
				joueur = 1;

		return joueur;
	}
	
	private void resetJeu() {
		vaisseaux	= new ArrayList<Vaisseaux>();
		partieV		= new ArrayList<PartiesVaisseaux>();
		partie		= new Parties();
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
		this._vuePlateau= new VuePlateau(this, 10);
	}
	
	public void resetVues() {
		this._vueMenuStarwars	= null;
		this._vuePartie			= null;
		this._vueJoueur			= null;
		this._vuePlateau		= null;
	}

	public PartiesVaisseaux getJoueur(int numJoueurActif) {
		return this.partieV.get(numJoueurActif);
	}
	
}
