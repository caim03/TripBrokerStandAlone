package controller;

import controller.command.Command;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Created by stg on 04/12/15.
 */
public class ButtonInvoker implements EventHandler<MouseEvent> {

    private Command command;

    public ButtonInvoker(Command command) {

        this.command = command;
    }

    @Override
    public void handle(MouseEvent event) {


    }
}
