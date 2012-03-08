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

import torque.generated.Parties;
import torque.generated.PartiesPeer;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;
import torque.generated.VaisseauxPeer;


public class Controller {
	protected static Connection conn, connTransaction;
	private final static String TORQUE_PROPS = new String("torque-3.3//Torque.properties");
	
	private final int nb_joueurs = 2;
	
	// Vaisseau et partie en cours
	protected List<Vaisseaux> vaisseaux;
	protected List<PartiesVaisseaux> partieV;
	protected Parties partie;

	// Les vues
	private VueMenuStarWars	_vueMenuStarwars;
	private VuePartie		_vuePartie;
	private VueJoueur		_vueJoueur;
	
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
					this.nouvellePartie();
					// TODO on va jouer
					break;
				case 2: // On veut charger une partie et ses vaisseaux
					this.chargerPartie();
					// TODO on va jouer
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
		partie.setTour(r.nextInt(nb_joueurs)+1);
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
					
					v.save(connTransaction);
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
		//conn = DriverManager.getConnection(url, "user5a00", "p00");
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
	
	public void resetVues() {
		this._vueMenuStarwars	= null;
		this._vuePartie			= null;
		this._vueJoueur			= null;
	}
	
}
