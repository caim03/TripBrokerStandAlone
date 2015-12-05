package view;

import controller.CatalogHandler;
import controller.command.Command;
import controller.command.LogoutCommand;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Amministratore;
import model.Scout;

public class TripBrokerConsole extends Application {

    public static Command logoutCommand;

    @Override
    public void start(Stage primaryStage) throws Exception {

        TripBrokerConsole.logoutCommand = new LogoutCommand(primaryStage);

        primaryStage.setScene(new Scout().generateView());

        primaryStage.setFullScreen(true);
        CatalogHandler.setStage(primaryStage);
        primaryStage.show();
    }
}
