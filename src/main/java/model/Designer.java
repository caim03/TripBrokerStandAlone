package model;

import com.jfoenix.controls.JFXTabPane;
import controller.command.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.material.ConsolePane;

public class Designer extends Ruolo {

    @Override
    public Stage generateView() {

        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        ConsolePane container = new ConsolePane("Designer");
        container.setCenter(welcome);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("material.css");

        Stage stage = new Stage();
        stage.setScene(scene);

        container.addCommands(new RefreshMacroCommand(container, new ShowCatalogCommand(container)),
                new RefreshMacroCommand(container, new ShowPacketForm(container)), new RefreshMacroCommand(container, new Command() {
                    @Override
                    public void execute() {

                        JFXTabPane tabPane = new JFXTabPane();
                        tabPane.setPrefSize(300, 200);
                        for (int i = 0; i < 3; ++i) {

                            Tab tab = new Tab();
                            tab.setText("Tab " + (i + 1));
                            tab.setContent(new Label("Content " + (i + 1)));
                            tab.setStyle("-fx-background-color: #3F51B5; -fx-text-fill: white");

                            tabPane.getTabs().add(tab);
                        }

                        container.setCenter(tabPane);
                    }
                }),
                new LogoutCommand(stage));

        return stage;
    }

    @Override
    public String getRole() {
        return "Designer";
    }
}
