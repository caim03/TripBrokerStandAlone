package controller.command;

import view.material.ConsolePane;

public class RefreshMacroCommand extends MacroCommand {

    public RefreshMacroCommand(ConsolePane pane, Command... commands) {

        super(commands);
        this.commands.add(0, new RefreshCommand(pane));
    }
}
