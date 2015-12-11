package view;

import controller.command.Command;
import controller.command.LogoutCommand;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Scout;
import model.entityDB.DipendentiEntity;

public class TripBrokerConsole extends Application {

    public static Command logoutCommand;
    private static int guest;

    public TripBrokerConsole(int id) {

        TripBrokerConsole.guest = id;
    }

    public static int getGuest() { return guest; }

    @Override
    public void start(Stage primaryStage) throws Exception {

        TripBrokerConsole.logoutCommand = new LogoutCommand(primaryStage);

        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
