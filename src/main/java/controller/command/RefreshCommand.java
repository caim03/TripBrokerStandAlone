package controller.command;

import view.material.ConsolePane;

/**
 * RefreshCommand is used to clear the main panel into a ConsolePane instance.
 */
public class RefreshCommand extends Command {

    ConsolePane pane; //The ConsolePane instance that needs to be cleared on request

    /**
     * Main constructor.
     * @param pane ConsolePane: a ConsolePane instance, the one that needs to be cleared.
     */
    public RefreshCommand(ConsolePane pane) { this.pane = pane; }

    @Override
    public void execute() {
        //On execution, this method calls ConsolePane clearing methods.
        pane.hideToolbarButtons();
        pane.setCenter(null);
    }
}
