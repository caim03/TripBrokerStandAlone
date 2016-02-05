package view.agent;

import controller.Constants;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.entityDB.ViaggioGruppoEntity;
import view.material.DBListView;
import view.material.LayerPane;
import view.material.MaterialPopup;
import view.popup.BookingListPopup;

public class ManageBookingView extends  LayerPane {

    public ManageBookingView() {

        DBListView list = new DBListView("from ViaggioGruppoEntity v where v.prenotazioni > 0 and v.prenotazioni + v.acquisti > (select valore from PoliticheEntity where id = " + Constants.minGroup + ")");
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null || newValue.equals(oldValue)) return;

            new MaterialPopup(
                    ManageBookingView.this,
                    new BookingListPopup((ViaggioGruppoEntity)newValue))
                    .show();
        });

        GridPane.setHgrow(list, Priority.ALWAYS);
        GridPane.setVgrow(list, Priority.ALWAYS);
        GridPane container = new GridPane();
        container.getChildren().add(list);

        attach(container);

        list.fill();
    }
}
