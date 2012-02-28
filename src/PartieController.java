import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.util.Transaction;

import torque.generated.*;

public class PartieController extends Controller {
	
	private Plateau plat;
	private VaisseauController vaisseaux;
	int nb_joueurs = 2;
	private Parties parties;
	
	public PartieController() {
		plat = new Plateau(10);
		vaisseaux = new VaisseauController();
		//plat.afficher();
		parties = new Parties();
	}
	
	/**
	 * Créer une nouvelle partie vide et l'enregistre
	 */
	public void nouvelle() {
		try {
			Transaction.begin();
		} catch (TorqueException e2) {
			e2.printStackTrace();
		}
		System.out.println("Nom de la partie : ");
		parties.setNom(IO.lireChaine());
		parties.setTour("");
		try {
			parties.setId(IO.lireEntier());
		} catch (TorqueException e1) {
			e1.printStackTrace();
		}
		
		for (int i=0; i < nb_joueurs; i++) {
			System.out.println("");
			System.out.println("Joueur "+(i+1)+" que voulez-vous faire ?");
			System.out.println("");
			if (this.menuPartie(i+1) == 10)
				i = nb_joueurs;
		}
		try {
			// TODO Enregistrer les Vaisseaux et les PartiesVaisseaux dans une transaction pour eviter les problemes
			parties.save();
			Transaction.commit(conn);
		} catch (Exception e) {
			try {
				Transaction.rollback(conn);
			} catch (TorqueException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Affichage du menu
	 * @param numJoueur numero du joueur pour le quel est affiché le menu
	 * @return 10 si on quitte, 0 si on passe au joueur suivant
	 */
	public int menuPartie(int numJoueur) {
		
		System.out.println("************************************");
		System.out.println("*  Star Wars 1.0 | Nouvelle partie");
		System.out.println("*");
		System.out.println("* 1. Utiliser un vaisseau ");
		System.out.println("* 2. Créer un vaisseau");
		System.out.println("* 0. Menu Principal");
		System.out.println("************************************");

		int choix;
		int fin = 0;
		
		do{
			choix = IO.lireEntier();
			switch (choix) {
				case 1:
					//chargerVaisseau();
					break;
				case 2:
					vaisseaux.creer();
					choix = 0;
					break;
				case 0:
					fin = 10;
					break;
				default:
					System.out.println("Vous n'avez pas saisi une valeur correcte, veuillez recommencer!");
					break;
			}
		} while (choix !=0);
		return fin;
	}
}
