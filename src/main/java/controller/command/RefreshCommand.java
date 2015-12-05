package controller.command;

import view.material.ConsolePane;

public class RefreshCommand extends Command {

    ConsolePane pane;

    public RefreshCommand(ConsolePane pane) { this.pane = pane; }

    @Override
    public void execute() {
        pane.hideToolbarButtons();
        pane.setCenter(null);
    }
}
