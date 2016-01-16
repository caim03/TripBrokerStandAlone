package controller.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MacroCommands are peculiar implementations of Command that allows for sequential execution
 * of many Commands. In this project, it is mainly used as parent for RefreshMacroCommand class,
 * whose implementation firstly executes a RefreshCommand instance.
 */

public class MacroCommand extends Command {

    //List of Commands to be sequentially executed when requested
    List<Command> commands = new ArrayList<>();

    /**
     * Main constructor.
     * @param commands Command[]: a List of commands to be sequentially executed.
     */
    public MacroCommand(Command... commands) { Collections.addAll(this.commands, commands); }

    @Override
    public void execute() {
        for (Command c : commands) c.execute(); //for each Command in the List call execute
    }
}
