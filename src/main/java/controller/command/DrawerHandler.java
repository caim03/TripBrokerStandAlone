package controller.command;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

/***
 * Invoker class, used in conjunction with NavigationDrawer instances.
 * Every option in the drawer is associated with a different Command,
 * executed on selection.
 */

public class DrawerHandler implements EventHandler<MouseEvent> {

    protected Map<Integer, Command> commands;

    public DrawerHandler() { commands = new HashMap<>(); }

    public DrawerHandler(Command... commands) {

        this();

        int i = 0;
        for (Command c : commands) {

            addCommand(i, c);
            ++i;
        }
    }

    public void addCommand(int position, Command command) {

        if (commands.containsKey(position)) commands.put(position, command);
    }

    @Override
    public void handle(MouseEvent event) {

        int index = ((ListView)event.getSource()).getSelectionModel().getSelectedIndex();

        if (index < 0 || index >= commands.size()) return;

        if (commands.containsKey(index)) {

            commands.get(index).execute();
        }
    }
}
