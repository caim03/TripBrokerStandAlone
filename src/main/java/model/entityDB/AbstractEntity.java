package model.entityDB;

import javafx.scene.Scene;

public class AbstractEntity implements Cloneable {

    private boolean valid = true;
    private static AbstractEntity invalidOne = new AbstractEntity(false);

    public AbstractEntity() {}
    private AbstractEntity(boolean valid) { this.valid = valid; }

    public AbstractEntity clone() {

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
        return valid;
    }
}
