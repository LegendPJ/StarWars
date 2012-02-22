public class Partie {
	
	private Plateau plat;
	
	public Partie() {
		plat = new Plateau(10);
	}
	public void nouvelle() {
		
		int c1, c2;
		
		String tableauV[] = {"(-)", "[-]", "{-}" , "/-\\", "|-|"};
		
				System.out.println("Joueur 1, veuillez choisir votre vaisseau : ");
				for (int i=0; i < tableauV.length; i++) {
					int nb = i+1;
					System.out.println(nb+". "+tableauV[i]);
				}
			do {
					c1 = IO.lireEntier();
				
			} while (c1 > 5 || c1 < 1);
				System.out.println("Joueur 1, votre vaisseau est : "+tableauV[c1-1]);
				
				
			
				System.out.println("Joueur 2, veuillez choisir votre vaisseau à votre tour : ");

				for (int i=0; i < tableauV.length; i++) {
					int a = 0;
					int nb;
						if (tableauV[a] != tableauV[c1-1]) {
							nb = a+1;
							System.out.println(nb+". "+tableauV[a]);
						}
					a++;
				}
			do {
					c2 = IO.lireEntier();
				
			} while (c2 > 5 || c2 < 1);
				System.out.println("Joueur 2, votre vaisseau est : "+tableauV[c2-1]);
	}
}
