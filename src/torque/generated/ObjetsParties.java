package torque.generated;

import org.apache.torque.TorqueException;

/**
 * objets
 *
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Tue Feb 21 11:05:11 CET 2012]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class ObjetsParties
    extends torque.generated.BaseObjetsParties
{
	public ObjetsParties() { super(); }
    public ObjetsParties(String nom_obj, String nomP, int x, int y) {
    	try {
			this.setNomObjet(nom_obj);
	    	this.setNomPartie(nomP);
	    	this.setCoordX(x);
	    	this.setCoordY(y);
		} catch (TorqueException e) {
			e.printStackTrace();
		}
	}

	/** Serial version */
    private static final long serialVersionUID = 1329818711143L;

}
