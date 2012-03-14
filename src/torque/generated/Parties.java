package torque.generated;


import java.util.ArrayList;
import java.util.List;

import org.apache.torque.TorqueException;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;

/**
 * parties
 *
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Tue Feb 21 11:05:11 CET 2012]
 *
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */
public  class Parties
    extends torque.generated.BaseParties
    implements Persistent
{
	public Parties() { super(); }
	
    public Parties(String nom) {
    	super();
		try {
			this.setNom(nom);
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}
    
    @SuppressWarnings("unchecked")
	public List<PartiesVaisseaux> getPartiesVaisseauxsOrdered() {
    	Criteria criteria = new Criteria();
    	List<PartiesVaisseaux> l = new ArrayList<PartiesVaisseaux>();
    	criteria.addAscendingOrderByColumn(PartiesVaisseauxPeer.NUM_JOUEUR);
    	try {
			l = this.getPartiesVaisseauxs(criteria);
		} catch (TorqueException e) {
			e.printStackTrace();
		}
    	return l;
    }

	/** Serial version */
    private static final long serialVersionUID = 1329818711143L;

}
