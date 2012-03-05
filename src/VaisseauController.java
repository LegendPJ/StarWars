import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.torque.TorqueException;

import torque.generated.Parties;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;
import torque.generated.VaisseauxPeer;

public class VaisseauController extends Controller {

	private static String VAISSEAUX[] = {"(-)", "[-]", "{-}" , "/-\\", "|-|"};
	private List<Vaisseaux> vaisseaux;
	private List<PartiesVaisseaux> partieV; 
	private HashSet<String> noms;
	
	public VaisseauController () {
		this.vaisseaux = new ArrayList<Vaisseaux> ();
		this.partieV = new ArrayList<PartiesVaisseaux> ();
		noms = new HashSet<String>();
	}
	/**
	 * Permet de créer un vaisseau (nom et type)
	 */
	public void creer(int numJoueur, Parties p) {
		int c1;
		String nomV;
		Vaisseaux v = new Vaisseaux();
		
		// Nom du vaisseau
		do {
			System.out.print("Joueur "+numJoueur+", veuillez nommer votre vaisseau : ");
			nomV = IO.lireChaine();
		} while (nomV.length() > 40 || VaisseauxPeer.nomPris(nomV, noms));
		
		// Type du vaisseau
		System.out.println("Joueur "+numJoueur+", choisissez votre vaisseau : ");
		for (int i=0; i < VAISSEAUX.length; i++) {
			int nb = i+1;
			System.out.println(nb+". "+VAISSEAUX[i]);
		}
		do {
			c1 = IO.lireEntier();
		} while (c1 > 5 || c1 < 1);
		System.out.println("Joueur " + numJoueur + ", \"" + nomV + "\" a été créé " + VAISSEAUX[c1-1] + "\n");
		
		// Caracteristiques du vaisseau pour la partie
		PartiesVaisseaux pv = this.setCaracs(numJoueur);
		
		try {
			v.setNom(nomV);
			v.setType(VAISSEAUX[c1-1]);
			pv.setNomVaisseau(v.getNom());
			pv.setNomPartie(p.getNom());
			pv.setNumJoueur(numJoueur);
			
			if (numJoueur == 1) {
				pv.setCoordX(4);
				pv.setCoordY(0);
			}
			else {
				pv.setCoordX(5);
				pv.setCoordY(9);
			}
			
			noms.add(nomV);
			vaisseaux.add(v);

			v.save(connTransaction);
			pv.save(connTransaction);
		} catch (TorqueException e1) {
			e1.printStackTrace();
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
		
		return new PartiesVaisseaux(atq, cdf, dgt, nrj);
	}
	@SuppressWarnings("unchecked")
	public void chargerVaisseauxPartie(Parties partie) {
		try {
			partieV = partie.getPartiesVaisseauxs();
			//On recupere les vaisseaux (type et nom)
			for (PartiesVaisseaux pv : partieV) {
				Vaisseaux v = new Vaisseaux(pv.getNomVaisseau(), pv.getVaisseaux().getType());
				vaisseaux.add(v);
			}
			
		} catch (TorqueException e) {
		}
	}
	public int chargerVaisseau(int numJoueur, Parties partie) {
		List<Vaisseaux> lVaisPossible = VaisseauxPeer.doSelectAll(vaisseaux);
		int r=0;
		if (lVaisPossible == null || lVaisPossible.size() == 0)
		{
			System.out.println("Aucun vaisseau disponible");
			r = 15;
		}
		else
		{
			int c = 1, menu = 0;
			int length = lVaisPossible.size();
			for (Vaisseaux v : lVaisPossible){
				System.out.println(c + ". " + v.getNom());
				c++;
			}
			do {
				System.out.println("Choisissez votre vaisseau [1.."+length+"] : ");
				menu = IO.lireEntier();
			} while (menu < 1 || menu > length);
			
			PartiesVaisseaux pv = this.setCaracs(numJoueur);
			
			try {
				
				pv.setNomVaisseau(lVaisPossible.get(menu-1).getNom());
				pv.setNomPartie(partie.getNom());
				pv.setNumJoueur(numJoueur);
				
				vaisseaux.add(lVaisPossible.get(menu-1));
				
				pv.save(connTransaction);
			} catch (TorqueException e1) {
				e1.printStackTrace();
			}
			
		}
		return r;
	}
	
	public void clearLists() {
		vaisseaux.clear();
		partieV.clear();
	}
	public List<Vaisseaux> getVaisseaux() {
		return vaisseaux;
	}
}

