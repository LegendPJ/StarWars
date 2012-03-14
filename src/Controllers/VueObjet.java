package Controllers;

import java.util.List;

import torque.generated.Caracteristiques;
import torque.generated.Objets;
import torque.generated.ObjetsPeer;
import Services.IO;
import Vues.Vue;

public class VueObjet extends Vue {
	
	public VueObjet(Controller c) {
		super(c);
	}
	
	public Objets creer (List<Caracteristiques> l) {
		int pts = 0, i = 1, c = 0;
		String nom;
		do {
		System.out.print("Nom de l'objet : ");
			nom = IO.lireChaine();
		} while (ObjetsPeer.nomPris(nom));
		do {
		System.out.println("Caractéristique à modifier :");
		for (Caracteristiques ca : l) {
			System.out.println(i + ". " + ca.getNom());
			i++;
		}
			c = IO.lireEntier();
		} while (c < 1 || c > l.size());
		
		System.out.print("Nombre de points (negatif pour un malus) : ");
		pts = IO.lireEntier();
		
		return new Objets(nom, l.get(c-1).getNom(), pts);
	}

}
