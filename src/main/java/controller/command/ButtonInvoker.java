package controller.command;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/***
 * Invoker class for Command pattern; it has the shape of a MouseEventHandler,
 * in order to call for the execution when a click happens on the listening Button
 * instance.
 */
public class ButtonInvoker implements EventHandler<MouseEvent> {

    private Command command;

    public ButtonInvoker(Command command) { this.command = command; }

    @Override
    public void handle(MouseEvent event) { command.execute(); }
}
