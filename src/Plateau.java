import java.util.ArrayList;
import java.util.List;

import torque.generated.BaseVaisseaux;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;

public class Plateau {
	
	private	static int dimension;
	private List<PartiesVaisseaux> Pvaisseaux;
	private List<Vaisseaux> vaisseaux;
	//private List<BaseObjets> objets;
	private char alphabet[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	public Plateau(int dim) {
		dimension = dim;
		Pvaisseaux = new ArrayList<PartiesVaisseaux>();
		//objets = new HashSet<BaseObjets>();
	}
	
	public void setVariables(List<PartiesVaisseaux> pv, List<Vaisseaux> v) {
		this.Pvaisseaux = pv;
		this.vaisseaux = v;
	}
	
	
	public void afficher(List<PartiesVaisseaux> pv, List<Vaisseaux> v) {
		this.setVariables(pv,v);
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
		
		this.menuActions();
	}//FIN FONCTION

	private void menuActions() {
		for (int i=0; i< PartieController.nb_joueurs;i++) {
			int choix = 110;
			do{
				System.out.println("\n     ************************************");
				System.out.println("     *  Star Wars 1.0 | Jeu             *");
				System.out.println("     *                                  *");
				System.out.println("     * 1. Déplacer le vaisseau          *");
				System.out.println("     * 2. Attaquer                      *");
				System.out.println("     * 3. Ramasser Objet                *");
				System.out.println("     * 4. Utiliser un Objet             *");
				System.out.println("     * 5. Equiper un Objet              *");
				System.out.println("     * 0. Menu Principal                *");
				System.out.println("     ************************************");
				System.out.println("\nJoueur "+(i+1)+" que voulez-vous faire ?\n");
				choix = IO.lireEntier();
				switch (choix) {
					case 1:
						break;
					case 2:
						break;
					case 0:
						break;
					default:
						System.out.println("Vous n'avez pas saisi une valeur correcte, veuillez recommencer!");
						break;
				}
			} while (Pvaisseaux.get(i).getPa() > 0);
		}
	}
}

/*
 * ______ ______ ______ ______ ______ ______ ______ ______ ______ ______
  |      |      |      |      |(-) 2 |      |      |      |      |      |
A |      |      |      | /-\ 1|/-\ 1 |  O   |      |      |      |      |
  |______|______|______|______|___O__|______|______|______|______|______|
  |      |      |      |      |      |      |      |      |      |      |
 * 
 * 
 * 
 * 
 * 
 * 
 *    1     2     3     4     5     6     7     8     9    10
 *  _____ _____ _____ _____ _____ _____ _____ _____ _____ _____
 * |     |     |     |     |     |     |     |     |     |     | 
 */
// Vaisseaux : /-\ |-| (-) [-]




