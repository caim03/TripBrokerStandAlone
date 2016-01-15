package model.entityDB;

/*** AbstractEntity class is used to implement factory method and polymorphism between DataBase entity;
 *   it allows to use DAO Interface in dynamic way, because the all entity classes are united among themselves
 *   and DAO Interface by this super class
 *   This class also implements Cloneable to clone objects and using pattern prototype;
 *   this pattern allows to create new objects by cloning an initial objects, called prototype.
 *   Unlike other patterns as FactoryMethod, it allows to create new objects at run-time,
 *   using a prototype manager to saving and retrieving dynamically the instances of guessed objects ***/

public class AbstractEntity implements Cloneable {

    private boolean valid = true;
    private static AbstractEntity invalidOne = new AbstractEntity(false);

    public AbstractEntity() {}

    private AbstractEntity(boolean valid) {
        /** @param boolean; this param validates or invalidates the entity **/

        this.valid = valid;
    }

    public AbstractEntity clone() {
        /** @return AbstractEntity; return the cloned entity **/

        /*PATTERN PROTOTYPE*/

        try {
            return (AbstractEntity) super.clone();
        }
        catch (CloneNotSupportedException ignore) { return null; }
    }

    public static AbstractEntity getInvalidEntity() {

        return invalidOne.clone();
    }

    public boolean isValid() {
        /** @return boolean; return true if entity is valid, false otherwise **/

        return valid;
    }
}
