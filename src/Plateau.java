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
	
	public void affficher() {
		
		//Construction du plateau
		for (int j=0; j < dimension; j++) {
			if (j==0) {
				for (int i=0; i < dimension; i++) {
					System.out.print(" _____");
				}
				System.out.println();
			}
			for (int i=0; i < dimension; i++) {
				System.out.print("|     ");
			}
			System.out.println("|");
			for (int i=0; i < dimension; i++) {
				System.out.print("|     ");
			}
			System.out.println("|");
			for (int i=0; i < dimension; i++) {
				System.out.print("|_____");
			}
			System.out.println("|");
		}
	}
}


// Vaisseaux : /-\ |-| (-) [-]
