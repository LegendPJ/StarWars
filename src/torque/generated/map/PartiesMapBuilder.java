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
 * parties
 *
  *  This class was autogenerated by Torque on:
  *
  * [Mon Mar 05 15:38:02 CET 2012]
  *
  */
public class PartiesMapBuilder implements MapBuilder
{
    /**
     * The name of this class
     */
    public static final String CLASS_NAME =
        "torque.generated.map.PartiesMapBuilder";

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

        dbMap.addTable("parties");
        TableMap tMap = dbMap.getTable("parties");
        tMap.setJavaName("Parties");
        tMap.setOMClass( torque.generated.Parties.class );
        tMap.setPeerClass( torque.generated.PartiesPeer.class );
        tMap.setDescription("parties");
        tMap.setPrimaryKeyMethod("none");

        ColumnMap cMap = null;


  // ------------- Column: nom --------------------
        cMap = new ColumnMap( "nom", tMap);
        cMap.setType( "" );
        cMap.setTorqueType( "VARCHAR" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(true);
        cMap.setNotNull(true);
        cMap.setJavaName( "Nom" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("Nom de la partie");
        cMap.setInheritance("false");
        cMap.setSize( 40 );
        cMap.setPosition(1);
        tMap.addColumn(cMap);
  // ------------- Column: tour --------------------
        cMap = new ColumnMap( "tour", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "Tour" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("loto a qui le tour ?");
        cMap.setInheritance("false");
        cMap.setPosition(2);
        tMap.addColumn(cMap);
        tMap.setUseInheritance(false);
    }
}
