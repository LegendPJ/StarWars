public class Jeu {

	Partie part;
	
	public static void main (String[] args) { new Jeu(); }
	
	public Jeu () {
		int choix;
		System.out.println("Bienvenue dans le jeu StarWars 1.0 GUIless");
		do{
			choix = this.menu();
			switch (choix) {
				case 1:
					System.out.println("Nouvelle partie");
					part = new Partie();
					part.nouvelle();
					break;
				case 2:
					System.out.println("Charger une partie");
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

	public int menu() {
		System.out.println("************************************");
		System.out.println("*	Star Wars 1.0");
		System.out.println("*");
		System.out.println("* 1. Nouvelle partie ");
		System.out.println("* 2. Charger une partie");
		System.out.println("* 3. Nouvel Objet");
		System.out.println("* 0. Quitter");
		System.out.println("************************************");

		return IO.lireEntier();
		
	}
}
