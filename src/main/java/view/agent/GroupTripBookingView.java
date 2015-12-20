package view.agent;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.entityDB.AbstractEntity;
import view.material.DBListView;

public class GroupTripBookingView extends VBox {

    DBListView list;

    public GroupTripBookingView() {

        list = new DBListView("WHERE prenotazioni < max");
        list.fill();

        getChildren().add(list);
    }
}
