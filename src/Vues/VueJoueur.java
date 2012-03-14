package Vues;

import java.util.ArrayList;
import java.util.List;

import Services.IO;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;
import torque.generated.VaisseauxPeer;
import Controllers.Controller;

public class VueJoueur extends Vue {
	private String VAISSEAUX[] = {"(-)", "[-]", "{-}" , "/-\\", "|-|"};
	private List<String> noms; //liste des noms déjà utilisés dans "creer"
	
	public VueJoueur(Controller c) {
		super(c);
		noms = new ArrayList<String>();
	}
	
	public int menuJoueur (int numJoueur, int nbVaisseaux) {
		int choix = Vue.QUITTER, choixMax = 1;
		do{
			System.out.println("\n     **************************************");
			System.out.println("     *  Star Wars 1.0 | Nouvelle partie   *");
			System.out.println("     *                                    *");
			System.out.println("     * 1. Créer un vaisseau               *");
			if (nbVaisseaux != 0) {
				System.out.println("     * 2. Utiliser un vaisseau            *");
				choixMax = 2;
			}
			System.out.println("     * 0. Menu Principal                  *");
			System.out.println("     **************************************");
			System.out.print("\nJoueur "+numJoueur+" que voulez-vous faire ? [0.."+choixMax+"] ");

			choix = IO.lireEntier();
		} while (choix < Vue.QUITTER || choix > choixMax);
		
		return choix;
	}
	
	public Vaisseaux nouveauVaisseau (int numJoueur) {
		String nomVaisseau = new String();
		int c1;
		
		do {
			System.out.print("Joueur "+numJoueur+", veuillez nommer votre vaisseau : ");
			nomVaisseau = IO.lireChaine();
		} while (nomVaisseau.length() > 40 || VaisseauxPeer.nomPris(nomVaisseau, noms));
		noms.add(nomVaisseau);
		
		// Type du vaisseau
		System.out.println("Joueur "+numJoueur+", choisissez votre vaisseau : ");
		for (int i=0; i < VAISSEAUX.length; i++) {
			int nb = i+1;
			System.out.println(nb+". "+VAISSEAUX[i]);
		}
		do {
			c1 = IO.lireEntier();
		} while (c1 > 5 || c1 < 1);
		
		System.out.println("Joueur " + numJoueur + ", \"" + nomVaisseau + "\" a été créé " + VAISSEAUX[c1-1] + "\n");
		
		return new Vaisseaux (nomVaisseau, VAISSEAUX[c1-1]);
	}
	
	public PartiesVaisseaux setCaracs (int numJoueur) {
		int atq = 0, dgt = 0, cdf = 0, nrj = 0, coordX = 0, coordY = 0;
		int sommeC = 40;
		System.out.println("Joueur "+numJoueur+", vous devez caractériser votre vaisseau.");
		System.out.println("Vous avez 40 points à répartir sur différentes caractéristiques.");
		System.out.println("Vous devez attribuer au moins un point sur chaque caractéristique.\n");
		while (atq < 1 || atq > 37) {
			System.out.print("Nombre de points pour l'attaque [1..37]: "); 
			atq = IO.lireEntier();
		}
		sommeC-=atq;
		
		while (dgt < 1 || dgt > sommeC-2) {
			System.out.print("Nombre de points pour les degats [1.."+(sommeC-2)+"] : "); 
			dgt = IO.lireEntier();
		}
		sommeC-=dgt;
		
		while (cdf < 1 || cdf > sommeC-1) {
			System.out.print("Nombre de points pour le champ de force [1.."+(sommeC-1)+"] : "); 
			cdf = IO.lireEntier();
		}
		sommeC-=cdf;
		nrj = sommeC;
		System.out.println("Votre vaisseau aura donc "+(nrj*10)+" points d'energie.");
		
		return new PartiesVaisseaux(atq, cdf, dgt, nrj, coordX, coordY);
	}
	
	public Vaisseaux chargerVaisseau (int numJoueur, List<Vaisseaux> vaisseaux) {
		int numVaisseau = 1, menu = Vue.QUITTER;
		int length = vaisseaux.size();
		
		for (Vaisseaux v : vaisseaux){
			System.out.println(numVaisseau + ". " + v.getNom());
			numVaisseau++;
		}
		System.out.println("0. Quitter");
		
		do {
			System.out.print("Choisissez votre vaisseau [0.."+length+"] : ");
			menu = IO.lireEntier();
		} while (menu < Vue.QUITTER || menu > length);
		if (menu == Vue.QUITTER)
			return null;
		else
			return new Vaisseaux(vaisseaux.get(menu-1).getNom(), vaisseaux.get(menu-1).getType());
	}

	public int attaquer(List<PartiesVaisseaux> vais, int coordX, int coordY, String nom) {
		int i = 1, menu = 0;
		for (PartiesVaisseaux v : vais) {
			if (getController().memeCase(coordX, coordY, nom))
				System.out.print(i + ". " + v.getNomVaisseau());
			i++;
		}
		do {
			System.out.print("Quel vaisseau attaquer ? [1.." + i + "] ");
			menu = IO.lireEntier();
		} while (menu < 1 || menu > i);
		
		return menu;
	}
	
}