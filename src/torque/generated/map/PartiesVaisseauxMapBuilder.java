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
 * parties_vaisseaux
 *
  *  This class was autogenerated by Torque on:
  *
  * [Tue Feb 21 11:24:42 CET 2012]
  *
  */
public class PartiesVaisseauxMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "torque.generated.map.PartiesVaisseauxMapBuilder";

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

        dbMap.addTable("parties_vaisseaux");
        TableMap tMap = dbMap.getTable("parties_vaisseaux");
        tMap.setJavaName("PartiesVaisseaux");
        tMap.setOMClass( torque.generated.PartiesVaisseaux.class );
        tMap.setPeerClass( torque.generated.PartiesVaisseauxPeer.class );
        tMap.setDescription("parties_vaisseaux");
        tMap.setPrimaryKeyMethod("none");

        ColumnMap cMap = null;


  // ------------- Column: id_partie --------------------
        cMap = new ColumnMap( "id_partie", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(true);
        cMap.setNotNull(true);
        cMap.setJavaName( "IdPartie" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("Une partie");
        cMap.setInheritance("false");
        cMap.setForeignKey("parties", "id");
        cMap.setPosition(1);
        tMap.addColumn(cMap);
  // ------------- Column: nom_vaisseau --------------------
        cMap = new ColumnMap( "nom_vaisseau", tMap);
        cMap.setType( "" );
        cMap.setTorqueType( "VARCHAR" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "NomVaisseau" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("Nom du vaisseau");
        cMap.setInheritance("false");
        cMap.setSize( 40 );
        cMap.setForeignKey("vaisseaux", "nom");
        cMap.setPosition(2);
        tMap.addColumn(cMap);
  // ------------- Column: coord_x --------------------
        cMap = new ColumnMap( "coord_x", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "CoordX" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("coordonnÃ©es en X");
        cMap.setInheritance("false");
        cMap.setPosition(3);
        tMap.addColumn(cMap);
  // ------------- Column: coord_y --------------------
        cMap = new ColumnMap( "coord_y", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "CoordY" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("coordonnÃ©es en Y");
        cMap.setInheritance("false");
        cMap.setPosition(4);
        tMap.addColumn(cMap);
        tMap.setUseInheritance(false);
    }
}