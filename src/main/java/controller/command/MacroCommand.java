package controller.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MacroCommand extends Command {

    List<Command> commands = new ArrayList<>();

    public MacroCommand(Command... commands) {

        Collections.addAll(this.commands, commands);
    }

    @Override
    public void execute() {

        for (Command c : commands) c.execute();
    }
}
