package torque.generated;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.torque.TorqueException;
import org.apache.torque.map.TableMap;
import org.apache.torque.om.BaseObject;
import org.apache.torque.om.ComboKey;
import org.apache.torque.om.DateKey;
import org.apache.torque.om.NumberKey;
import org.apache.torque.om.ObjectKey;
import org.apache.torque.om.SimpleKey;
import org.apache.torque.om.StringKey;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;
import org.apache.torque.util.Transaction;





/**
 * objets_parties
 *
 * This class was autogenerated by Torque on:
 *
 * [Tue Mar 20 14:25:17 CET 2012]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to ObjetsParties
 */
public abstract class BaseObjetsParties extends BaseObject
{
    /** Serial version */
    private static final long serialVersionUID = 1332249917606L;

    /** The Peer class */
    private static final ObjetsPartiesPeer peer =
        new ObjetsPartiesPeer();


    /** The value for the nomPartie field */
    private String nomPartie;

    /** The value for the nomObjet field */
    private String nomObjet;

    /** The value for the coordX field */
    private int coordX;

    /** The value for the coordY field */
    private int coordY;


    /**
     * Get the NomPartie
     *
     * @return String
     */
    public String getNomPartie()
    {
        return nomPartie;
    }


    /**
     * Set the value of NomPartie
     *
     * @param v new value
     */
    public void setNomPartie(String v) throws TorqueException
    {

        if (!ObjectUtils.equals(this.nomPartie, v))
        {
            this.nomPartie = v;
            setModified(true);
        }


        if (aParties != null && !ObjectUtils.equals(aParties.getNom(), v))
        {
            aParties = null;
        }

    }

    /**
     * Get the NomObjet
     *
     * @return String
     */
    public String getNomObjet()
    {
        return nomObjet;
    }


    /**
     * Set the value of NomObjet
     *
     * @param v new value
     */
    public void setNomObjet(String v) throws TorqueException
    {

        if (!ObjectUtils.equals(this.nomObjet, v))
        {
            this.nomObjet = v;
            setModified(true);
        }


        if (aObjets != null && !ObjectUtils.equals(aObjets.getNom(), v))
        {
            aObjets = null;
        }

    }

    /**
     * Get the CoordX
     *
     * @return int
     */
    public int getCoordX()
    {
        return coordX;
    }


    /**
     * Set the value of CoordX
     *
     * @param v new value
     */
    public void setCoordX(int v) 
    {

        if (this.coordX != v)
        {
            this.coordX = v;
            setModified(true);
        }


    }

    /**
     * Get the CoordY
     *
     * @return int
     */
    public int getCoordY()
    {
        return coordY;
    }


    /**
     * Set the value of CoordY
     *
     * @param v new value
     */
    public void setCoordY(int v) 
    {

        if (this.coordY != v)
        {
            this.coordY = v;
            setModified(true);
        }


    }

    



    private Parties aParties;

    /**
     * Declares an association between this object and a Parties object
     *
     * @param v Parties
     * @throws TorqueException
     */
    public void setParties(Parties v) throws TorqueException
    {
        if (v == null)
        {
            setNomPartie((String) null);
        }
        else
        {
            setNomPartie(v.getNom());
        }
        aParties = v;
    }


    /**
     * Returns the associated Parties object.
     * If it was not retrieved before, the object is retrieved from
     * the database
     *
     * @return the associated Parties object
     * @throws TorqueException
     */
    public Parties getParties()
        throws TorqueException
    {
        if (aParties == null && (!ObjectUtils.equals(this.nomPartie, null)))
        {
            aParties = PartiesPeer.retrieveByPK(SimpleKey.keyFor(this.nomPartie));
        }
        return aParties;
    }

