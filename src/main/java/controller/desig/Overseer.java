package controller.desig;

import javafx.collections.ListChangeListener;
import model.entityDB.AbstractEntity;

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
