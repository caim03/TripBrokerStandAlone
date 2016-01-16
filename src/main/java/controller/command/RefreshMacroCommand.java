package controller.command;

import view.material.ConsolePane;

/**
 * MacroCommand subclass; its first command is a RefreshCommand instance
 */
public class RefreshMacroCommand extends MacroCommand {

    /**
     * Main constructor.
     * @param pane ConsolePane: the ConsolePane to pass as an argument for RefreshCommand constructor
     * @param commands Command[]: sequential list of Commands to be executed after refreshing the window
     */
    public RefreshMacroCommand(ConsolePane pane, Command... commands) {
        super(commands);
        this.commands.add(0, new RefreshCommand(pane));
    }
}
