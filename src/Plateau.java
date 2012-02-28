import java.util.HashSet;

import torque.generated.BaseObjets;
import torque.generated.BaseVaisseaux;

public class Plateau {
	
	private	static int dimension;
	private HashSet<BaseVaisseaux> vaisseaux;
	private HashSet<BaseObjets> objets;
	private char alphabet[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	public Plateau(int dim) {
		dimension = dim;
		vaisseaux = new HashSet<BaseVaisseaux>();
		objets = new HashSet<BaseObjets>();
	}
	
	public void afficher() {
		
		//Construction du plateau
		for (int j=0; j < dimension; j++) {
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
			for (int i=0; i < dimension; i++) {
				if (i == 0) 
					System.out.print(" ");
				System.out.print(" |     ");
			}
			System.out.println(" |");
				System.out.print(alphabet[j]);
			for (int i=0; i < dimension; i++) {
				System.out.print(" |     ");
			}
			System.out.println(" |");
			for (int i=0; i < dimension; i++) {
				if (i == 0) 
					System.out.print("  ");
				System.out.print("|______");
			}
			System.out.println("|");
		}
	}
}

/*
 *    1     2     3     4     5     6     7     8     9    10
 *  _____ _____ _____ _____ _____ _____ _____ _____ _____ _____
 * |     |     |     |     |     |     |     |     |     |     | 
 */
// Vaisseaux : /-\ |-| (-) [-]
