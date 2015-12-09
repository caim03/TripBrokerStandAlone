package controller.command;


import view.PacketApproveView;
import view.material.ConsolePane;

public class ShowApproveCommand extends Command{

    ConsolePane pane;

    public ShowApproveCommand(ConsolePane pane){
        this.pane = pane;
    }

    @Override
    public void execute() {
        pane.setCenter(new PacketApproveView().buildScene());
    }
}
