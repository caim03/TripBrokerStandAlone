package controller;


import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.entityDB.DipendentiEntity;
import view.DBTablePane;

public class ManageRolesController implements EventHandler<MouseEvent>{
    TableView<DipendentiEntity> list;
    DBTablePane pane;

    public ManageRolesController(TableView<DipendentiEntity> list, DBTablePane pane){
        this.list = list;
        this.pane = pane;
    }

    @Override
    public void handle(MouseEvent event) {
        DipendentiEntity entity = list.getSelectionModel().getSelectedItem();

        if (entity == null){
            return;
        }
    }
}
