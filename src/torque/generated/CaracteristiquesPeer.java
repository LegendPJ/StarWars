package torque.generated;

import java.util.ArrayList;
import java.util.List;

import org.apache.torque.TorqueException;
import org.apache.torque.util.Criteria;

/**
 * caracteristiques
 *
 * The skeleton for this class was autogenerated by Torque on:
 *
 * [Tue Feb 21 11:05:11 CET 2012]
 *
 *  You should add additional methods to this class to meet the
 *  application requirements.  This class will only be generated as
 *  long as it does not already exist in the output directory.
 */
public class CaracteristiquesPeer
    extends torque.generated.BaseCaracteristiquesPeer
{
    /** Serial version */
    private static final long serialVersionUID = 1329818711143L;

	@SuppressWarnings("unchecked")
	public static List<Caracteristiques> doSelectAll() {
		List<Caracteristiques> c = new ArrayList<Caracteristiques>();
		try {
			c = CaracteristiquesPeer.doSelect(new Criteria());
		} catch (TorqueException e) {
			e.printStackTrace();
		}
		return c;
	}

}
