package view.popup;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.entityDB.AbstractEntity;
import model.entityDB.ViaggioGruppoEntity;
import view.material.DBListView;
import view.material.MaterialPopup;

public class BookingListPopup extends PopupView {

    private ViaggioGruppoEntity entity;
    private ListView list;

    public BookingListPopup(ViaggioGruppoEntity entity) {

        this.entity = entity;
    }

    @Override
    protected ListView<? extends AbstractEntity> generatePopup() {

        list = new DBListView("from PrenotazioneEntity where vid = " + entity.getId());
        list.refresh();

        return list;
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        list.getItems().addListener((ListChangeListener) c -> {
            c.next();
            if (c.getList().size() == 0) BookingListPopup.this.parent.hide();
        });
    }
}
