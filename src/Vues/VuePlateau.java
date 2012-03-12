package Vues;
import java.util.List;

import Services.IO;

import Controllers.Controller;

import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;


public class VuePlateau extends Vue {

	public static int dimension;
	private char alphabet[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	
	public VuePlateau(Controller c, int d) {
		super(c);
		dimension = d;
	}
	
	public void afficher(List<PartiesVaisseaux> pv, List<Vaisseaux> v) {
		//Construction du plateau
		for (int j=0; j < dimension; j++) {
			
			
			//TOUJOURS LE FAIRE
			if (j==0) { // border-top
				for (int c=1; c <= dimension; c++) {
					if (j == 0)
						System.out.print("  ");
					if (c >= 10)
						System.out.print("  "+c+" ");
					else
						System.out.print("   "+c+" ");
				} 
				System.out.println();
				for (int i=0; i < dimension; i++) {
					if (i == 0) 
						System.out.print("  ");
					System.out.print(" ______");
				}
				System.out.println();
			}
			//END TOUJOURS LE FAIRE
			
			//LIGNE PAR LIGNE		
			//PREMIERS BATONS - JOUEUR 1
			for (int i=0; i < dimension; i++) {
				if (i == 0) 
					System.out.print(" ");
				System.out.print(" |");
				if (pv.get(0).getCoordX() == i && pv.get(0).getCoordY() == j)
					System.out.print(v.get(0).getType()+" "+pv.get(0).getNumJoueur());
				else 
					System.out.print("     ");
			}
			System.out.println(" |");
			
			//DEUXIEMES BATONS - JOUEUR 2
			System.out.print(alphabet[j]);
			for (int i=0; i < dimension; i++) {
				System.out.print(" |");
				if (pv.get(1).getCoordX() == i && pv.get(1).getCoordY() == j)
					System.out.print(v.get(1).getType()+" "+pv.get(1).getNumJoueur());
				else 
					System.out.print("     ");
			}
			System.out.println(" |");
			
			//TROISIEMES BATONS - OBJETS
			for (int i=0; i < dimension; i++) {
				if (i == 0) 
					System.out.print("  ");
				System.out.print("|______");
			}
			System.out.println("|");
		} //FIN CREATION
	}//FIN FONCTION
	
	public int menu(int numJoueur) {
		int menu = 0;
		boolean cond = (menu != 0 || menu != 6); 
		System.out.println("\n     ************************************");
		System.out.println("     *  Star Wars 1.0 | Jeu             *");
		System.out.println("     *                                  *");
		if (getController().getJoueur(numJoueur).getPa() > 0) {
			cond = (menu > 6 || menu < 0);
			System.out.println("     * 1. Déplacer le vaisseau          *");
			System.out.println("     * 2. Attaquer                      *");
			System.out.println("     * 3. Ramasser Objet                *");
			System.out.println("     * 4. Utiliser un Objet             *");
			System.out.println("     * 5. Equiper un Objet              *");
		}
		System.out.println("     * 6. Fin du tour                   *");
		System.out.println("     * 0. Menu Principal                *");
		System.out.println("     ************************************");
		
		System.out.println("\n Points d'action restants : " + getController().getJoueur(numJoueur).getPa());
		
		System.out.println("\nJoueur "+(numJoueur+1)+" que voulez-vous faire ?\n");
		do {
			menu = IO.lireEntier();
		} while (cond);
		return menu;
	}
	
	public int deplacement(int numJoueurActif) {
		System.out.println("Déplacement : ");
		if (getController().getJoueur(numJoueurActif).getCoordY() != 0)
			System.out.println("              8. Haut");
		if (getController().getJoueur(numJoueurActif).getCoordY() != dimension-1)
			System.out.println("              2. Bas");
		if (getController().getJoueur(numJoueurActif).getCoordX() != 0)
			System.out.println("              4. Gauche");
		if (getController().getJoueur(numJoueurActif).getCoordX() != dimension-1)
			System.out.println("              6. Droite");
		return IO.lireEntier();
	}

	
}
