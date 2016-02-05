package view.popup;

import controller.Constants;
import controller.admin.ModifyPoliticsController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.PoliticheEntity;
import org.controlsfx.control.Notifications;
import view.material.FlatButton;
import view.material.MaterialPopup;
import view.material.NumericField;
import view.material.ProgressCircle;


public class PoliticsPopup extends PopupView {

    private PoliticheEntity entity;
    private NumericField field;
    private Button modButton;
    private GridPane pane;
    private TableView table;

    public PoliticsPopup(PoliticheEntity entity) { this.entity = entity; }

    public PoliticsPopup(PoliticheEntity entity, TableView table) {
        this.entity = entity;
        this.table = table;
    }

    @Override
    protected Parent generatePopup() {

        Label politic = new Label(entity.getNome());
        Label description = new Label(entity.getDescrizione());

        field = new NumericField(false);
        field.setText(entity.toString());
        field.setPromptText("Inserisci il nuovo valore");

        modButton = new FlatButton("Modifica");

        pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(politic, 0, 0);
        pane.add(description, 0, 1);
        pane.add(field, 0, 2);
        if (entity.getId() != Constants.minGroup) pane.add(new Label("%"), 1, 2);
        pane.add(modButton, 1, 3);

        return new VBox(40, pane);
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        modButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

            ProgressCircle circle = ProgressCircle.miniCircle();
            pane.getChildren().remove(modButton);
            pane.add(circle, 1, 3);

            new Thread(() -> {
                PoliticheEntity clone = (PoliticheEntity) entity.clone();
                clone.setValore(field.getNumber());

                boolean result = false;
                try {
                    result = ModifyPoliticsController.handle(clone);
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        Notifications.create().text(e.getMessage()).showWarning();
                        pane.getChildren().remove(circle);
                        pane.add(modButton, 1, 3);
                    });
                    return;
                }

                if (result)
                    Platform.runLater(() -> {
                        Notifications.
                                create().
                                text("Aggiornamento effettuato con successo").
                                show();
                        table.getItems().set(table.getItems().indexOf(entity), clone);
                    });

                else Platform.runLater(() ->
                        Notifications.
                                create().
                                text("Aggiornamento fallito").
                                showError());
                Platform.runLater(parent::hide);
            }).start();
        });
    }
}
