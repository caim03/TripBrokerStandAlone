package controller.command;

import controller.ButtonInvoker;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import view.desig.PacketAssembleView;
import view.material.ConsolePane;
import view.material.FlatButton;

public class ShowPacketForm extends Command {

    ConsolePane pane;

    public ShowPacketForm(ConsolePane pane) {

        this.pane = pane;
    }

    @Override
    public void execute() {

        PacketAssembleView view = new PacketAssembleView();
        pane.setCenter(view);
        Button done = new FlatButton();
        Command command = new CreatePacketCommand(view);
        done.addEventFilter(MouseEvent.MOUSE_CLICKED, new ButtonInvoker(command));
        pane.addToolbarButton(done);
    }
}
