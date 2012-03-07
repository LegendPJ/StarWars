package Vues;

import Services.IO;
import Controllers.Controller;

public class VueMenuStarWars extends Vue {

	public VueMenuStarWars (Controller c) {
		super(c);
	}
	
	public int menu () {
		int choix = 0;
		do {
			System.out.println("\n     **************************************");
			System.out.println("     *          Star Wars 1.0             *");
			System.out.println("     *                                    *");
			System.out.println("     * 1. Nouvelle partie                 *");
			System.out.println("     * 2. Charger une partie              *");
			System.out.println("     * 3. Nouvel Objet                    *");
			System.out.println("     * 0. Quitter                         *");
			System.out.println("     **************************************");
	
			choix = IO.lireEntier();
		} while (choix < 0 || choix > 3);
		
		return choix;
	}

}
