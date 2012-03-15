package torque.generated.map;

import org.apache.torque.TorqueException;

/**
 * This is a Torque Generated class that is used to load all database map 
 * information at once.  This is useful because Torque's default behaviour
 * is to do a "lazy" load of mapping information, e.g. loading it only
 * when it is needed.<p>
 *
 * @see org.apache.torque.map.DatabaseMap#initialize() DatabaseMap.initialize() 
 */
public class Base5a00MapInit
{
    public static final void init()
        throws TorqueException
    {
        torque.generated.VaisseauxPeer.getMapBuilder();
        torque.generated.CaracteristiquesPeer.getMapBuilder();
        torque.generated.TypesPeer.getMapBuilder();
        torque.generated.ObjetsPeer.getMapBuilder();
        torque.generated.PartiesPeer.getMapBuilder();
        torque.generated.PartiesVaisseauxPeer.getMapBuilder();
        torque.generated.ObjetsVaisseauxPeer.getMapBuilder();
        torque.generated.ObjetsPartiesPeer.getMapBuilder();
    }
}
