package Controllers;

import java.util.List;

import torque.generated.Caracteristiques;
import torque.generated.Objets;
import torque.generated.ObjetsPeer;
import torque.generated.Types;
import Services.IO;
import Vues.Vue;

public class VueObjet extends Vue {
	
	public VueObjet(Controller c) {
		super(c);
	}
	
	public Objets creer (List<Caracteristiques> l, List<Types> t) {
		int pts = 0, 
			i = 1, 
			k = 1,
			c = 0, 
			nbTr = 0, 
			c2 =0;
		String nom;
		
		//enregistrer le nom de l'objet
		do {
		System.out.print("Nom de l'objet : ");
			nom = IO.lireChaine();
		} while (ObjetsPeer.nomPris(nom));
		
		//enregistrer la caracteristique de l'objet
		do {
			System.out.println("Caractéristique à modifier :");
			for (Caracteristiques ca : l) {
				System.out.println(i + ". " + ca.getNom());
				i++;
			}
			i =1;
				c = IO.lireEntier();
		} while (c < 1 || c > l.size());
		
		
		//enregistrer le type de l'objet
		do {
			System.out.println("Type d'objet :");
			for (Types ty : t) {
				System.out.println(k + ". " + ty.getNom());
				k++;
			}
			k =1;
				c2 = IO.lireEntier();
		} while (c2 < 1 || c2 > t.size());
		
		
		//enregistrer le nombre de points augmenté par l'objet
		do {
			System.out.print("Nombre de points (negatif pour un malus) : ");
			pts = IO.lireEntier();
		} while (pts == 0);
		
		
		//enregistrer le nombre de tours de validité de l'objet
		do {
			System.out.print("Nombre de tours : ");
			nbTr = IO.lireEntier();
		} while (nbTr <= 0);
		
		return new Objets(nom, pts, l.get(c-1).getNom(), nbTr, t.get(c2-1).getNom());
	}

}
