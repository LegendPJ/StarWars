package torque.generated;

import java.util.ArrayList;
import java.util.List;

import org.apache.torque.NoRowsException;
import org.apache.torque.TooManyRowsException;
import org.apache.torque.TorqueException;
import org.apache.torque.util.Criteria;

/**
 * vaisseaux
 *
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Tue Feb 21 11:05:11 CET 2012]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class VaisseauxPeer
    extends torque.generated.BaseVaisseauxPeer
{
    /** Serial version */
    private static final long serialVersionUID = 1329818711143L;

	public static boolean nomPris(String nomV, List<String> noms) {
		boolean vE = false;
		
		try {
			BaseVaisseauxPeer.retrieveByPK(nomV);
			vE = true;
		} catch (NoRowsException e) {
			for (String vtest : noms) {
				if (vtest.toString().equals(nomV.toString()))
					vE = true;
			}
		} catch (TooManyRowsException e) {

		} catch (TorqueException e) {
			e.printStackTrace();
		}
		return vE;
	}

	@SuppressWarnings("unchecked")
	public static List<Vaisseaux> doSelectAll() {
		List<Vaisseaux> v = new ArrayList<Vaisseaux>();
		try {
			v = VaisseauxPeer.doSelect(new Criteria());
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		return v;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Vaisseaux> doSelectAllNotSelected(List<Vaisseaux> vlist) {
		List<Vaisseaux> v = null;
		Criteria c = new Criteria();
		if (vlist.size() != 0) {
			List<String> nList = new ArrayList<String>();
			for (Vaisseaux vaisso : vlist)
				nList.add(vaisso.getNom());
			c.add(VaisseauxPeer.NOM, nList, Criteria.NOT_IN);
		}
				
		try {
			v = VaisseauxPeer.doSelect(c);
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		return v;
	}
}
