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
 * types dobjet
 *
 * This class was autogenerated by Torque on:
 *
 * [Thu Mar 15 14:33:14 CET 2012]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to Types
 */
public abstract class BaseTypes extends BaseObject
{
    /** Serial version */
    private static final long serialVersionUID = 1331818394632L;

    /** The Peer class */
    private static final TypesPeer peer =
        new TypesPeer();


    /** The value for the nom field */
    private String nom;


    /**
     * Get the Nom
     *
     * @return String
     */
    public String getNom()
    {
        return nom;
    }


    /**
     * Set the value of Nom
     *
     * @param v new value
     */
    public void setNom(String v) throws TorqueException
    {

        if (!ObjectUtils.equals(this.nom, v))
        {
            this.nom = v;
            setModified(true);
        }



        // update associated Objets
        if (collObjetss != null)
        {
            for (int i = 0; i < collObjetss.size(); i++)
            {
                ((Objets) collObjetss.get(i))
                        .setType(v);
            }
        }
    }

       


    /**
     * Collection to store aggregation of collObjetss
     */
    protected List collObjetss;

    /**
     * Temporary storage of collObjetss to save a possible db hit in
     * the event objects are add to the collection, but the
     * complete collection is never requested.
     */
    protected void initObjetss()
    {
        if (collObjetss == null)
        {
            collObjetss = new ArrayList();
        }
    }


    /**
     * Method called to associate a Objets object to this object
     * through the Objets foreign key attribute
     *
     * @param l Objets
     * @throws TorqueException
     */
    public void addObjets(Objets l) throws TorqueException
    {
        getObjetss().add(l);
        l.setTypes((Types) this);
    }

    /**
     * Method called to associate a Objets object to this object
     * through the Objets foreign key attribute using connection.
     *
     * @param l Objets
     * @throws TorqueException
     */
    public void addObjets(Objets l, Connection con) throws TorqueException
    {
        getObjetss(con).add(l);
        l.setTypes((Types) this);
    }

