package model;

import controller.command.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import view.TripBrokerConsole;
import view.material.ConsolePane;

public class Scout extends Ruolo {

    @Override
    public Scene generateView() {

        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        ConsolePane container = new ConsolePane("Scout");
        container.setCenter(welcome);
        Command refresh;
        container.addCommands(new RefreshMacroCommand(container, new FormCommand(container)),
                refresh = new RefreshCommand(container), refresh, TripBrokerConsole.logoutCommand);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("material.css");
        return scene;
    }

    @Override
    public String getRole() {
        return "Scout";
    }
}
