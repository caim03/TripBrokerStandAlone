package controller;

import controller.command.Command;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class DrawerHandler implements EventHandler<MouseEvent> {

    protected Map<Integer, Command> commands;

    public DrawerHandler() {

        commands = new HashMap<>();
    }

    public DrawerHandler(Command... commands) {

        this();

        int i = 0;
        for (Command c : commands) {

            addCommand(i, c);
            ++i;
        }
    }

    public void addCommand(int position, Command command) {

        if (position >= 0) commands.put(position, command);
    }

    @Override
    public void handle(MouseEvent event) {

        int index = ((ListView)event.getSource()).getSelectionModel().getSelectedIndex();
        System.out.println("INDEX " + index);

        if (index < 0 || index >= commands.size()) return;

        if (commands.containsKey(index)) {

            System.out.println("COMMAND " + index);
            commands.get(index).execute();
            System.out.println("EXECUTED " + index);
        }
    }
}
