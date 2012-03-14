package Vues;

import Services.IO;
import Controllers.Controller;

public class VueMenuStarWars extends Vue {

	public VueMenuStarWars (Controller c) {
		super(c);
	}
	
	public int menu () {
		int choix = Vue.QUITTER;
		do {
			System.out.println("\n     **************************************");
			System.out.println("     *          Star Wars 1.0             *");
			System.out.println("     *                                    *");
			System.out.println("     * 1. Nouvelle partie                 *");
			System.out.println("     * 2. Charger une partie              *");
			System.out.println("     * 3. Nouvel Objet                    *");
			System.out.println("     * 0. Quitter                         *");
			System.out.println("     **************************************");
			
			System.out.print("Que voulez-vous faire ? ");
	
			choix = IO.lireEntier();
		} while (choix < Vue.QUITTER || choix > 3);
		
		return choix;
	}

}
