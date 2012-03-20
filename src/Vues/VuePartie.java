package Vues;

import java.util.List;

import Services.IO;
import torque.generated.Parties;
import torque.generated.PartiesPeer;
import Controllers.Controller;

public class VuePartie extends Vue {

	public VuePartie(Controller c) {
		super(c);
	}
	/**
	 * Créer une nouvelle partie
	 * @return la partie créée
	 */
	public Parties nouvellePartie() {
		String nom = new String();
		int dimension = 0;
		boolean cond = false;
		do {
			System.out.print("Nom de la partie (40 caracteres maximum) : ");
			nom = IO.lireChaine();
			nom = nom.trim();
			cond = PartiesPeer.nomPris(nom);
			if (PartiesPeer.nomPris(nom))
				System.out.println("Ce nom de partie est deja pris");
			else if (nom.length() > 40)
				System.out.println("Le nom comporte plus de 40 caractères");
		} while (nom.length() > 40 || cond);
		
		do {
			System.out.print("Dimension du plateau [5..10] : ");
			dimension = IO.lireEntier();
		} while (dimension < 5 || dimension > 10);
		
		return new Parties(nom, dimension);
	}
	/**
	 * Charger une partie
	 * @param list liste des parties existantes
	 * @return la partie sélectionnée
	 */
	public Parties chargerPartie(List<Parties> list) {
		int numPartie = 1, menu = 0;
		int length = list.size();
		Parties r = null;
		
		for (Parties p : list){
			System.out.println(numPartie + ". " + p.getNom());
			numPartie++;
		}
		System.out.println("0. Menu Principal");
		
		do {
			System.out.print("Choisissez votre partie [0.."+length+"] : ");
			menu = IO.lireEntier();
		} while (menu < Vue.QUITTER || menu > length);
		
		if (menu != 0)
			r = PartiesPeer.charger(list.get(menu-1).getNom());
		
		return r;
	}

}
