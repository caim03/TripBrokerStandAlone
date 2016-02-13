package view.popup;

import javafx.scene.control.ListView;
import model.entityDB.AbstractEntity;
import model.entityDB.ViaggioGruppoEntity;
import view.material.DBListView;

public class BookingListPopup extends PopupView {

    private ViaggioGruppoEntity entity;
    private ListView list;

    public BookingListPopup(ViaggioGruppoEntity entity) { this.entity = entity; }

    @Override
    protected ListView<? extends AbstractEntity> generatePopup() {

        list = new DBListView("from PrenotazioneEntity where vid = " + entity.getId());
        list.refresh();

        return list;
    }
}
