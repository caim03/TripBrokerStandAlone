package view;

import controller.command.Command;
import controller.command.LogoutCommand;
import javafx.application.Application;
import javafx.stage.Stage;
import model.entityDB.DipendentiEntity;

public class TripBrokerConsole extends Application {

    public static Command logoutCommand;
    private static DipendentiEntity guest;

    public TripBrokerConsole(DipendentiEntity entity) {
        /** @param int; this integer represents the employee's identifier (logged in employee) **/
        guest = entity;
    }

    public static String getGuestName() {
        /** @return String; guest name **/
        return guest.getNome() + " " + guest.getCognome();
    }
    public static int getGuestID() { return guest.getId(); }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage = guest.generateView();

        TripBrokerConsole.logoutCommand = new LogoutCommand(primaryStage);

        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public void start() {
        try { start(guest.generateView()); } catch (Exception e) { e.printStackTrace(); }
    }
}
