package view.agent;

import controller.agent.DeleteGroupTripController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.DBManager;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.ViaggioGruppoEntity;
import org.controlsfx.control.Notifications;
import view.material.*;
import view.popup.PopupView;

public class DeleteGroupTripView extends LayerPane {

    private DBListView list;

    public DeleteGroupTripView() {

        list = new DBListView("from ViaggioGruppoEntity");
        list.fill();

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            list.getSelectionModel().clearSelection();
            if (newValue == null) return;

            PopupView confirmPopup = new ConfirmPopup((ViaggioGruppoEntity) newValue);
            new MaterialPopup(this, confirmPopup).show();
        });

        attach(list);
    }

    private class ConfirmPopup extends PopupView {

        private VBox pane;
        private Button confirm, cancel;
        private ViaggioGruppoEntity entity;
        private HBox buttons;

        private ConfirmPopup(ViaggioGruppoEntity entity) { this.entity = entity; }

        @Override
        protected Parent generatePopup() {
            confirm = new FlatButton("Conferma");
            cancel = new FlatButton("Annulla");
            Pane emptyBox = new Pane();

            buttons = new HBox(emptyBox, confirm, cancel);
            HBox.setHgrow(pane, Priority.ALWAYS);

            Label label = new Label("Confermi l'eliminazione del viaggio di gruppo?");
            buttons.maxWidthProperty().bind(label.prefWidthProperty());

            pane = new VBox(label, buttons);

            return pane;
        }

        @Override
        public void setParent(MaterialPopup parent) {
            super.setParent(parent);

            parent.parentProperty().
                    addListener((observable, oldValue, newValue) -> {
                        if (newValue == null) return;

                        confirm.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
                            buttons.getChildren().remove(confirm);
                            buttons.getChildren().remove(cancel);
                            buttons.getChildren().add(ProgressCircle.miniCircle());
                            new Thread(() -> {
                                boolean result = DeleteGroupTripController.handle(entity);
                                if (result)
                                    Platform.runLater(() -> {
                                        parent.hide();
                                        Notifications.
                                                create().
                                                text("Viaggio di gruppo cancellato").
                                                show();
                                        list.getItems().remove(entity);
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
            });
        }
    }
}
