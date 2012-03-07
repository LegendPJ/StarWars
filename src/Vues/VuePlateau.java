package Vues;
import java.util.List;

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

	
}
