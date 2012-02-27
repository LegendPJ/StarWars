public class Vaisseau {

	static String tableauV[] = {"(-)", "[-]", "{-}" , "/-\\", "|-|"};
	
	public Vaisseau (int numJoueur) {
		int c1;
				
		System.out.println("Joueur "+numJoueur+", veuillez choisir votre vaisseau : ");
		for (int i=0; i < tableauV.length; i++) {
			int nb = i+1;
			System.out.println(nb+". "+tableauV[i]);
		}
		do {
			c1 = IO.lireEntier();
		} while (c1 > 5 || c1 < 1);
		System.out.println("Joueur "+numJoueur+", votre vaisseau est : "+tableauV[c1-1]);
	}
}