    /**
     * Return the associated Parties object
     * If it was not retrieved before, the object is retrieved from
     * the database using the passed connection
     *
     * @param connection the connection used to retrieve the associated object
     *        from the database, if it was not retrieved before
     * @return the associated Parties object
     * @throws TorqueException
     */
    public Parties getParties(Connection connection)
        throws TorqueException
    {
        if (aParties == null && (!ObjectUtils.equals(this.nomPartie, null)))
        {
            aParties = PartiesPeer.retrieveByPK(SimpleKey.keyFor(this.nomPartie), connection);
        }
        return aParties;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey, for example
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setPartiesKey(ObjectKey key) throws TorqueException
    {

        setNomPartie(key.toString());
    }




    private Objets aObjets;

    /**
     * Declares an association between this object and a Objets object
     *
     * @param v Objets
     * @throws TorqueException
     */
    public void setObjets(Objets v) throws TorqueException
    {
        if (v == null)
        {
            setNomObjet((String) null);
        }
        else
        {
            setNomObjet(v.getNom());
        }
        aObjets = v;
    }


    /**
     * Returns the associated Objets object.
     * If it was not retrieved before, the object is retrieved from
     * the database
     *
     * @return the associated Objets object
     * @throws TorqueException
     */
    public Objets getObjets()
        throws TorqueException
    {
        if (aObjets == null && (!ObjectUtils.equals(this.nomObjet, null)))
        {
            aObjets = ObjetsPeer.retrieveByPK(SimpleKey.keyFor(this.nomObjet));
        }
        return aObjets;
    }

    /**
     * Return the associated Objets object
     * If it was not retrieved before, the object is retrieved from
     * the database using the passed connection
     *
     * @param connection the connection used to retrieve the associated object
     *        from the database, if it was not retrieved before
     * @return the associated Objets object
     * @throws TorqueException
     */
    public Objets getObjets(Connection connection)
        throws TorqueException
    {
        if (aObjets == null && (!ObjectUtils.equals(this.nomObjet, null)))
        {
            aObjets = ObjetsPeer.retrieveByPK(SimpleKey.keyFor(this.nomObjet), connection);
        }
        return aObjets;
    }

    /**
     * Provides convenient way to set a relationship based on a
     * ObjectKey, for example
     * <code>bar.setFooKey(foo.getPrimaryKey())</code>
     *
     */
    public void setObjetsKey(ObjectKey key) throws TorqueException
    {

        setNomObjet(key.toString());
    }
   
        
    private static List fieldNames = null;

    /**
     * Generate a list of field names.
     *
     * @return a list of field names
     */
    public static synchronized List getFieldNames()
    {
        if (fieldNames == null)
        {
            fieldNames = new ArrayList();
            fieldNames.add("NomPartie");
            fieldNames.add("NomObjet");
            fieldNames.add("CoordX");
            fieldNames.add("CoordY");
            fieldNames = Collections.unmodifiableList(fieldNames);
        }
        return fieldNames;
    }

    /**
     * Retrieves a field from the object by field (Java) name passed in as a String.
     *
     * @param name field name
     * @return value
     */
    public Object getByName(String name)
    {
        if (name.equals("NomPartie"))
        {
            return getNomPartie();
        }
        if (name.equals("NomObjet"))
        {
            return getNomObjet();
        }
        if (name.equals("CoordX"))
        {
            return new Integer(getCoordX());
        }
        if (name.equals("CoordY"))
        {
            return new Integer(getCoordY());
        }
        return null;
    }

    /**
     * Set a field in the object by field (Java) name.
     *
     * @param name field name
     * @param value field value
     * @return True if value was set, false if not (invalid name / protected field).
     * @throws IllegalArgumentException if object type of value does not match field object type.
     * @throws TorqueException If a problem occurs with the set[Field] method.
     */
    public boolean setByName(String name, Object value )
        throws TorqueException, IllegalArgumentException
    {
        if (name.equals("NomPartie"))
        {
            // Object fields can be null
            if (value != null && ! String.class.isInstance(value))
            {
                throw new IllegalArgumentException("Invalid type of object specified for value in setByName");
            }
            setNomPartie((String) value);
            return true;
        }
        if (name.equals("NomObjet"))
        {
            // Object fields can be null
            if (value != null && ! String.class.isInstance(value))
            {
                throw new IllegalArgumentException("Invalid type of object specified for value in setByName");
            }
            setNomObjet((String) value);
            return true;
        }
        if (name.equals("CoordX"))
        {
            if (value == null || ! (Integer.class.isInstance(value)))
            {
                throw new IllegalArgumentException("setByName: value parameter was null or not an Integer object.");
            }
            setCoordX(((Integer) value).intValue());
            return true;
        }
        if (name.equals("CoordY"))
        {
            if (value == null || ! (Integer.class.isInstance(value)))
            {
                throw new IllegalArgumentException("setByName: value parameter was null or not an Integer object.");
            }
            setCoordY(((Integer) value).intValue());
            return true;
        }
        return false;
    }

    /**
     * Retrieves a field from the object by name passed in
     * as a String.  The String must be one of the static
     * Strings defined in this Class' Peer.
     *
     * @param name peer name
     * @return value
     */
    public Object getByPeerName(String name)
    {
        if (name.equals(ObjetsPartiesPeer.NOM_PARTIE))
        {
            return getNomPartie();
        }
        if (name.equals(ObjetsPartiesPeer.NOM_OBJET))
        {
            return getNomObjet();
        }
        if (name.equals(ObjetsPartiesPeer.COORD_X))
        {
            return new Integer(getCoordX());
        }
        if (name.equals(ObjetsPartiesPeer.COORD_Y))
        {
            return new Integer(getCoordY());
        }
        return null;
    }

    /**
     * Set field values by Peer Field Name
     *
     * @param name field name
     * @param value field value
     * @return True if value was set, false if not (invalid name / protected field).
     * @throws IllegalArgumentException if object type of value does not match field object type.
     * @throws TorqueException If a problem occurs with the set[Field] method.
     */
    public boolean setByPeerName(String name, Object value)
        throws TorqueException, IllegalArgumentException
    {
      if (ObjetsPartiesPeer.NOM_PARTIE.equals(name))
        {
            return setByName("NomPartie", value);
        }
      if (ObjetsPartiesPeer.NOM_OBJET.equals(name))
        {
            return setByName("NomObjet", value);
        }
      if (ObjetsPartiesPeer.COORD_X.equals(name))
        {
            return setByName("CoordX", value);
        }
      if (ObjetsPartiesPeer.COORD_Y.equals(name))
        {
            return setByName("CoordY", value);
        }
        return false;
    }

    /**
     * Retrieves a field from the object by Position as specified
     * in the xml schema.  Zero-based.
     *
     * @param pos position in xml schema
     * @return value
     */
    public Object getByPosition(int pos)
    {
        if (pos == 0)
        {
            return getNomPartie();
        }
        if (pos == 1)
        {
            return getNomObjet();
        }
        if (pos == 2)
        {
            return new Integer(getCoordX());
        }
        if (pos == 3)
        {
            return new Integer(getCoordY());
        }
        return null;
    }

    /**
     * Set field values by its position (zero based) in the XML schema.
     *
     * @param position The field position
     * @param value field value
     * @return True if value was set, false if not (invalid position / protected field).
     * @throws IllegalArgumentException if object type of value does not match field object type.
     * @throws TorqueException If a problem occurs with the set[Field] method.
     */
    public boolean setByPosition(int position, Object value)
        throws TorqueException, IllegalArgumentException
    {
    if (position == 0)
        {
            return setByName("NomPartie", value);
        }
    if (position == 1)
        {
            return setByName("NomObjet", value);
        }
    if (position == 2)
        {
            return setByName("CoordX", value);
        }
    if (position == 3)
        {
            return setByName("CoordY", value);
        }
        return false;
    }
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     *
     * @throws Exception
     */
    public void save() throws Exception
    {
        save(ObjetsPartiesPeer.DATABASE_NAME);
    }

    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     * Note: this code is here because the method body is
     * auto-generated conditionally and therefore needs to be
     * in this file instead of in the super class, BaseObject.
     *
     * @param dbName
     * @throws TorqueException
     */
    public void save(String dbName) throws TorqueException
    {
        Connection con = null;
        try
        {
            con = Transaction.begin(dbName);
            save(con);
            Transaction.commit(con);
        }
        catch(TorqueException e)
        {
            Transaction.safeRollback(con);
            throw e;
        }
    }

    /** flag to prevent endless save loop, if this object is referenced
        by another object which falls in this transaction. */
    private boolean alreadyInSave = false;
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.  This method
     * is meant to be used as part of a transaction, otherwise use
     * the save() method and the connection details will be handled
     * internally
     *
     * @param con
     * @throws TorqueException
     */
    public void save(Connection con) throws TorqueException
    {
        if (!alreadyInSave)
        {
            alreadyInSave = true;



            // If this object has been modified, then save it to the database.
            if (isModified())
            {
                if (isNew())
                {
                    ObjetsPartiesPeer.doInsert((ObjetsParties) this, con);
                    setNew(false);
                }
                else
                {
                    ObjetsPartiesPeer.doUpdate((ObjetsParties) this, con);
                }
            }

            alreadyInSave = false;
        }
    }



    private final SimpleKey[] pks = new SimpleKey[2];
    private final ComboKey comboPK = new ComboKey(pks);

    /**
     * Set the PrimaryKey with an ObjectKey
     *
     * @param key
     */
    public void setPrimaryKey(ObjectKey key) throws TorqueException
    {
        SimpleKey[] keys = (SimpleKey[]) key.getValue();
        setNomPartie(keys[0].toString());
        setNomObjet(keys[1].toString());
    }

    /**
     * Set the PrimaryKey using SimpleKeys.
     *
     * @param nomPartie String
     * @param nomObjet String
     */
    public void setPrimaryKey( String nomPartie, String nomObjet)
        throws TorqueException
    {
        setNomPartie(nomPartie);
        setNomObjet(nomObjet);
    }

    /**
     * Set the PrimaryKey using a String.
     */
    public void setPrimaryKey(String key) throws TorqueException
    {
        setPrimaryKey(new ComboKey(key));
    }

    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        pks[0] = SimpleKey.keyFor(getNomPartie());
        pks[1] = SimpleKey.keyFor(getNomObjet());
        return comboPK;
    }
 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public ObjetsParties copy() throws TorqueException
    {
        return copy(true);
    }

