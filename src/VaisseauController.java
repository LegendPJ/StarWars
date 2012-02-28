import java.util.HashSet;

import org.apache.torque.TorqueException;

import torque.generated.Vaisseaux;

public class VaisseauController extends Controller {

	static String VAISSEAUX[] = {"(-)", "[-]", "{-}" , "/-\\", "|-|"};
	private int numJoueur;
	private HashSet<Vaisseaux> vaisseaux;
	
	public VaisseauController () {
		this.numJoueur = 1;
		this.vaisseaux = new HashSet<Vaisseaux> ();
	}
	/**
	 * Permet de créer un vaisseau (nom et type)
	 */
	public void creer() {
		int c1;
		Vaisseaux v = new Vaisseaux();
		
		// Nom du vaisseau
		System.out.print("Joueur "+numJoueur+", veuillez nommer votre vaisseau : ");
		try {
			// TODO: répéter tant qu'il y a une exception
			v.setNom(IO.lireChaine());
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		
		// Type du vasseau
		System.out.println("Joueur "+numJoueur+", choisissez votre vaisseau : ");
		for (int i=0; i < VAISSEAUX.length; i++) {
			int nb = i+1;
			System.out.println(nb+". "+VAISSEAUX[i]);
		}
		do {
			c1 = IO.lireEntier();
		} while (c1 > 5 || c1 < 1);
		System.out.println("Joueur "+numJoueur+", \""+v.getNom()+"\" a été créé "+VAISSEAUX[c1-1]);
		v.setType(VAISSEAUX[c1-1]);
		
		// Caracteristiques du vaisseau pour la partie
		v = this.setCaracs(v);
		
		// Ajoute le vaisseau en bd et pour le controleur
		vaisseaux.add(v);
		try {
			v.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.numJoueur++;
	}
	/**
	 * Ajoute les caracteristiques au vaisseau pour la partie
	 * @param v Vaisseau sans ses caracteristiques
	 * @return vaisseau avec ses caracteristiques
	 */
	public Vaisseaux setCaracs(Vaisseaux v) {
		System.out.print("Joueur "+numJoueur+", veuillez caractériser votre vaisseau : ");
		// TODO set les caracteristiques
		
		return v;
	}
}

