package view.admin;

import controller.AddNewEmployeeController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        surname = new MaterialTextField();
        password = new MaterialTextField();
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

        attach(pane);
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
                Platform.runLater(() -> Notifications.create()
                        .text("Employee " + name.getText() + " " + surname + " was added correctly")
                        .title("Operation completed successfully")
                        .show());
            }).start();
        }

        else Notifications.create().text("Check for empty fields").showWarning();

        System.out.println(
                "NAME " + name.getText() +
                "\nSURNAME " + surname.getText() +
                "\nPASSWORD " + password.getText() +
                "\nEMAIL " + email.getText() +
                "\nROLE " + role.getValue());
    }

    private boolean check() {

        return false;
    }
}