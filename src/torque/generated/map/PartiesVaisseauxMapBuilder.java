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
  * [Mon Mar 05 15:38:02 CET 2012]
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
        cMap.setDescription("Une partie");
        cMap.setInheritance("false");
        cMap.setSize( 40 );
        cMap.setForeignKey("parties", "nom");
        cMap.setPosition(1);
        tMap.addColumn(cMap);
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
        cMap.setDescription("coordonnées en X");
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
        cMap.setDescription("coordonnées en Y");
        cMap.setInheritance("false");
        cMap.setPosition(4);
        tMap.addColumn(cMap);
  // ------------- Column: attaque --------------------
        cMap = new ColumnMap( "attaque", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "Attaque" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("attaque du vaisseau");
        cMap.setDefault("0");
        cMap.setInheritance("false");
        cMap.setPosition(5);
        tMap.addColumn(cMap);
  // ------------- Column: degats --------------------
        cMap = new ColumnMap( "degats", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "Degats" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("degats du vaisseau");
        cMap.setDefault("0");
        cMap.setInheritance("false");
        cMap.setPosition(6);
        tMap.addColumn(cMap);
  // ------------- Column: champ --------------------
        cMap = new ColumnMap( "champ", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "Champ" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("champ du vaisseau");
        cMap.setDefault("0");
        cMap.setInheritance("false");
        cMap.setPosition(7);
        tMap.addColumn(cMap);
  // ------------- Column: energie --------------------
        cMap = new ColumnMap( "energie", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "Energie" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("energie du vaisseau");
        cMap.setDefault("0");
        cMap.setInheritance("false");
        cMap.setPosition(8);
        tMap.addColumn(cMap);
  // ------------- Column: pa --------------------
        cMap = new ColumnMap( "pa", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "Pa" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("points daction du vaisseau");
        cMap.setDefault("6");
        cMap.setInheritance("false");
        cMap.setPosition(9);
        tMap.addColumn(cMap);
  // ------------- Column: num_joueur --------------------
        cMap = new ColumnMap( "num_joueur", tMap);
        cMap.setType( new Integer(0) );
        cMap.setTorqueType( "INTEGER" );
        cMap.setUsePrimitive(true);
        cMap.setPrimaryKey(false);
        cMap.setNotNull(true);
        cMap.setJavaName( "NumJoueur" );
        cMap.setAutoIncrement(false);
        cMap.setProtected(false);
        cMap.setDescription("numero du joueur");
        cMap.setInheritance("false");
        cMap.setPosition(10);
        tMap.addColumn(cMap);
        tMap.setUseInheritance(false);
    }
}
