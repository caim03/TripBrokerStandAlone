package controller.command;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import view.Collector;
import view.material.ConsolePane;

public class ShowCommand extends Command {

    protected ConsolePane container;
    protected Class c;
    protected Node view;

    public ShowCommand(ConsolePane container, Class c) {

        this.container = container;
        this.c = c;
    }

    @Override
    public void execute() {

        try {

            view = (Node) c.newInstance();
            container.setCenter(view);
        }
        catch (InstantiationException | IllegalAccessException | ClassCastException e) { e.printStackTrace(); }
    }
}