    /**
     * Makes a copy of this object using connection.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     *
     * @param con the database connection to read associated objects.
     */
    public ObjetsParties copy(Connection con) throws TorqueException
    {
        return copy(true, con);
    }

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * If the parameter deepcopy is true, it then fills all the
     * association collections and sets the related objects to
     * isNew=true.
     *
     * @param deepcopy whether to copy the associated objects.
     */
    public ObjetsParties copy(boolean deepcopy) throws TorqueException
    {
        return copyInto(new ObjetsParties(), deepcopy);
    }

    /**
     * Makes a copy of this object using connection.
     * It creates a new object filling in the simple attributes.
     * If the parameter deepcopy is true, it then fills all the
     * association collections and sets the related objects to
     * isNew=true.
     *
     * @param deepcopy whether to copy the associated objects.
     * @param con the database connection to read associated objects.
     */
    public ObjetsParties copy(boolean deepcopy, Connection con) throws TorqueException
    {
        return copyInto(new ObjetsParties(), deepcopy, con);
    }
  
    /**
     * Fills the copyObj with the contents of this object.
     * The associated objects are also copied and treated as new objects.
     *
     * @param copyObj the object to fill.
     */
    protected ObjetsParties copyInto(ObjetsParties copyObj) throws TorqueException
    {
        return copyInto(copyObj, true);
    }

  
    /**
     * Fills the copyObj with the contents of this object using connection.
     * The associated objects are also copied and treated as new objects.
     *
     * @param copyObj the object to fill.
     * @param con the database connection to read associated objects.
     */
    protected ObjetsParties copyInto(ObjetsParties copyObj, Connection con) throws TorqueException
    {
        return copyInto(copyObj, true, con);
    }
  
