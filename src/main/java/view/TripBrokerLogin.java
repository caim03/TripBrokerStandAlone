package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TripBrokerLogin extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(buildScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene buildScene() {

        TextField nameField = new TextField(),
                  surnameField = new TextField(),
                  passField = new TextField();
        nameField.setPromptText("Inserisci nome");
        surnameField.setPromptText("Inserisci cognome");
        passField.setPromptText("Inserisci password");

        Label nameLbl = new Label("Nome:"),
              surLbl = new Label("Cognome:"),
              passLbl = new Label("Password:");

        Button button = new Button("Accedi");

        GridPane pane = new GridPane();
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 1);
        pane.add(surLbl, 0, 2);
        pane.add(passLbl, 0, 3);

        pane.add(nameField, 1, 1, 2, 1);
        pane.add(surnameField, 1, 2, 2, 1);
        pane.add(passField, 1, 3, 2, 1);

        pane.add(button, 1, 4);

        Label title = new Label("Login");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-size: 25");

        ToolBar toolbar = new ToolBar(title);
        toolbar.setPadding(new Insets(12, 12, 12, 12));
        toolbar.setStyle("-fx-background-color: crimson");

        VBox login = new VBox(toolbar, pane);
        login.setStyle("-fx-pref-height: 300");
        login.minWidth(120);

        return new Scene(login);
    }
}
