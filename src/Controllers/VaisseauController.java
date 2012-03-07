package Controllers;
import java.util.HashSet;
import java.util.List;

import org.apache.torque.TorqueException;

import torque.generated.Parties;
import torque.generated.PartiesVaisseaux;
import torque.generated.Vaisseaux;
import torque.generated.VaisseauxPeer;

public class VaisseauController extends Controller {

	
	public VaisseauController () {
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
}

