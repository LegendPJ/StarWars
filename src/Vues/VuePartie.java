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
	
	public Parties nouvellePartie() {
		String nom = new String();
		
		do {
			System.out.println("Nom de la partie (40 caracteres maximum) : ");
			nom = IO.lireChaine();
		} while (nom.length() > 40 || PartiesPeer.nomPris(nom));
		
		return new Parties(nom);
	}
	
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
			System.out.println("Choisissez votre partie [0.."+length+"] : ");
			menu = IO.lireEntier();
		} while (menu < Vue.QUITTER || menu > length);
		if (menu != 0)
			r = PartiesPeer.charger(list.get(menu-1).getNom());
		
			return r;
	}

}