    /**
     * The criteria used to select the current contents of collObjetss
     */
    private Criteria lastObjetssCriteria = null;

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getObjetss(new Criteria())
     *
     * @return the collection of associated objects
     * @throws TorqueException
     */
    public List getObjetss()
        throws TorqueException
    {
        if (collObjetss == null)
        {
            collObjetss = getObjetss(new Criteria(10));
        }
        return collObjetss;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Types has previously
     * been saved, it will retrieve related Objetss from storage.
     * If this Types is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     *
     * @throws TorqueException
     */
    public List getObjetss(Criteria criteria) throws TorqueException
    {
        if (collObjetss == null)
        {
            if (isNew())
            {
               collObjetss = new ArrayList();
            }
            else
            {
                criteria.add(ObjetsPeer.TYPE, getNom() );
                collObjetss = ObjetsPeer.doSelect(criteria);
            }
        }
        else
        {
            // criteria has no effect for a new object
            if (!isNew())
            {
                // the following code is to determine if a new query is
                // called for.  If the criteria is the same as the last
                // one, just return the collection.
                criteria.add(ObjetsPeer.TYPE, getNom());
                if (!lastObjetssCriteria.equals(criteria))
                {
                    collObjetss = ObjetsPeer.doSelect(criteria);
                }
            }
        }
        lastObjetssCriteria = criteria;

        return collObjetss;
    }

    /**
     * If this collection has already been initialized, returns
     * the collection. Otherwise returns the results of
     * getObjetss(new Criteria(),Connection)
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getObjetss(Connection con) throws TorqueException
    {
        if (collObjetss == null)
        {
            collObjetss = getObjetss(new Criteria(10), con);
        }
        return collObjetss;
    }

    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Types has previously
     * been saved, it will retrieve related Objetss from storage.
     * If this Types is new, it will return
     * an empty collection or the current collection, the criteria
     * is ignored on a new object.
     * This method takes in the Connection also as input so that
     * referenced objects can also be obtained using a Connection
     * that is taken as input
     */
    public List getObjetss(Criteria criteria, Connection con)
            throws TorqueException
    {
        if (collObjetss == null)
        {
            if (isNew())
            {
               collObjetss = new ArrayList();
            }
            else
            {
                 criteria.add(ObjetsPeer.TYPE, getNom());
                 collObjetss = ObjetsPeer.doSelect(criteria, con);
             }
         }
         else
         {
             // criteria has no effect for a new object
             if (!isNew())
             {
                 // the following code is to determine if a new query is
                 // called for.  If the criteria is the same as the last
                 // one, just return the collection.
                 criteria.add(ObjetsPeer.TYPE, getNom());
                 if (!lastObjetssCriteria.equals(criteria))
                 {
                     collObjetss = ObjetsPeer.doSelect(criteria, con);
                 }
             }
         }
         lastObjetssCriteria = criteria;

         return collObjetss;
     }











    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Types is new, it will return
     * an empty collection; or if this Types has previously
     * been saved, it will retrieve related Objetss from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Types.
     */
    protected List getObjetssJoinCaracteristiques(Criteria criteria)
        throws TorqueException
    {
        if (collObjetss == null)
        {
            if (isNew())
            {
               collObjetss = new ArrayList();
            }
            else
            {
                criteria.add(ObjetsPeer.TYPE, getNom());
                collObjetss = ObjetsPeer.doSelectJoinCaracteristiques(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            criteria.add(ObjetsPeer.TYPE, getNom());
            if (!lastObjetssCriteria.equals(criteria))
            {
                collObjetss = ObjetsPeer.doSelectJoinCaracteristiques(criteria);
            }
        }
        lastObjetssCriteria = criteria;

        return collObjetss;
    }









    /**
     * If this collection has already been initialized with
     * an identical criteria, it returns the collection.
     * Otherwise if this Types is new, it will return
     * an empty collection; or if this Types has previously
     * been saved, it will retrieve related Objetss from storage.
     *
     * This method is protected by default in order to keep the public
     * api reasonable.  You can provide public methods for those you
     * actually need in Types.
     */
    protected List getObjetssJoinTypes(Criteria criteria)
        throws TorqueException
    {
        if (collObjetss == null)
        {
            if (isNew())
            {
               collObjetss = new ArrayList();
            }
            else
            {
                criteria.add(ObjetsPeer.TYPE, getNom());
                collObjetss = ObjetsPeer.doSelectJoinTypes(criteria);
            }
        }
        else
        {
            // the following code is to determine if a new query is
            // called for.  If the criteria is the same as the last
            // one, just return the collection.
            criteria.add(ObjetsPeer.TYPE, getNom());
            if (!lastObjetssCriteria.equals(criteria))
            {
                collObjetss = ObjetsPeer.doSelectJoinTypes(criteria);
            }
        }
        lastObjetssCriteria = criteria;

        return collObjetss;
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
            fieldNames.add("Nom");
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
        if (name.equals("Nom"))
        {
            return getNom();
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
        if (name.equals("Nom"))
        {
            // Object fields can be null
            if (value != null && ! String.class.isInstance(value))
            {
                throw new IllegalArgumentException("Invalid type of object specified for value in setByName");
            }
            setNom((String) value);
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
        if (name.equals(TypesPeer.NOM))
        {
            return getNom();
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
      if (TypesPeer.NOM.equals(name))
        {
            return setByName("Nom", value);
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
            return getNom();
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
            return setByName("Nom", value);
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
        save(TypesPeer.DATABASE_NAME);
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
                    TypesPeer.doInsert((Types) this, con);
                    setNew(false);
                }
                else
                {
                    TypesPeer.doUpdate((Types) this, con);
                }
            }


            if (collObjetss != null)
            {
                for (int i = 0; i < collObjetss.size(); i++)
                {
                    ((Objets) collObjetss.get(i)).save(con);
                }
            }
            alreadyInSave = false;
        }
    }


    /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param key nom ObjectKey
     */
    public void setPrimaryKey(ObjectKey key)
        throws TorqueException
    {
        setNom(key.toString());
    }

    /**
     * Set the PrimaryKey using a String.
     *
     * @param key
     */
    public void setPrimaryKey(String key) throws TorqueException
    {
        setNom(key);
    }


    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
        return SimpleKey.keyFor(getNom());
    }
 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
     * It then fills all the association collections and sets the
     * related objects to isNew=true.
     */
    public Types copy() throws TorqueException
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
    public Types copy(Connection con) throws TorqueException
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
    public Types copy(boolean deepcopy) throws TorqueException
    {
        return copyInto(new Types(), deepcopy);
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
    public Types copy(boolean deepcopy, Connection con) throws TorqueException
    {
        return copyInto(new Types(), deepcopy, con);
    }
  
    /**
     * Fills the copyObj with the contents of this object.
     * The associated objects are also copied and treated as new objects.
     *
     * @param copyObj the object to fill.
     */
    protected Types copyInto(Types copyObj) throws TorqueException
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
    protected Types copyInto(Types copyObj, Connection con) throws TorqueException
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
    protected Types copyInto(Types copyObj, boolean deepcopy) throws TorqueException
    {
        copyObj.setNom(nom);

        copyObj.setNom((String)null);

        if (deepcopy)
        {


        List vObjetss = getObjetss();
        if (vObjetss != null)
        {
            for (int i = 0; i < vObjetss.size(); i++)
            {
                Objets obj = (Objets) vObjetss.get(i);
                copyObj.addObjets(obj.copy());
            }
        }
        else
        {
            copyObj.collObjetss = null;
        }
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
    protected Types copyInto(Types copyObj, boolean deepcopy, Connection con) throws TorqueException
    {
        copyObj.setNom(nom);

        copyObj.setNom((String)null);

        if (deepcopy)
        {


        List vObjetss = getObjetss(con);
        if (vObjetss != null)
        {
            for (int i = 0; i < vObjetss.size(); i++)
            {
                Objets obj = (Objets) vObjetss.get(i);
                copyObj.addObjets(obj.copy(con), con);
            }
        }
        else
        {
            copyObj.collObjetss = null;
        }
        }
        return copyObj;
    }
    
    

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public TypesPeer getPeer()
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
        return TypesPeer.getTableMap();
    }


    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("Types:\n");
        str.append("Nom = ")
           .append(getNom())
           .append("\n");
        return(str.toString());
    }
}
