package controller.desig;

import javafx.collections.ListChangeListener;
import model.entityDB.AbstractEntity;

/**
 * ListChangeListener abstract implementation; it coordinates list addition and eventually
 * rejects list updates if not coherent.
 */
public abstract class Overseer implements ListChangeListener<AbstractEntity> {

    @Override
    public void onChanged(Change<? extends AbstractEntity> c) {
        c.next();
        if (c.wasAdded()) checkAdded(c);
        else checkRemoved(c);
    }

    protected abstract void checkAdded(Change<? extends AbstractEntity> c);
    protected abstract void checkRemoved(Change<? extends AbstractEntity> c);
}
