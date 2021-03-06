package Vues;

import java.util.List;

import torque.generated.Caracteristiques;
import torque.generated.Objets;
import torque.generated.ObjetsPeer;
import torque.generated.Types;
import Controllers.Controller;
import Services.IO;

public class VueObjet extends Vue {
	
	public VueObjet(Controller c) {
		super(c);
	}
	/**
	 * Créer un objet
	 * @param l liste des caractéristiques améliorables (attaque, champ, degats...)
	 * @param t liste des types d'objet créables (armes, bonus...)
	 * @return l'objet créé
	 */
	public Objets creer (List<Caracteristiques> l, List<Types> t) {
		int pts = 0, 
			i = 1, 
			k = 1,
			c = 0, 
			nbTr = 0, 
			c2 =0;
		String nom;
		
		// le nom de l'objet
		do {
			System.out.print("Nom de l'objet : ");
			nom = IO.lireChaine();
			nom = nom.trim();
		} while (ObjetsPeer.nomPris(nom) || nom.length() > 40);
		
		// la caracteristique de l'objet
		do {
			System.out.println("Caractéristique à modifier :");
			for (Caracteristiques ca : l) {
				System.out.println(i + ". " + ca.getNom());
				i++;
			}
			i = 1;
			c = IO.lireEntier();
		} while (c < 1 || c > l.size());
		
		
		// le type de l'objet
		if (l.get(c-1).getNom().equals("attaque"))
			c2 = 1;
		else {
			do {
				System.out.println("Type d'objet :");
				for (Types ty : t) {
					System.out.println(k + ". " + ty.getNom());
					k++;
				}
				k =1;
					c2 = IO.lireEntier();
			} while (c2 < 1 || c2 > t.size());
		}
		
		
		// le nombre de points augmenté par l'objet
		String condition = new String();
		boolean cond = false;
		if (!t.get(c2-1).getNom().equals("arme"))
			condition = " (negatif pour un malus)";
		do {
			System.out.print("Nombre de points" +condition+" : ");
			pts = IO.lireEntier();
			if (t.get(c2-1).getNom().equals("arme"))
				cond = pts < 0;
		} while (pts == 0 || cond);
		
		
		// le nombre de tours de validité de l'objet
		if (!t.get(c2-1).equals("energie")) {
			do {
				System.out.print("Nombre de tours : ");
				nbTr = IO.lireEntier();
			} while (nbTr <= 0);
		} else
			nbTr = 1;
		
		return new Objets(nom, pts, l.get(c-1).getNom(), nbTr, t.get(c2-1).getNom());
	}
	
	public void listerObjets(List<Objets> lo) {

		System.out.println("\nObjets enregistrés en BD : ");
		for (Objets ob : lo) {
			String tour = " tours";
			if (ob.getDuree() == 1)
				tour = " tour";
			System.out.println("- " + ob.getNom() + " modifie de " + ob.getPoints() + " points votre " + ob.getCarac() + " pour " + ob.getDuree() + tour + " ("+ ob.getType()+")");
		}
	}
	public int supprimerObjet(List<Objets> lo) {
		int i = 1, choix = 0;
		
		System.out.println();
		System.out.println("Objets enregistrés : ");
		for (Objets ob : lo) {
			String tour = " tours.";
			if (ob.getDuree() == 1)
				tour = " tour.";
			System.out.println(i + ". " + ob.getNom() + " modifie de " + ob.getPoints() + " points votre " + ob.getCarac() + " pour " + ob.getDuree() + tour);
			i++;
		}
		
		do {
			System.out.println();
			System.out.print("Objet à supprimer : [1.." + lo.size() + "] ");
			choix = IO.lireEntier();
		} while (choix < 1 || choix > lo.size());
		
		return choix-1;
	}
	
	

}
