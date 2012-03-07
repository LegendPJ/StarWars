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

import Vues.VueMenuStarWars;
import Vues.VuePartie;
import Vues.VueJoueur;
//import Vues.VuePlateau;

import torque.generated.Parties;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;


public class Controller {
	protected static Connection conn, connTransaction;
	private final static String TORQUE_PROPS = new String("torque-3.3//Torque.properties");
	
	private int nb_joueurs = 2;
	
	// Variables du jeu
	protected List<Vaisseaux> vaisseaux;
	protected List<PartiesVaisseaux> partieV;
	protected Parties partie;

	// Les vues
//	private VuePlateau		_vuePlateau;
	private VueMenuStarWars	_vueMenuStarwars;
	private VuePartie		_vuePartie;
	private VueJoueur		_vueJoueur;
	
	public Controller() {
		vaisseaux			= new ArrayList<Vaisseaux>();
		partieV				= new ArrayList<PartiesVaisseaux>();
		partie				= new Parties();
//		_vuePlateau 		= null;
		_vueMenuStarwars	= null;
		_vuePartie			= null;
	}
	
	public void menuPrincipal() {
		int action = 0;
		do {
			this.resetVues();
			this.setVueMenuStarWars();
			action = this._vueMenuStarwars.menu();
			switch (action) {
				case 1: // On veut créer une partie
					this.nouvellePartie();
					break;
				case 2: // On veut charger une partie et ses vaisseaux
					this.chargerPartie();
					break;
			}
		} while (action != 0);
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
		partie.setTour(r.nextInt(nb_joueurs)+1);
		try {
			partie.save(connTransaction);
		} catch (TorqueException e1) {
			e1.printStackTrace();
		}
		
		int action = 0;
		for (int i = 1; i <= nb_joueurs; i++) {
			action = this._vueJoueur.menuJoueur(i);
			Vaisseaux v = null;
			PartiesVaisseaux pv = null;
			
			switch (action) {
				case 1:
					v = this._vueJoueur.nouveauVaisseau(i);
					pv = this._vueJoueur.setCaracs(i);
					break;
				case 2:
					v = this._vueJoueur.chargerVaisseau(i);
					pv = this._vueJoueur.chargerPartieVaisseau(i);
					break;
				case 100: // Valeur de Vue.QUITTER
					Controller.rollBack();
					vaisseaux.clear();
					partieV.clear();
					break;
			}
			
			if (action != 100) {
				// TODO
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
					
					v.save(connTransaction);
					pv.save(connTransaction);
				} catch (TorqueException e) {
					e.printStackTrace();
				}
			}
		} // Fin sélection vaisseaux joueurs
		if (action != 100) {
			try {
				Controller.commitTransaction();
			} catch (Exception e2) {
				e2.printStackTrace();
				Controller.rollBack();
			}
		} else {
			this.menuPrincipal();
		}
	}
	public void chargerPartie() {
		this.resetVues();
		this.setVuePartie();
		this._vuePartie.chargerPartie();
		
	}
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////
	// POUR LA BD
	//////////////////////////////////////////////////////
	/**
	 * Connexion à la base de donnée
	 */
	public static void connexion() {
		try {
			//CONNEXION
			Torque.init(TORQUE_PROPS);
			String url = "jdbc:postgresql://postgres-info/base5a00";
			conn = DriverManager.getConnection(url, "user5a00", "p00");
		} catch (SQLException e) {
			System.err.println("SQLException : " + e.getMessage());
			System.err.println("SQLState : " + e.getSQLState());
		}  catch (TorqueException e) {
			e.printStackTrace();
		}
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
	
	public void resetVues() {
		this._vueMenuStarwars = null;
//		this._vuePlateau = null;
		this._vuePartie = null;
	}
	
}
