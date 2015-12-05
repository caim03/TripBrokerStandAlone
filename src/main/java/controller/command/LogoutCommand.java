package controller.command;

import javafx.stage.Stage;

public class LogoutCommand extends Command {

    private Stage stage;

    public LogoutCommand(Stage stage) {

        this.stage = stage;
    }

    @Override
    public void execute() {

        stage.close();
    }
}
