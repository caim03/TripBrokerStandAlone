package view;

import controller.admin.DeleteButtonController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.entityDB.DipendenteEntity;
import org.controlsfx.control.Notifications;
import view.material.FlatButton;
import view.material.LayerPane;
import view.material.MaterialPopup;
import view.material.ProgressCircle;
import view.popup.EmployeePopup;
import view.popup.PopupView;


public class ButtonCell extends TableCell<DipendenteEntity, Boolean> {
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
            if (type.equals("Modifica"))
                cellButton.setOnMouseClicked(event -> {
                    DipendenteEntity entity = (DipendenteEntity) getTableRow().getItem();
                    PopupView popupView = new EmployeePopup(getTableView(), entity);
                    new MaterialPopup(pane, popupView).show();
                });

            // if delete button
            else {

                DipendenteEntity entity = (DipendenteEntity) getTableRow().getItem();
                cellButton.setOnMouseClicked(event -> {
                    PopupView confirmPopup = new ConfirmPopup(entity);
                    new MaterialPopup((LayerPane) getTableView().getParent().getParent(), confirmPopup).show();
                });
            }
        }

        else setGraphic(null);
    }

    private class ConfirmPopup extends PopupView {

        private VBox pane;
        private Button confirm, cancel;
        private DipendenteEntity entity;
        private HBox buttons;

        private ConfirmPopup(DipendenteEntity entity) {
            this.entity = entity;
        }

        @Override
        protected Parent generatePopup() {
            confirm = new FlatButton("Conferma");
            cancel = new FlatButton("Annulla");

            confirm.setOnMouseClicked(event -> {
                buttons.getChildren().remove(confirm);
                buttons.getChildren().remove(cancel);
                buttons.getChildren().add(ProgressCircle.miniCircle());
                new Thread(() -> {
                    boolean result = DeleteButtonController.handle(entity);
                    if (result)
                        Platform.runLater(() -> {
                            parent.hide();
                            Notifications.
                                    create().
                                    text("Dipendente cancellato").
                                    show();
                            getTableView().getItems().remove(entity);
                        });
                    else
                        Platform.runLater(() -> {
                            parent.hide();
                            Notifications.
                                    create().
                                    text("Errore durante la cancellazione").
                                    showError();
                        });
                }).start();
            });
            cancel.setOnMouseClicked(event -> Platform.runLater(parent::hide));

            Pane emptyBox = new Pane();

            buttons = new HBox(emptyBox, confirm, cancel);
            HBox.setHgrow(emptyBox, Priority.ALWAYS);

            Label label = new Label("Confermi l'eliminazione del dipendente?");
            buttons.maxWidthProperty().bind(label.prefWidthProperty());

            pane = new VBox(label, buttons);
            pane.setStyle("-fx-background-color: white");
            pane.setPadding(new Insets(16));

            return pane;
        }
    }
}