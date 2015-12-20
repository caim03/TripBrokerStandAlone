package view.agent;

import javafx.scene.layout.VBox;
import model.dao.ViaggioGruppoDaoHibernate;
import view.material.DBListView;

public class DeleteGroupTripView extends VBox {

    DBListView list;

    public DeleteGroupTripView() {

        list = new DBListView("from ViaggioGruppoEntity");
        list.fill();

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ViaggioGruppoDaoHibernate.instance().delete(newValue); });

        getChildren().add(list);
    }
}
