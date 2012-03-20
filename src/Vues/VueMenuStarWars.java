package Vues;

import Services.IO;
import Services.Messages;
import Controllers.Controller;

public class VueMenuStarWars extends Vue {

	public VueMenuStarWars (Controller c) {
		super(c);
	}
	/**
	 * Menu principal
	 * @return choix
	 */
	public int menu () {
		int choix = Vue.QUITTER;
		do {
			System.out.println("\n     **************************************");
			System.out.println("     *          Star Wars 1.0             *");
			System.out.println("     *                                    *");
			System.out.println("     * 1. Nouvelle partie                 *");
			System.out.println("     * 2. Charger une partie              *");
			System.out.println("     * 3. Nouvel Objet                    *");
			System.out.println("     * 4. Lister les Objets               *");
			System.out.println("     * 5. Supprimer un Objet              *");
			System.out.println("     * 0. Quitter                         *");
			System.out.println("     **************************************");
			
			if (!Messages.isEmpty())
				Messages.println();
			
			System.out.print("Que voulez-vous faire ? [0..5] ");
	
			choix = IO.lireEntier();
		} while (choix < Vue.QUITTER || choix > 5);
		
		return choix;
	}

}
