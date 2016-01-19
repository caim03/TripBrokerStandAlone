package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import view.material.FlatButton;
import view.material.MaterialTextField;
import view.material.ProgressCircle;
import view.material.Toolbar;

public class TripBrokerLogin extends Application {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setScene(new LoginScene());
        primaryStage.show();
    }

    private class LoginScene extends Scene {

        private VBox root;
        private GridPane grid;
        private Button button;
        private TextField nameField, surnameField, passField;

        public LoginScene() {
            super(new VBox());

            getStylesheets().add("material.css");

            generate();
        }

        private void fire() {
            Event.fireEvent(button, new MouseEvent(MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, null, 0,
                    false, false, false, false, false, false,
                    false, false, false, false, null));
        }

        private void root() {
            root = (VBox) getRoot();
            root.setStyle("-fx-background-color: white");
            root.setStyle("-fx-pref-height: 300");
            root.minWidth(120);
        }

        private void grid() {
            grid = new GridPane();
            grid.setStyle("-fx-background-color: white");
            grid.setHgap(25);
            grid.setVgap(8);
            grid.setPadding(new Insets(25, 25, 25, 25));

            root.getChildren().add(grid);
        }

        private void fields() {

            nameField = new MaterialTextField();
            surnameField = new MaterialTextField();
            passField = MaterialTextField.passwordFieldInstance();

            nameField.setPromptText("Inserisci nome");
            surnameField.setPromptText("Inserisci cognome");
            passField.setPromptText("Inserisci password");

            EventHandler<KeyEvent> enter = event -> { if (KeyCode.ENTER.equals(event.getCode())) fire(); };
            nameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
            surnameField.addEventFilter(KeyEvent.KEY_PRESSED, enter);
            passField.addEventFilter(KeyEvent.KEY_PRESSED, enter);

            grid.add(nameField, 1, 1, 2, 1);
            grid.add(surnameField, 1, 2, 2, 1);
            grid.add(passField, 1, 3, 2, 1);
        }

        private void labels() {

            Label nameLbl = new Label("Nome:"),
                    surLbl = new Label("Cognome:"),
                    passLbl = new Label("Password:");

            grid.add(nameLbl, 0, 1);
            grid.add(surLbl, 0, 2);
            grid.add(passLbl, 0, 3);
        }

        private void disable() {
            button.setDisable(true);
            nameField.setDisable(true);
            surnameField.setDisable(true);
            passField.setDisable(true);
        }

        private void enable() {
            button.setDisable(false);
            nameField.setDisable(false);
            surnameField.setDisable(false);
            passField.setDisable(false);
        }

        private Node circle() {
            ProgressCircle progressCircle = new ProgressCircle();
            grid.add(progressCircle, 1, 5);
            progressCircle.start();
            return progressCircle;
        }

        private void button() {
            button = new FlatButton("Accedi");
            grid.add(button, 1, 4);

            button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {

                disable();

                Node circle = circle();

                new Thread(() -> {
                    AbstractEntity entity;
                    try {
                        entity = LoginController.handle(new LoginController.Credentials(nameField.getText(),
                                surnameField.getText(), passField.getText()));

                        Platform.runLater(() -> {
                            if (entity != null && entity.isValid()) {
                                stage.close();
                                TripBrokerConsole tripBrokerConsole = new TripBrokerConsole(((DipendentiEntity) entity));
                                try { tripBrokerConsole.start(); }
                                catch (Exception e) { e.printStackTrace(); }
                            }

                            else if (entity == null)
                                Notifications.create().
                                        title("Campi vuoti").
                                        text("Riempire tutti i campi obbligatori").
                                        show();

                            else Notifications.create().
                                        title("Dipendente non trovato").
                                        text("Questo dipendente non è registrato").
                                        show();
                        });
                    }
                    catch (JDBCConnectionException e) {
                        e.printStackTrace();
                        Platform.runLater(() -> Notifications.create().
                                    title("Connessione rifiutata").
                                    text("Errore interno al database").
                                    showWarning());
                    }
                    finally {
                        Platform.runLater(() -> {
                            grid.getChildren().remove(circle);
                            enable(); });
                    }
                }).start();
            });
        }

        private void toolbar() {
            Label title = new Label("Login");
            title.setTextFill(Color.WHITE);
            title.setStyle("-fx-font-size: 25");

            Toolbar toolbar = new Toolbar("Login");

            root.getChildren().add(0, toolbar);
        }

        private void generate() {
            root();
            grid();
            toolbar();
            labels();
            fields();
            button();
        }
    }
}
