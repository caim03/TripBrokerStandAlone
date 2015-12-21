package view.agent;

import javafx.scene.layout.VBox;
import model.entityDB.ViaggioGruppoEntity;
import view.popup.BookingPopup;
import view.popup.GroupTripPopup;
import view.material.DBListView;

public class GroupTripBookingView extends VBox {

    DBListView list;

    public GroupTripBookingView() {

        list = new DBListView("from ViaggioGruppoEntity entity where entity.prenotazioni < entity.max");
        list.fill();
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null) return;
            new BookingPopup(new GroupTripPopup((ViaggioGruppoEntity) newValue), (ViaggioGruppoEntity) newValue).show();
        });

        getChildren().add(list);
    }
}
