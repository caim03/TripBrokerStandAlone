package controller.command;

import javafx.stage.Stage;
import view.TripBrokerLogin;

public class LogoutCommand extends Command {

    private Stage stage;

    public LogoutCommand(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void execute() {

        stage.close();
        try {
            new TripBrokerLogin().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
