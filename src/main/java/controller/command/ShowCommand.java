package controller.command;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class ShowCommand extends Command {

    private BorderPane container;
    private Node view;

    public ShowCommand(BorderPane container, Node view) {

        this.container = container;
        this.view = view;
    }

    @Override
    public void execute() {

        container.setCenter(view);
    }
}
