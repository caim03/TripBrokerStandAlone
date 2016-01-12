package view;

import controller.command.Command;
import controller.command.LogoutCommand;
import javafx.application.Application;
import javafx.stage.Stage;

public class TripBrokerConsole extends Application {

    public static Command logoutCommand;
    private static int guest;

    public TripBrokerConsole(int id) {
        /** @param int; this integer represents the employee's identifier (logged in employee) **/

        TripBrokerConsole.guest = id;
    }

    public static int getGuest() {
        /** @return int; return the id attribute **/

        return guest;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        TripBrokerConsole.logoutCommand = new LogoutCommand(primaryStage);

        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
