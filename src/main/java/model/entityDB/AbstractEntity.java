package model.entityDB;

/***
 * This class represents the base class for all entities in the Hibernate Database.
 * Its main purpose is to ensures generalization between DB entities, joined together as they have a
 * common reason of being; no to-be-overridden methods are provided because AbstractEntities are not
 * actual DB entities and do not track any table.
 * This class also implements Cloneable in order to enable self-cloning and implement the Prototype pattern;
 * this pattern allows to create new objects by cloning a previously instantiated objects, called a prototype.
 * It creates new objects at run-time, using a prototype manager for dynamically saving and
 * retrieving of instances of default objects ***/

//TODO is Prototype pattern necessary?

public class AbstractEntity implements Cloneable {

    private boolean valid = true;

     //Invalid entities are used as alternative ways of return-based controls
    private static AbstractEntity invalidOne = new AbstractEntity(false);

    /***
     * Protected/private constructors; this class should be abstract, but in order to implement
     * the Prototype pattern it has to be instantiable. Unaccessible constructor impede direct instantiation
     * of this class.
     */
    protected AbstractEntity() {}
    private AbstractEntity(boolean valid) {
        /** @param boolean; this param validates or invalidates the entity **/
        this.valid = valid;
    }

    public AbstractEntity clone() {
        /** @return AbstractEntity; cloned entity **/
        /*PATTERN PROTOTYPE*/
        try {
            return (AbstractEntity) super.clone();
        }
        catch (CloneNotSupportedException ignore) { return null; }
    }

    public static AbstractEntity getInvalidEntity() { return invalidOne.clone(); }

    public boolean isValid() {
        /** @return boolean; return true if entity is valid, false otherwise **/
        return valid;
    }
}
