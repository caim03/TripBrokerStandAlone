package model.role;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.TripBrokerConsole;
import view.material.ConsolePane;

/***
 * Abstract class that represents an Employee job as part of TripBroker staff.
 * All extending classes must override two methods:
 *      - generateView()
 *      - getRole()
 * According to their implementation, these methods build different GUI interfaces,
 * aimed at offering a particular set of features mirroring the employee responsibilities.
 * This class both realize dynamic association between Employee and its role and Factory Method
 * pattern implementation for GUI construction.
 ***/

public abstract class Ruolo {

    protected ConsolePane container;
    protected Stage stage;

    public Stage generateView() {

        container = new ConsolePane(getRole());
        stage = new Stage();

        welcomeMessage();

        try { addCommands(); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        Scene scene = new Scene(container);
        scene.getStylesheets().add("material.css");

        stage.setScene(scene);
        return stage;
    }
    /*** @result Stage; return the view of the right role ***/

    public abstract String getRole();
    /*** @result String; return the role as a string ***/

    protected abstract void addCommands() throws ClassNotFoundException;

    private void welcomeMessage() {

        Label welcome = new Label("Ciao, " + TripBrokerConsole.getGuestName());
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 32px");
        welcome.setAlignment(Pos.CENTER);

        container.setCenter(welcome);
    }
}
