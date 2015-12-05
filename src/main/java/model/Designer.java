package model;

import controller.ButtonInvoker;
import controller.command.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.CatalogView;
import view.OfferInsertionView;
import view.TripBrokerConsole;
import view.material.ConsolePane;
import view.material.FlatButton;

public class Designer extends Ruolo {

    @Override
    public Stage generateView() {

        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        ConsolePane container = new ConsolePane("Administrator");
        container.setCenter(welcome);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("material.css");

        Stage stage = new Stage();
        stage.setScene(scene);

        Command refresh;
        container.addCommands(new RefreshMacroCommand(container, new Command() {
                    @Override public void execute() { container.setCenter(CatalogView.buildScene()); }}),
                refresh = new RefreshCommand(container), refresh, new LogoutCommand(stage));

        return stage;
    }

    @Override
    public String getRole() {
        return "Designer";
    }
}
