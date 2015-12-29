package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.entityDB.AbstractEntity;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;
import org.hibernate.exception.JDBCConnectionException;
import view.material.*;

public class TripBrokerLogin extends Application {

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setScene(buildScene());
        primaryStage.show();
    }

    private Scene buildScene() {

        TextField nameField = new MaterialTextField(),
                  surnameField = new MaterialTextField(),
                  passField = MaterialTextField.passwordFieldInstance();
        nameField.setPromptText("Inserisci nome");
        surnameField.setPromptText("Inserisci cognome");
        passField.setPromptText("Inserisci password");

        Label nameLbl = new Label("Nome:"),
              surLbl = new Label("Cognome:"),
              passLbl = new Label("Password:");

        Button button = new FlatButton("Accedi");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
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

        Toolbar toolbar = new Toolbar("Login");

        VBox login = new VBox(toolbar, pane);
        login.setStyle("-fx-background-color: white");
        login.setStyle("-fx-pref-height: 300");
        login.minWidth(120);

        EventHandler<KeyEvent> enter = event -> {

            if (event.getCode().equals(KeyCode.ENTER)) {
                Event.fireEvent(button, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, null, 0, false, false, false, false, false, false, false, false, false, false, null));
            }
        };

        nameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
        surnameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
        passField.addEventFilter(KeyEvent.KEY_PRESSED, enter);

        EventHandler<MouseEvent> handler = event -> {

            button.setDisable(true);
            nameField.removeEventFilter(KeyEvent.KEY_PRESSED, enter);
            surnameField.removeEventFilter(KeyEvent.KEY_PRESSED, enter);
            passField.removeEventFilter(KeyEvent.KEY_PRESSED, enter);

            ProgressCircle progressCircle = new ProgressCircle();
            pane.add(progressCircle, 1, 5);
            progressCircle.start();

            new Thread(() -> {

                AbstractEntity entity;
                try {
                    entity = LoginController.handle(new LoginController.Credentials(nameField.getText(),
                            surnameField.getText(), passField.getText()));

                    Platform.runLater(() -> {

                        if (entity != null && entity.isValid()) {

                            Stage stage = ((DipendentiEntity) entity).generateView();

                            TripBrokerLogin.this.stage.close();

                            TripBrokerConsole tripBrokerConsole = new TripBrokerConsole(((DipendentiEntity) entity).getId());
                            try {
                                tripBrokerConsole.start(stage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {

                            if (entity == null)
                                Notifications.create().title("Empty field").text("Empty field detected, please fill all fields").show();
                            else if (!entity.isValid())
                                Notifications.create().title("Not Found").text("This user is not registered").show();

                            pane.getChildren().remove(progressCircle);
                            button.setDisable(false);
                            nameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
                            surnameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
                            passField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
                            //RESTORE LISTENERS
                        }
                    });
                }
                catch (JDBCConnectionException e) {
                    e.printStackTrace();

                    Platform.runLater(() -> {
                        Notifications.create().title("Connection refused").text("DB service not available").showWarning();
                        pane.getChildren().remove(progressCircle);
                        button.setDisable(false);
                        nameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
                        surnameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
                        passField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
                        //RESTORE LISTENERS
                    });
                }
            }).start();
        };

        button.addEventFilter(MouseEvent.MOUSE_CLICKED, handler);

        Scene scene = new Scene(login);
        scene.getStylesheets().add("material.css");
        return scene;
    }
}
