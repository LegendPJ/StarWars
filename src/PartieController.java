import java.util.List;
import java.util.Random;

import org.apache.torque.TorqueException;
import torque.generated.*;

public class PartieController extends Controller {
	
	private static int QUITTER = 10;
	
	private Plateau plat;
	private VaisseauController vaisseaux;
	public static int nb_joueurs = 2;
	private Parties partie;
	
	public PartieController() {
		plat = new Plateau(10);
		vaisseaux = new VaisseauController();
		partie = new Parties();
	}
	
	/**
	 * Créer une nouvelle partie vide et l'enregistre
	 */
	public void nouvelle() {
		int choixMenu = 0;
		String nom;
		this.beginTransaction();
		
		partie = new Parties();
		do {
			System.out.println("Nom de la partie (40 caracteres maximum) : ");
			nom = IO.lireChaine();
		} while (nom.length() > 40 || PartiesPeer.nomPris(nom));
		
		try {
			Random r = new Random();
			partie.setNom(nom);
			partie.setTour(r.nextInt(2)+1);
			partie.save(connTransaction);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		for (int i=0; i < nb_joueurs; i++) {
			choixMenu = this.menuPartie(i+1);
			if (choixMenu == 10)
				i = nb_joueurs;
		}
		
		if (choixMenu != PartieController.QUITTER)
		{
			this.commitTransaction();
			vaisseaux.clearLists();
		
			this.jouer();
		}
	}
	
	public void charger() {
		try {
			List<Parties> list = PartiesPeer.doSelectAll();
			
			int c = 1, menu = 0;
			int length = list.size();
			for (Parties p : list){
				System.out.println(c + ". " + p.getNom());
				c++;
			}
			do {
				System.out.println("Choisissez votre partie [1.."+length+"] : ");
				menu = IO.lireEntier();
			} while (menu < 1 || menu > length);
			
			partie = PartiesPeer.charger(list.get(menu-1).getNom());
			vaisseaux.chargerVaisseauxPartie(partie);
			this.jouer();
			
		} catch (TorqueException e) {
			System.out.println("Aucune partie enregistrée");
		}
		
	}
	
	/**
	 * Affichage du menu
	 * @param numJoueur numero du joueur pour le quel est affiché le menu
	 * @return 10 si on quitte, 0 si on passe au joueur suivant
	 */
	
	public int menuPartie(int numJoueur) {
		
		int choix;
		int fin = 0;
		
		do{
			System.out.println("\n************************************");
			System.out.println("*  Star Wars 1.0 | Nouvelle partie");
			System.out.println("*");
			System.out.println("* 1. Créer un vaisseau");
			System.out.println("* 2. Utiliser un vaisseau ");
			System.out.println("* 0. Menu Principal");
			System.out.println("************************************");
			System.out.println("\nJoueur "+numJoueur+" que voulez-vous faire ?\n");
			choix = IO.lireEntier();
			switch (choix) {
				case 1:
					vaisseaux.creer(numJoueur, this.partie);
					choix = 0;
					break;
				case 2:
					choix = vaisseaux.chargerVaisseau(numJoueur, partie);
					break;
				case 0:
					this.rollBack();
					fin = PartieController.QUITTER;
					break;
				default:
					System.out.println("Vous n'avez pas saisi une valeur correcte, veuillez recommencer!");
					break;
			}
		} while (choix !=0);
		return fin;
	}
	
	@SuppressWarnings("unchecked")
	public void jouer() {
		//New Game
		//Clear Ecran
		try {
			plat.afficher(partie.getPartiesVaisseauxs(), vaisseaux.getVaisseaux());
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}
}
