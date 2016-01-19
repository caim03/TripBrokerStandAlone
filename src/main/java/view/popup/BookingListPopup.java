package view.popup;

import javafx.scene.control.ListView;
import model.entityDB.AbstractEntity;
import model.entityDB.ViaggioGruppoEntity;
import view.material.DBListView;

public class BookingListPopup extends PopupView {

    private ViaggioGruppoEntity entity;

    public BookingListPopup(ViaggioGruppoEntity entity) {

        this.entity = entity;
    }

    @Override
    protected ListView<? extends AbstractEntity> generatePopup() {

        DBListView list = new DBListView("from PrenotazioneEntity where vid = " + entity.getId());
        list.fill();

        return list;
    }
}
