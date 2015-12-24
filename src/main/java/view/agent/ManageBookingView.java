package view.agent;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import model.entityDB.ViaggioGruppoEntity;
import view.material.DBListView;
import view.material.LayerPane;
import view.material.MaterialPopup;
import view.popup.BookingListPopup;

public class ManageBookingView extends  LayerPane {

    public ManageBookingView() {

        DBListView list = new DBListView("from ViaggioGruppoEntity where prenotazioni > 0");
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null || newValue.equals(oldValue)) return;
            System.out.println("SELECTED");
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
