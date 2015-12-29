package controller;


import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.entityDB.DipendentiEntity;
import view.DBTablePane;
import view.material.MaterialPopup;
import view.popup.EmployeePopup;
import view.popup.PopupView;

public class ModifyButtonController implements EventHandler<MouseEvent>{
    private TableView<DipendentiEntity> list;
    private DBTablePane pane;

    public ModifyButtonController(TableView<DipendentiEntity> list, DBTablePane pane){
        this.list = list;
        this.pane = pane;
    }

    @Override
    public void handle(MouseEvent event) {
        DipendentiEntity entity = list.getSelectionModel().getSelectedItem();
        //PopupView popupView = new EmployeePopup(list);

        //new MaterialPopup(pane, popupView).show();
    }
}
