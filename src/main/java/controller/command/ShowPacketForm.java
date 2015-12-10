package controller.command;

import view.desig.PacketAssembleView;
import view.material.ConsolePane;

public class ShowPacketForm extends Command {

    ConsolePane pane;

    public ShowPacketForm(ConsolePane pane) {

        this.pane = pane;
    }

    @Override
    public void execute() {

        pane.setCenter(new PacketAssembleView());
    }
}
