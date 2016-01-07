package view;

import controller.DeleteButtonController;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import model.entityDB.DipendentiEntity;
import view.material.MaterialPopup;
import view.popup.EmployeePopup;
import view.popup.PopupView;


public class ButtonCell extends TableCell<DipendentiEntity, Boolean> {
    final private Button cellButton;
    private DBTablePane pane;
    private String type;

    public ButtonCell(String type, DBTablePane pane){
        cellButton = new Button(type);
        this.pane = pane;
        this.type = type;
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty){
            setGraphic(cellButton);

            if (type.equals("modify"))
                cellButton.setOnMouseClicked(event -> {

                    DipendentiEntity entity = (DipendentiEntity) getTableRow().getItem();
                    PopupView popupView = new EmployeePopup(getTableView(), entity, getTableRow().getIndex());
                    new MaterialPopup(pane, popupView).show();
                });

            else {
                DipendentiEntity entity = (DipendentiEntity) getTableRow().getItem();
                cellButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new DeleteButtonController(getTableView(), entity));
            }
        }
        else{
            setGraphic(null);
        }
    }
}