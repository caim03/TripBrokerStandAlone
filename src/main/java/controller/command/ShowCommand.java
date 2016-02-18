package controller.command;

import javafx.scene.Node;
import view.material.ConsolePane;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Command implementation that allows for on-the-fly creation of a certain GUI element and its
 * consequential attachment to ConsolePane main panel.
 */
public class ShowCommand extends Command {

    protected ConsolePane container;
    protected Class c;
    protected Method method;
    protected Object args[];
    protected Node view;

    public ShowCommand(ConsolePane container, Method method, Object... args) {
        this.container = container;
        this.method = method;
        this.args = args;
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
            view = (Node) method.invoke(null, args);
            container.setCenter(view);
        }
        catch (InvocationTargetException |
               IllegalAccessException |
               ClassCastException e) {
            e.printStackTrace();
        }
    }
}
