package Vues;

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
	
	public void chargerPartie() {
		
	}

}
