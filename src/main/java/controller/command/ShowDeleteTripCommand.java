package controller.command;

import view.agent.DeleteGroupTripView;
import view.agent.GroupTripBookingView;
import view.material.ConsolePane;

public class ShowDeleteTripCommand extends Command {

    ConsolePane container;

    public ShowDeleteTripCommand(ConsolePane container) {

        this.container = container;
    }

    @Override
    public void execute() {

        container.setCenter(new DeleteGroupTripView());
    }
}
