package controller.command;

import view.agent.GroupTripBookingView;
import view.material.ConsolePane;

public class ShowBookingCommand extends Command {

    ConsolePane container;

    public ShowBookingCommand(ConsolePane container) {

        this.container = container;
    }

    @Override
    public void execute() {

        container.setCenter(new GroupTripBookingView());
    }
}
