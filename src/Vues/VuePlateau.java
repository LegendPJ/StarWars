package Vues;
import java.util.ArrayList;
import java.util.List;

import Services.IO;

import Controllers.Controller;

import torque.generated.ObjetsParties;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;


public class VuePlateau extends Vue {

	public static int dimension;
	private char alphabet[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	
	public VuePlateau(Controller c, int d) {
		super(c);
		dimension = d;
	}
	
	public void afficher(List<PartiesVaisseaux> pv, List<Vaisseaux> v, List<ObjetsParties> op) {
		//Construction du plateau
		for (int j=0; j < dimension; j++) {
			// BORDURE DU HAUT
			if (j==0) {
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
			//END BORDURE DU HAUT
			
			//LIGNE PAR LIGNE		
			//PREMIERE LIGNE - JOUEUR 1 
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
			
			//DEUXIEME LIGNE - JOUEUR 2
			System.out.print(alphabet[j]);
			for (int i=0; i < dimension; i++) {
				System.out.print(" |");
				if (pv.get(1).getCoordX() == i && pv.get(1).getCoordY() == j)
					System.out.print(v.get(1).getType()+" "+pv.get(1).getNumJoueur());
				else 
					System.out.print("     ");
			}
			System.out.println(" |");
			
			//TROISIEME LIGNE - OBJETS
			for (int i=0; i < dimension; i++) {
				if (i == 0) 
					System.out.print("  ");
				StringBuffer s = new StringBuffer("|______");
				// boucle pour ajouter les objets
				for (ObjetsParties o : op) {
					if (o.getCoordX() == i && o.getCoordY() == j)
						s.setCharAt(3, 'o');
				}
				System.out.print(s.toString());
			}
			System.out.println("|");
		} //FIN CREATION
	}//FIN FONCTION
	
	public int menu(int numJoueur) {
		int menu = 0;
		boolean cond = true; 
		System.out.println("\n     ************************************");
		System.out.println("     *  Star Wars 1.0 | Jeu             *");
		System.out.println("     *                                  *");
		if (getController().getJoueur(numJoueur).getPa() > 0) {
			System.out.println("     * 1. Déplacer le vaisseau          *");
			System.out.println("     * 2. Attaquer                      *");
			System.out.println("     * 3. Ramasser Objet                *");
			System.out.println("     * 4. Utiliser un Objet             *");
			System.out.println("     * 5. Equiper un Objet              *");
			System.out.println("     * 6. Lister les Objets             *");
		}
		System.out.println("     * 7. Fin du tour                   *");
		System.out.println("     * 0. Menu Principal                *");
		System.out.println("     ************************************");
		
		System.out.println("\nPoints d'attaque : " + getController().getJoueur(numJoueur).getAttaque());
		System.out.println("Points de dégats : " + getController().getJoueur(numJoueur).getDegats());
		System.out.println("Points de champ de force : " + getController().getJoueur(numJoueur).getChamp());
		System.out.println("Points d'énergie : " + getController().getJoueur(numJoueur).getEnergie());
		System.out.println("Points d'action restants : " + getController().getJoueur(numJoueur).getPa());
		
		do {
			System.out.print("\nJoueur "+(numJoueur+1)+" que voulez-vous faire ? ");
			menu = IO.lireEntier();
			if (getController().getJoueur(numJoueur).getPa() > 0)
				cond = (menu > 7 || menu < 0);
			else
				cond = (menu != 0 && menu != 7); 
		} while (cond);
		return menu;
	}
	
	public int deplacement(int numJoueurActif) {
		List<Integer> p = new ArrayList<Integer>();
		int d;
		
		do {
			System.out.println("Déplacement : ");
			if (getController().getJoueur(numJoueurActif).getCoordY() != 0) {
				System.out.println("              8. Haut");
				p.add(8);
			}
			if (getController().getJoueur(numJoueurActif).getCoordY() != dimension-1) {
				System.out.println("              2. Bas");
				p.add(2);
			}
			if (getController().getJoueur(numJoueurActif).getCoordX() != 0) {
				System.out.println("              4. Gauche");
				p.add(4);
			}
			if (getController().getJoueur(numJoueurActif).getCoordX() != dimension-1) {
				System.out.println("              6. Droite");
				p.add(6);
			}
			d = IO.lireEntier();
		} while (!p.contains(d));
		
		return d;
	}

	
}
