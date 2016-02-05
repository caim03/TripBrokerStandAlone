package view.agent;

import model.entityDB.ViaggioGruppoEntity;
import view.material.DBListView;
import view.material.LayerPane;
import view.material.MaterialPopup;
import view.popup.BookingPopup;
import view.popup.GroupTripPopup;
import view.popup.PopupView;

public class GroupTripBookingView extends LayerPane {

    DBListView list;

    public GroupTripBookingView() {

        list = new DBListView("from ViaggioGruppoEntity entity where entity.prenotazioni < entity.max");
        list.fill();
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null) return;
            PopupView popupView = new BookingPopup((ViaggioGruppoEntity) newValue);
            new MaterialPopup(this, popupView).show();
        });

        attach(list);
    }
}
