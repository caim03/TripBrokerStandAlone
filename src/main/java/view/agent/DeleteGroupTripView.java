package view.agent;

import javafx.scene.layout.VBox;
import model.DBManager;
import model.dao.ViaggioGruppoDaoHibernate;
import view.material.DBListView;

public class DeleteGroupTripView extends VBox {

    DBListView list;

    public DeleteGroupTripView() {

        list = new DBListView("from ViaggioGruppoEntity");
        list.fill();

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            DBManager.initHibernate();
            ViaggioGruppoDaoHibernate.instance().delete(newValue);
            DBManager.shutdown();
        });

        getChildren().add(list);
    }
}
