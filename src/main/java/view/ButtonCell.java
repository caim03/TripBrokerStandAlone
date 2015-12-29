package view;


import controller.DeleteButtonController;
import controller.ModifyButtonController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import model.entityDB.DipendentiEntity;

public class ButtonCell extends TableCell<DipendentiEntity, Boolean> {
    final Button cellButton;
    private TableView<DipendentiEntity> list;
    private DBTablePane pane;

    public ButtonCell(String type, TableView<DipendentiEntity> list, DBTablePane pane){
        cellButton = new Button(type);
        this.list = list;
        this.pane = pane;

        if (type.equals("modify")){
            cellButton.setOnMouseClicked(new ModifyButtonController(list, pane));
        }

        else{
            cellButton.setOnMouseClicked(new DeleteButtonController(list));
        }
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(cellButton);
        }
    }
}