package view.popup;


import controller.admin.ApproveController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;
import view.material.FlatButton;
import view.material.MaterialTextField;
import view.material.ProgressCircle;

import java.io.NotActiveException;

public class ApprovalPopup extends PopupDecorator {

    private CreaPacchettoEntity pacchetto;
    private TableView<ProdottoEntity> list;
    private EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            int id = Integer.parseInt(((Button) event.getSource()).getId());

            if (id == 2) {
                Button done = new FlatButton("Conferma");
                TextField field = new MaterialTextField();
                field.setPromptText("Inserisci motivazione...");
                changeDecoration(new HBox(field, done));
                done.setOnMouseClicked(e -> { pacchetto.setMotivazione(field.getText()); call(pacchetto, id); });
            }
            else call(pacchetto, id);
        }

        private void call(CreaPacchettoEntity entity, int id) {
            changeDecoration(ProgressCircle.miniCircle());
            new Thread(() -> {
                boolean result = ApproveController.handle(entity, id);
                if (result) Platform.runLater(() -> Notifications.create().text("Operazione completata").show());
                else Platform.runLater(() -> Notifications.create().text("Errore interno").showError());
                Platform.runLater(() -> {
                    parent.hide();
                    list.getItems().remove(entity);
                });
            }).start();
        }
    };

    public ApprovalPopup(PopupView popupView, CreaPacchettoEntity pacchetto, TableView<ProdottoEntity> list){
        super(popupView);
        this.pacchetto = pacchetto;
        this.list = list;
    }

    @Override
    protected Node decorate() {

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setPadding(new Insets(0, 0, 25, 0));
        Button approveButton = new FlatButton("Approva");
        Button modifyButton = new FlatButton("Respingi");
        Button deleteButton = new FlatButton("Cancella");

        approveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        modifyButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);
        deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);

        approveButton.setId("1");
        modifyButton.setId("2");
        deleteButton.setId("0");

        pane.add(approveButton, 0, 0);
        pane.add(modifyButton, 1, 0);
        pane.add(deleteButton, 2, 0);

        pane.setAlignment(Pos.CENTER);
        pane.setHgap(4);

        return pane;
    }
}
