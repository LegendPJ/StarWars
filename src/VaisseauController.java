import java.util.HashSet;

import org.apache.torque.NoRowsException;
import org.apache.torque.TooManyRowsException;
import org.apache.torque.TorqueException;

import torque.generated.BaseVaisseauxPeer;
import torque.generated.Parties;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;

public class VaisseauController extends Controller {

	static String VAISSEAUX[] = {"(-)", "[-]", "{-}" , "/-\\", "|-|"};
	private HashSet<Vaisseaux> vaisseaux;
	private HashSet<String> noms;
	
	public VaisseauController () {
		this.vaisseaux = new HashSet<Vaisseaux> ();
		noms = new HashSet<String>();
	}
	/**
	 * Permet de créer un vaisseau (nom et type)
	 */
	public void creer(int numJoueur, Parties p) {
		int c1;
		String nomV;
		Vaisseaux v = new Vaisseaux();
		Vaisseaux vE;
		boolean contain;
		
		
		
		
		
		
		/* CHANGER LA BD POUR METTRE LE NOM DUNE PARTIE EN PRIMARY KEY!!!!!!
		 * 
		 * 
		 * URGENTISSIME
		 * 
		 * */
		
		
		
		
		
		
		
		
		
		
		
		
		// Nom du vaisseau
		do {
			contain = false;
			vE = null;
			System.out.print("Joueur "+numJoueur+", veuillez nommer votre vaisseau : ");
			nomV = IO.lireChaine();
			try {
				vE = BaseVaisseauxPeer.retrieveByPK(nomV);
			} catch (NoRowsException e) {
				for (String vtest : noms) {
					if (vtest.toString().equals(nomV.toString()))
						contain = true;
				}
			} catch (TooManyRowsException e) {
				// TODO Auto-generated catch block

			} catch (TorqueException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (nomV.length() > 40 || vE != null || contain == true);
		
		try {
			// TODO: répéter tant qu'il y a une exception
			v.setNom(nomV);
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		noms.add(nomV);
		
		// Type du vasseau
		System.out.println("Joueur "+numJoueur+", choisissez votre vaisseau : ");
		for (int i=0; i < VAISSEAUX.length; i++) {
			int nb = i+1;
			System.out.println(nb+". "+VAISSEAUX[i]);
		}
		do {
			c1 = IO.lireEntier();
		} while (c1 > 5 || c1 < 1);
		System.out.println("Joueur "+numJoueur+", \""+v.getNom()+"\" a été créé "+VAISSEAUX[c1-1]+"\n");
		v.setType(VAISSEAUX[c1-1]);
		
		// Caracteristiques du vaisseau pour la partie
		PartiesVaisseaux pv = this.setCaracs(numJoueur);
		
		try {
			pv.setNomVaisseau(v.getNom());
			pv.setIdPartie(p.getId());
			pv.setNumJoueur(numJoueur);
		} catch (TorqueException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Ajoute le vaisseau en bd et pour le controleur
		vaisseaux.add(v);
		try {
			v.save(connTransaction);
			pv.save(connTransaction);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Ajoute les caracteristiques au vaisseau pour la partie
	 * @param v Vaisseau sans ses caracteristiques
	 * @return vaisseau avec ses caracteristiques
	 */
	public PartiesVaisseaux setCaracs(int numJoueur) {
		
		int atq = 0, dgt = 0, cdf = 0, nrj = 0;
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
		System.out.println("Votre vaisseau aura donc "+nrj+" points d'energie.");
		// TODO set les caracteristiques
		
		return new PartiesVaisseaux(atq, cdf, dgt, nrj);
	}
}

