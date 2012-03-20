package Vues;

import java.util.ArrayList;
import java.util.List;

import org.apache.torque.TorqueException;

import Services.IO;
import Services.Messages;
import torque.generated.Objets;
import torque.generated.ObjetsParties;
import torque.generated.ObjetsVaisseaux;
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
	/**
	 * Menu de sélection de vaisseau
	 * @param numJoueur numéro du joueur
	 * @param nbVaisseaux nombre de vaisseaux déjà créé
	 * @return action à faire, 1 => créer, 2 => utiliser, 0 => quitter
	 */
	public int menuJoueur (int numJoueur, int nbVaisseaux) {
		int choix = Vue.QUITTER, choixMax = 1;
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
		do{
			System.out.print("Joueur "+numJoueur+" que voulez-vous faire ? [0.."+choixMax+"] ");
			choix = IO.lireEntier();
		} while (choix < Vue.QUITTER || choix > choixMax);
		
		return choix;
	}
	/**
	 * Créer un vaisseau pour un joueur
	 * @param numJoueur numéro du joueur
	 * @return le vaisseau créé
	 */
	public Vaisseaux nouveauVaisseau (int numJoueur) {
		String nomVaisseau = new String();
		int c1;
		
		// Nom du vaisseau
		do {
			System.out.print("Joueur "+numJoueur+", veuillez nommer votre vaisseau : ");
			nomVaisseau = IO.lireChaine();
			nomVaisseau = nomVaisseau.trim();
		} while (nomVaisseau.length() > 40 || VaisseauxPeer.nomPris(nomVaisseau, noms));
		noms.add(nomVaisseau);
		
		// Type du vaisseau
		System.out.println("Joueur "+numJoueur+", choisissez votre vaisseau : ");
		for (int i=0; i < VAISSEAUX.length; i++) {
			int nb = i+1;
			System.out.println(nb+". "+VAISSEAUX[i]);
		}
		System.out.println();
		do {
			System.out.print("Choix : ");
			c1 = IO.lireEntier();
		} while (c1 > 5 || c1 < 1);
		
		System.out.println("Joueur " + numJoueur + ", \"" + nomVaisseau + "\" a été créé " + VAISSEAUX[c1-1] + "\n");
		
		return new Vaisseaux (nomVaisseau, VAISSEAUX[c1-1]);
	}
	/**
	 * Enregistre les caractéristiques du vaisseau (attaque, champ de force...)
	 * @param numJoueur numéro du joueur
	 * @return Le vaisseau qui joue dans la partie (VaisseauPartie)
	 */
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
	/**
	 * Utiliser un vaisseau existant (nom+type)
	 * @param numJoueur numéro du joueur
	 * @param vaisseaux liste de tous les vaisseaux enregistrés
	 * @return le vaisseau choisi
	 */
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
	/**
	 * Permet de choisir quel vaisseau attaquer
	 * @param vais Liste des vaisseaux en jeu
	 * @param coordX abscisse de l'attaquant
	 * @param coordY ordonnée de l'attaquant
	 * @param nom nom du vaisseau de l'attaquant
	 * @return numéro du vaisseau à attaquer
	 */
	public int attaquer(List<PartiesVaisseaux> vais, int coordX, int coordY, String nom) {
		int i = 1, menu = 0;
		List<PartiesVaisseaux> vDispo = new ArrayList<PartiesVaisseaux>();
		for (PartiesVaisseaux v : vais) {
			if (getController().memeCase(coordX, coordY, nom)
					&& !v.getNomVaisseau().equals(nom)) {
				System.out.println(i + ". " + v.getNomVaisseau());
				vDispo.add(v);
				i++;
			}
		}
		do {
			System.out.print("Quel vaisseau attaquer ? [1.." + vDispo.size() + "] ");
			menu = IO.lireEntier();
		} while (menu < 1 || menu > vDispo.size());
		
		return vais.indexOf(vDispo.get(menu-1));
	}
	/**
	 * Ramasser un objet
	 * @param x Abscisse du vaisseau qui ramasse
	 * @param y Ordonnée du vaisseau qui ramasse
	 * @return numéro de l'objet ramassé
	 */
	public int ramasserObjet (int x, int y) {
		List<ObjetsParties> objp = getController().getObjetsParties();
		List<ObjetsParties> objc = new ArrayList<ObjetsParties>(); // Objets sur la case (x, y)
		int j = 0, menu = 0;
		for (ObjetsParties obj : objp) {
			if (obj.getCoordX() == x && obj.getCoordY() == y) {
				Objets ob;
				j++;
				try {
					ob = obj.getObjets();
					objc.add(obj);
					String tour = " tours.";
					if (ob.getDuree() == 1)
						tour = " tour.";
					if (ob.getCarac().equals("energie"))
						System.out.println(j + ". " + ob.getNom() + " modifie de " + ob.getPoints() + " points votre " + ob.getCarac() + " pour " + ob.getDuree() + tour);
					else
						System.out.println(j + ". " + ob.getNom() + " modifie de " + ob.getPoints() + " points votre " + ob.getCarac() + " jusqu'à la fin de la partie");
				} catch (TorqueException e) {
					e.printStackTrace();
				}
			}
		}
		
		do {
			System.out.print("Quel objet ramasser ? [1.." + j + "] ");
			menu = IO.lireEntier();
		} while (menu < 1 || menu > j);
		return objp.indexOf(objc.get(menu-1));
	}
	/**
	 * Lister les objets dans l'équipement du vaisseau
	 * @param ov les objets du vaisseau
	 */
	public void listerObjets(List<ObjetsVaisseaux> ov) {
		if (ov.size() == 0) 
			Messages.setMessage("Aucun objet dans votre équipement !");
		else {
			Messages.setMessage("Objets dans votre équipement :\n");
			for (ObjetsVaisseaux o : ov) {
				try {
					Objets obj = o.getObjets();
					String tour = " tours.";
					if (o.getDureeRestante() == 1)
						tour = " tour.";
					
					if (obj.getCarac().equals("energie"))
						Messages.append("- " + obj.getNom() + " modifie de " + obj.getPoints() + " points votre " + obj.getCarac() + " pour " + obj.getDuree() + tour);
					else
						Messages.append("- " + obj.getNom() + " modifie de " + obj.getPoints() + " points votre " + obj.getCarac() + " jusqu'à la fin de la partie");
					if (o.getEquipe() && obj.getType().equals("arme"))
						Messages.append(" (équipé)\n");
					else if (!o.getEquipe() && obj.getType().equals("arme"))
						Messages.append(" (non équipé)\n");
					else if (o.getEquipe() && obj.getType().equals("bonus"))
						Messages.append(" (utilisé)\n");
					else if (!o.getEquipe() && obj.getType().equals("bonus"))
						Messages.append(" (non utilisé)\n");
				} catch (TorqueException e) {
					e.printStackTrace();
				}
			} 
		} Messages.appendln("");
	}
	/**
	 * Affiche un message au joueur gagnant
	 * @param nomVaisseau nom du vaisseau gagnant
	 * @param nomPartie nom de la partie gagnée
	 */
	public void gagner(String nomVaisseau, String nomPartie) {
		Messages.setMessage("Félicitations "+nomVaisseau+", vous avez gagné la partie "+nomPartie+" !!");
	}
	/**
	 * Liste les objets utilisables et le joueur choisi le quel équiper ou utiliser (arme/bonus)
	 * @param objetsliste des objets
	 * @return numéro de l'objet à équiper ou utiliser (arme/bonus)
	 */
	public int utiliserObjet(List<ObjetsVaisseaux> objet, String type) {
		int choix = 0, i = 1;
		try {
			for (ObjetsVaisseaux o : objet) {
				Objets obj = o.getObjets();
				String tour = " tours.";
				if (obj.getDuree() == 1)
					tour = " tour.";
				if (obj.getCarac().equals("energie"))
					System.out.println(i + ". " + obj.getNom() + " modifie de " + obj.getPoints() + " points votre " + obj.getCarac() + " pour " + obj.getDuree() + tour);
				else
					System.out.println(i + ". " + obj.getNom() + " modifie de " + obj.getPoints() + " points votre " + obj.getCarac() + " jusqu'à la fin de la partie");
				i++;
			}
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		
		do {
			if (type.equals("arme"))
				System.out.print("Quelle arme voulez-vous utiliser ? [1.."+objet.size()+"] ");
			else if (type.equals("bonus"))
				System.out.print("Quel bonus voulez-vous utiliser ? [1.."+objet.size()+"] ");
			choix = IO.lireEntier();
		} while (choix < 1 && choix > objet.size());
		
		return choix-1;
	}
	
}