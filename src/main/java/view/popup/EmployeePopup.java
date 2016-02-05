package view.popup;

import controller.admin.ModifyEmployeeController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;
import view.material.*;

public class EmployeePopup extends PopupView{

    private static final ObservableList<String> ROLES = FXCollections.observableArrayList("Agente", "Amministratore", "Designer", "Scout");

    private TableView list;
    private DipendentiEntity entity;
    private Button modButton;
    private TextField nameTxt, surnameTxt, passTxt, mailTxt;
    private MaterialSpinner roleSpinner;
    private GridPane pane;

    public EmployeePopup(TableView list, DipendentiEntity entity) {
        this.entity = entity;
        this.list = list;
    }

    @Override
    protected Parent generatePopup() {

        nameTxt = new MaterialTextField();
        surnameTxt = new MaterialTextField();
        passTxt = new MaterialTextField();
        mailTxt = new MaterialTextField();

        nameTxt.setText(entity.getNome());
        surnameTxt.setText(entity.getCognome());
        passTxt.setText(entity.getPasswordLogin());
        mailTxt.setText(entity.getMail());

        modButton = new FlatButton("Conferma");

        nameTxt.setPromptText("Modifica nome");
        surnameTxt.setPromptText("Modifica cognome");
        passTxt.setPromptText("Modifica password");
        mailTxt.setPromptText("Modifica email");

        pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25));

        pane.add(new Label("Nome:"), 0, 0);
        pane.add(new Label("Cognome:"), 0, 1);
        pane.add(new Label("Ruolo:"), 0, 2);
        pane.add(new Label("Password:"), 0, 3);
        pane.add(new Label("Mail"), 0, 4);

        pane.add(nameTxt, 1, 0);
        pane.add(surnameTxt, 1, 1);
        pane.add(passTxt, 1, 3);
        pane.add(mailTxt, 1, 4);

        pane.add(modButton, 0, 5);

        return new VBox(40, pane);
    }

    @Override
    public void setParent(MaterialPopup parent) {
        super.setParent(parent);

        this.parent.parentProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {

                roleSpinner = new MaterialSpinner((LayerPane) newValue, ROLES);
                roleSpinner.setValue(entity.getRuolo());
                pane.add(roleSpinner, 1, 2);

                modButton.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

                    pane.getChildren().remove(modButton);
                    ProgressCircle mini = ProgressCircle.miniCircle();
                    pane.add(mini, 0, 5);

                    new Thread(() -> {
                        DipendentiEntity clone = (DipendentiEntity) entity.clone();
                        clone.setNome(nameTxt.getText());
                        clone.setCognome(surnameTxt.getText());
                        clone.setRuolo(roleSpinner.getValue());
                        clone.setMail(mailTxt.getText());
                        clone.setPasswordLogin(passTxt.getText());

                        if (!clone.equals(entity)) {

                            boolean result = false;
                            try {
                                result = ModifyEmployeeController.handle(clone);
                            }
                            catch (Exception e) { Platform.runLater(() ->
                                    Notifications.create().text(e.getMessage()).showWarning());
                                return;
                            }

                            if (result) {
                                Platform.runLater(() -> {
                                    Notifications.create().text("Credenziali correttamente modificate").show();
                                    list.getItems().set(list.getItems().indexOf(entity), clone);
                                    this.parent.hide();
                                });
                            }
                            else {
                                Platform.runLater(() -> {
                                    Notifications.create().text("Errore durante la modifica").showError();
                                    this.parent.hide();
                                });
                            }
                        }
                        else {
                            Platform.runLater(() -> {
                                Notifications.
                                        create().
                                        text("Le credenziali non sono state modificate!").
                                        showWarning();
                                pane.getChildren().remove(mini);
                                pane.add(modButton, 0, 5);
                            });
                        }
                    }).start();
                });
            }
        });
    }
}
