package view.admin;

import controller.admin.AddNewEmployeeController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.Notifications;
import view.Collector;
import view.material.LayerPane;
import view.material.MaterialSpinner;
import view.material.MaterialTextField;

/**
 * View class for employee management use case, in particular new one's addition to employee register.
 */
public class AddNewEmployeeView extends LayerPane implements Collector {

    private static final ObservableList<String> ROLES = FXCollections.observableArrayList("Agente", "Designer", "Scout");

    private TextField name, surname, password, email;
    private MaterialSpinner role;

    public AddNewEmployeeView() {

        name = new MaterialTextField();
        name.setPromptText("Inserisci nome");
        surname = new MaterialTextField();
        surname.setPromptText("Inserisci cognome");
        password = new MaterialTextField();
        password.setPromptText("Inserisci password");
        email = new MaterialTextField();

        role = new MaterialSpinner(this, ROLES);

        GridPane pane = new GridPane();
        pane.add(new Label("Nome"), 0, 0);
        pane.add(name, 1, 0);

        pane.add(new Label("Cognome"), 0, 1);
        pane.add(surname, 1, 1);

        pane.add(new Label("Password"), 0, 2);
        pane.add(password, 1, 2);

        pane.add(new Label("e-mail"), 0, 3);
        pane.add(email, 1, 3);

        pane.add(new Label("Occupazione"), 0, 4);
        pane.add(role, 1, 4);

        pane.setPadding(new Insets(25));
        pane.setHgap(4);

        attach(pane);
    }

    @Override
    public void reset() {
        try {
            name.setText(null);
            surname.setText(null);
            password.setText(null);
            email.setText(null);
            role = null;
            role = new MaterialSpinner(this, ROLES);
        }
        catch (NullPointerException ignore) {}
    }

    /**
     * Info recollection method; it gathers employee credential and tries to add them into the register.
     */
    @Override
    public void harvest() {

        new Thread(() -> {
            boolean result = false;
            try {
                result = AddNewEmployeeController.handle(
                        name.getText(),
                        surname.getText(),
                        password.getText(),
                        role.getValue(),
                        email.getText());
            }
            catch (Exception e) { Platform.runLater(() ->
                    Notifications.create().text(e.getMessage()).showWarning()); }
            //problems with submitted info

            if (result) {
                Platform.runLater(() -> {
                    Notifications.create()
                            .text("Il dipendente " + name.getText() + " " + surname.getText() + " è stato aggiunto con successo")
                            .title("Operazione completata")
                            .show();
                    reset(); });
            }
            else Platform.runLater(() ->
                Notifications.create()
                        .text("Errore inaspettato")
                        .title("Operazione fallita")
                        .showError());

        }).start();
    }
}
