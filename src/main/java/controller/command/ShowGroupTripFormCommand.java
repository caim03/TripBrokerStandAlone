package controller.command;

import controller.ButtonInvoker;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import view.GroupTripFormView;
import view.agent.GroupTripAssembleView;
import view.desig.PacketAssembleView;
import view.material.ConsolePane;
import view.material.FlatButton;

public class ShowGroupTripFormCommand extends Command {

    ConsolePane container;

    public ShowGroupTripFormCommand(ConsolePane container) {
        this.container = container;
    }

    @Override
    public void execute() {

        PacketAssembleView view = new GroupTripAssembleView();
        container.setCenter(view);


        Button done = new FlatButton();
        Command command = new CreatePacketCommand(view);
        done.addEventFilter(MouseEvent.MOUSE_CLICKED, new ButtonInvoker(command));
        container.addToolbarButton(done);

    }
}
