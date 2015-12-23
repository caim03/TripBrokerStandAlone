package model.role;

import controller.Constants;
import controller.command.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.material.ConsolePane;

public class Scout extends Ruolo {

    @Override
    public Stage generateView() {

        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        ConsolePane container = new ConsolePane(getRole());
        container.setCenter(welcome);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("material.css");

        Stage stage = new Stage();
        stage.setScene(scene);

        Command refresh;
        try {
            container.addCommands(
                    new RefreshMacroCommand(container, new ShowFormCommand(container, Class.forName("view.scout.OfferInsertionView"))),
                    refresh = new RefreshCommand(container),
                    refresh,
                    new LogoutCommand(stage));
        }
        catch (ClassNotFoundException e) { e.printStackTrace(); }

        return stage;
    }

    @Override
    public String getRole() {
        return Constants.scout;
    }
}