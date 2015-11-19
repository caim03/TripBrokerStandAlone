package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Amministratore;

public class TripBrokerConsole extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage = new Amministratore().generateView();
        primaryStage.show();
    }
}