    /**
     * Fills the copyObj with the contents of this object.
     * If deepcopy is true, The associated objects are also copied
     * and treated as new objects.
     *
     * @param copyObj the object to fill.
     * @param deepcopy whether the associated objects should be copied.
     */
    protected ObjetsParties copyInto(ObjetsParties copyObj, boolean deepcopy) throws TorqueException
    {
        copyObj.setNomPartie(nomPartie);
        copyObj.setNomObjet(nomObjet);
        copyObj.setCoordX(coordX);
        copyObj.setCoordY(coordY);

        copyObj.setNomPartie((String)null);
        copyObj.setNomObjet((String)null);

        if (deepcopy)
        {
        }
        return copyObj;
    }
        
    
    /**
     * Fills the copyObj with the contents of this object using connection.
     * If deepcopy is true, The associated objects are also copied
     * and treated as new objects.
     *
     * @param copyObj the object to fill.
     * @param deepcopy whether the associated objects should be copied.
     * @param con the database connection to read associated objects.
     */
    protected ObjetsParties copyInto(ObjetsParties copyObj, boolean deepcopy, Connection con) throws TorqueException
    {
        copyObj.setNomPartie(nomPartie);
        copyObj.setNomObjet(nomObjet);
        copyObj.setCoordX(coordX);
        copyObj.setCoordY(coordY);

        copyObj.setNomPartie((String)null);
        copyObj.setNomObjet((String)null);

        if (deepcopy)
        {
        }
        return copyObj;
    }
    
    

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public ObjetsPartiesPeer getPeer()
    {
        return peer;
    }

    /**
     * Retrieves the TableMap object related to this Table data without
     * compiler warnings of using getPeer().getTableMap().
     *
     * @return The associated TableMap object.
     */
    public TableMap getTableMap() throws TorqueException
    {
        return ObjetsPartiesPeer.getTableMap();
    }


    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("ObjetsParties:\n");
        str.append("NomPartie = ")
           .append(getNomPartie())
           .append("\n");
        str.append("NomObjet = ")
           .append(getNomObjet())
           .append("\n");
        str.append("CoordX = ")
           .append(getCoordX())
           .append("\n");
        str.append("CoordY = ")
           .append(getCoordY())
           .append("\n");
        return(str.toString());
    }
}
