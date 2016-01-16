package controller.command;

import javafx.stage.Stage;
import view.TripBrokerLogin;

/**
 * Command implementation, whose job is to close the application and redirect the user to
 * the Login interface. It needs the Stage object being currently shown in order to close it when
 * "Logout" option is selected.
 */
public class LogoutCommand extends Command {

    private Stage stage; //Application Stage currently shown

    public LogoutCommand(Stage stage) { this.stage = stage; }

    @Override
    public void execute() {

        stage.close(); //Closing the Application
        //Re-open Login interface
        try { new TripBrokerLogin().start(new Stage()); }
        catch (Exception e) { e.printStackTrace(); }
    }
}
