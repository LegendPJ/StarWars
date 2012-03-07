package Controllers;
import java.util.List;
import java.util.Random;

import org.apache.torque.TorqueException;
import torque.generated.*;

public class PartieController extends Controller {
	
	
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
	
	
	public void jouer() {
		//New Game
		//Clear Ecran
		JeuController j = new JeuController();
		j.tour();
	}
}
