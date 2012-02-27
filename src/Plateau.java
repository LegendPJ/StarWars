import java.util.HashSet;

import torque.generated.BaseObjets;
import torque.generated.BaseVaisseaux;

public class Plateau {
	
	private	static int dimension;
	private HashSet<BaseVaisseaux> vaisseaux;
	private HashSet<BaseObjets> objets;
	
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
						System.out.print("  "+c+"  ");
					else
						System.out.print("   "+c+"  ");
				} 
				System.out.println();
				for (int i=0; i < dimension; i++) {
					System.out.print("   _____");
				}
				System.out.println();
			}
			for (int i=0; i < dimension; i++) {
				System.out.print("  |     ");
			}
			System.out.println("|");
				System.out.print(String.valueOf(j));
			for (int i=0; i < dimension; i++) {
				System.out.print(" |     ");
			}
			System.out.println("|");
			for (int i=0; i < dimension; i++) {
				System.out.print("  |_____");
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
