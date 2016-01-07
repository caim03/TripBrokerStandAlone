package view.admin;

import controller.AddNewEmployeeController;
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

public class AddNewEmployeeView extends LayerPane implements Collector {

    private static final ObservableList<String> ROLES = FXCollections.observableArrayList("Agente", "Designer", "Scout");

    TextField name, surname, password, email;
    MaterialSpinner role;

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

        attach(pane);
    }

    private void reset() {

        name.setText(null);
        surname.setText(null);
        password.setText(null);
        email.setText(null);
        role = null;
        role = new MaterialSpinner(this, ROLES);
    }


    @Override
    public void harvest() {

        if (check()) {

            new Thread(() -> {
                AddNewEmployeeController.handle(
                        name.getText(),
                        surname.getText(),
                        password.getText(),
                        role.getValue(),
                        email.getText());
                Platform.runLater(() -> {
                    Notifications.create()
                            .text("Employee " + name.getText() + " " + surname.getText() + " was added correctly")
                            .title("Operation completed successfully")
                            .show();

                    reset();
                });
            }).start();
        }

        else Notifications.create().text("Check for empty fields").showWarning();
    }

    private boolean check() {

        return (name.getText() != null && !"".equals(name.getText())) &&
               (surname.getText() != null && !"".equals(surname.getText())) &&
               (password.getText() != null && !"".equals(password.getText())) &&
               (email.getText() != null && !"".equals(email.getText())) &&
               (role.getValue() != null && !"".equals(role.getValue()));
    }
}
