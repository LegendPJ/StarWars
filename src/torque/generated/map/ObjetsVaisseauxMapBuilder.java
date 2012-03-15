package torque.generated.map;

import java.util.Date;
import java.math.BigDecimal;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.map.MapBuilder;
import org.apache.torque.map.DatabaseMap;
import org.apache.torque.map.TableMap;
import org.apache.torque.map.ColumnMap;
import org.apache.torque.map.InheritanceMap;

/**
 * objets_vaisseaux
 *
  *  This class was autogenerated by Torque on:
  *
  * [Thu Mar 15 14:33:14 CET 2012]
  *
  */
public class ObjetsVaisseauxMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "torque.generated.map.ObjetsVaisseauxMapBuilder";

    /**
     * The database map.
     */
    private DatabaseMap dbMap = null;

    /**
     * Tells us if this DatabaseMapBuilder is built so that we
     * don't have to re-build it every time.
     *
     * @return true if this DatabaseMapBuilder is built
     */
    public boolean isBuilt()
    {
        return (dbMap != null);
    }

    /**
     * Gets the databasemap this map builder built.
     *
     * @return the databasemap
     */
    public DatabaseMap getDatabaseMap()
    {
        return this.dbMap;
    }

    /**
     * The doBuild() method builds the DatabaseMap
     *
     * @throws TorqueException
     */
    public synchronized void doBuild() throws TorqueException
    {
        if ( isBuilt() ) {
            return;
        }
        dbMap = Torque.getDatabaseMap("base5a00");

        dbMap.addTable("objets_vaisseaux");
        TableMap tMap = dbMap.getTable("objets_vaisseaux");
        tMap.setJavaName("ObjetsVaisseaux");
        tMap.setOMClass( torque.generated.ObjetsVaisseaux.class );
        tMap.setPeerClass( torque.generated.ObjetsVaisseauxPeer.class );
        tMap.setDescription("objets_vaisseaux");
        tMap.setPrimaryKeyMethod("none");

        ColumnMap cMap = null;


  // ------------- Column: nom_vaisseau --------------------
        cMap = new ColumnMap( "nom_vaisseau", tMap);
        cMap.setType( "" );
        cMap.setTorqueType( "VARCHAR" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(true);
        cMap.setNotNull(true);
        cMap.setJavaName( "NomVaisseau" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("Nom du vaisseau");
        cMap.setInheritance("false");
        cMap.setSize( 40 );
        cMap.setForeignKey("parties_vaisseaux", "nom_vaisseau");
        cMap.setPosition(1);
        tMap.addColumn(cMap);
  // ------------- Column: nom_partie --------------------
        cMap = new ColumnMap( "nom_partie", tMap);
        cMap.setType( "" );
        cMap.setTorqueType( "VARCHAR" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(true);
        cMap.setNotNull(true);
        cMap.setJavaName( "NomPartie" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("Nom de la partie");
        cMap.setInheritance("false");
        cMap.setSize( 40 );
        cMap.setForeignKey("parties_vaisseaux", "nom_partie");
        cMap.setPosition(2);
        tMap.addColumn(cMap);
  // ------------- Column: nom_objet --------------------
        cMap = new ColumnMap( "nom_objet", tMap);
        cMap.setType( "" );
        cMap.setTorqueType( "VARCHAR" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(true);
        cMap.setNotNull(true);
        cMap.setJavaName( "NomObjet" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("Nom de lobjet");
        cMap.setInheritance("false");
        cMap.setSize( 40 );
        cMap.setForeignKey("objets", "nom");
        cMap.setPosition(3);
        tMap.addColumn(cMap);
        tMap.setUseInheritance(false);
    }
}
