package Controllers;
import java.util.List;
import java.util.Random;

import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;



public class JeuController extends Controller{
	
	public JeuController () {
		Controller.connexion();
		nb_joueurs = 2;
		this.nouveau();
		Controller.finConnexion();
	}
	
	public void nouveau() {
		int choix;
		System.out.println("Bienvenue dans le jeu StarWars 1.0 GUIless");
		do{
			choix = this.menu();
			switch (choix) {
				case 1:
					part.nouvelle();
					break;
				case 2:
					part.charger();
					break;
				case 3:
					System.out.println("Ajouter un objet");
					break;
				case 0:
					break;
				default:
					System.out.println("Vous n'avez pas saisi une valeur correcte, veuillez recommencer!");
					break;
			}
		} while (choix !=0);
	}
	
	public void setVariables(List<PartiesVaisseaux> pv, List<Vaisseaux> v) {
		this.Pvaisseaux = pv;
		this.vaisseaux = v;
	}
	
	public void tour() {
		
		Random r = new Random();
		int joueurFirst = r.nextInt(2)+1;;
		int joueurCourant = joueurFirst;
		for (int i=1; i<=nb_joueurs; i++) {
			plat.afficher(Pvaisseaux, vaisseaux);
			menuActions(joueurCourant);
			joueurCourant = this.joueurTour(joueurCourant);
			if (i == nb_joueurs) {
				i=0;
				joueurFirst = joueurCourant = r.nextInt(2);
			}
			//QUITTATION : i = nb_joueurs;
		}
		
	}
	
	private void menuActions(int numJoueur) {

		
		for (int i=0; i< PartieController.nb_joueurs;i++) {
			PartiesVaisseaux pv = Pvaisseaux.get(i);
			int choix = 110, fin = 0;
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
				
				System.out.println("\nJoueur "+(numJoueur)+" que voulez-vous faire ?\n");
				choix = IO.lireEntier();
				switch (choix) {
					case 1:
						//bouger et réafficher la grille
						System.out.println("Déplacement : ");
						if (pv.getCoordY() != 0)
							System.out.println("              8. Haut");
						if (pv.getCoordY() != PlateauController.dimension-1)
							System.out.println("              2. Bas");
						if (pv.getCoordX() != 0)
							System.out.println("              4. Gauche");
						if (pv.getCoordX() != PlateauController.dimension-1)
							System.out.println("              6. Droite");
						int direction = IO.lireEntier();
						
						switch (direction) {
							case 8:
								pv.setCoordY(pv.getCoordY()-1);
								break;
							case 2:
								System.out.println("Go backward");
								break;
							case 4:
								System.out.println("Go to the left");
								break;
							case 6:
								System.out.println("Go to the right");
								break;
							default:
								break;
						}
						plat.afficher(Pvaisseaux, vaisseaux);
						break;
					case 2:
						break;
					case 0:
						fin = 10;
						break;
					default:
						System.out.println("Vous n'avez pas saisi une valeur correcte, veuillez recommencer!");
						break;
				}
			} while ((Pvaisseaux.get(i).getPa() > 0) || (fin == 10));
		}
	}
}
