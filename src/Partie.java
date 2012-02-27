import java.util.HashSet;


public class Partie {
	
	private Plateau plat;
	private HashSet<Vaisseau> vaisseaux;
	
	public Partie() {
		plat = new Plateau(10);
		vaisseaux = new HashSet<Vaisseau>();
		plat.afficher();
	}
	public void nouvelle(int numJoueur) {
		
		System.out.println("************************************");
		System.out.println("*  Star Wars 1.0 | Nouvelle partie");
		System.out.println("*");
		System.out.println("* 1. Utiliser un vaisseau ");
		System.out.println("* 2. Créer un vaisseau");
		System.out.println("* 0. Menu Principal");
		System.out.println("************************************");

		int choix;
		
		do{
			choix = IO.lireEntier();
			switch (choix) {
				case 1:
					//chargerVaisseau();
					break;
				case 2:
					Vaisseau v = new Vaisseau(numJoueur);
					vaisseaux.add(v);
					choix = 0;
					break;
				case 0:
					break;
				default:
					System.out.println("Vous n'avez pas saisi une valeur correcte, veuillez recommencer!");
					break;
			}
		} while (choix !=0);
	}
}
