package view;

import controller.DeleteButtonController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;
import view.material.FlatButton;
import view.material.MaterialPopup;
import view.material.ProgressCircle;
import view.popup.EmployeePopup;
import view.popup.PopupView;


public class ButtonCell extends TableCell<DipendentiEntity, Boolean> {
    final private Button cellButton;
    private DBTablePane pane;
    private String type;
    private HBox cell;

    public ButtonCell(String type, DBTablePane pane){
        /** @param String; string that appears in the button
         *  @param DBTablePane; table that contains this button cell
         *  @return ButtonCell; return the button cell **/

        // instantiate the button as FlatButton
        cellButton = new FlatButton(type);
        this.pane = pane;
        this.type = type;
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty) {

            cell = new HBox(cellButton);
            setGraphic(cell);

            // if modify button
            if (type.equals("modify"))
                cellButton.setOnMouseClicked(event -> {

                    DipendentiEntity entity = (DipendentiEntity) getTableRow().getItem();
                    PopupView popupView = new EmployeePopup(getTableView(), entity, getTableRow().getIndex());
                    new MaterialPopup(pane, popupView).show();
                });

            // if delete button
            else {

                DipendentiEntity entity = (DipendentiEntity) getTableRow().getItem();
                cellButton.addEventFilter(MouseEvent.MOUSE_CLICKED,
                        event -> {

                            ProgressCircle mini = ProgressCircle.miniCircle();
                            cell.getChildren().add(mini);

                            new Thread(() -> {

                                DeleteButtonController.handle(getTableView(), entity);

                                Platform.runLater(() -> {
                                    Notifications.create().title("Cancellato").text("Il dipendente è stato cancellato con successo").show();
                                    getTableView().getItems().remove(entity);
                                    getTableView().refresh();
                                    cell.getChildren().remove(mini);
                                });
                            }).start();
                        });
            }
        }

        else setGraphic(null);
    }
}