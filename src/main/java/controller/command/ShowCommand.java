package controller.command;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import view.Collector;
import view.material.ConsolePane;
import view.material.FlatButton;

/**
 * Command implementation that allows for on-the-fly creation of a certain GUI element and its
 * consequential attachment to ConsolePane main panel.
 */
public class ShowCommand extends Command {

    protected ConsolePane container;
    protected Class c;
    protected Node view;

    /**
     * Main constructor.
     * @param container ConsolePane
     * @param c Class: a generic Class object; in order to enhance performance, GUI elements
     *          are only created when they are being attached to 'container'
     */
    public ShowCommand(ConsolePane container, Class c) {
        this.container = container;
        this.c = c;
    }

    /**
     * On execution, a Class c instance is retrieved and subsequently
     * attached to ConsolePane container.
     * THIS CLASS SHOULD BE USED CAREFULLY, because no strict ClassCast controls
     * are specified and it could be used in conjunction with not Node subclasses.
     */
    @Override
    public void execute() {
        try {
            view = (Node) c.newInstance();
            container.setCenter(view);
        }
        catch (InstantiationException | IllegalAccessException | ClassCastException e) { e.printStackTrace(); }
    }
}
