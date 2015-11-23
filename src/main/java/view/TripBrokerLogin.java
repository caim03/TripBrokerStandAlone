package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entityDB.AbstractEntity;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;

public class TripBrokerLogin extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;

        primaryStage.setScene(buildScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene buildScene() {

        TextField nameField = new TextField(),
                  surnameField = new TextField(),
                  passField = new PasswordField();
        nameField.setPromptText("Inserisci nome");
        surnameField.setPromptText("Inserisci cognome");
        passField.setPromptText("Inserisci password");

        Label nameLbl = new Label("Nome:"),
              surLbl = new Label("Cognome:"),
              passLbl = new Label("Password:");

        Button button = new Button("Accedi");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 1);
        pane.add(surLbl, 0, 2);
        pane.add(passLbl, 0, 3);

        pane.add(new MaterialField(nameField, Color.GOLD), 1, 1, 2, 1);
        pane.add(new MaterialField(surnameField, Color.GOLD), 1, 2, 2, 1);
        pane.add(new MaterialField(passField, Color.GOLD), 1, 3, 2, 1);

        pane.add(button, 1, 4);

        Label title = new Label("Login");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-size: 25");

        ToolBar toolbar = new ToolBar(title);
        toolbar.setPadding(new Insets(12, 12, 12, 12));
        toolbar.setStyle("-fx-background-color: crimson");

        VBox login = new VBox(toolbar, pane);
        login.setStyle("-fx-background-color: white");
        login.setStyle("-fx-pref-height: 300");
        login.minWidth(120);
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                AbstractEntity entity = LoginController.handle(new LoginController.Credentials(nameField.getText(), surnameField.getText(), passField.getText()));

                if (entity == null)
                    Notifications.create().title("Empty field").text("Empty field detected, please fill all fields").show();

                else if (!entity.isValid())
                    Notifications.create().title("Not Found").text("This user is not registered").show();
                else {

                    Stage stage = new Stage();
                    stage.setScene(((DipendentiEntity)entity).generateView());
                    stage.getScene().getStylesheets().add("material.css");

                    TripBrokerLogin.this.stage.close();

                    TripBrokerConsole tripBrokerConsole = new TripBrokerConsole();
                    try {
                        tripBrokerConsole.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Scene scene = new Scene(login);
        scene.getStylesheets().add("material.css");
        return scene;
    }
}
