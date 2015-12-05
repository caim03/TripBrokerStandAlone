package controller.command;

import view.OfferInsertionView;

public class CreateOfferCommand extends Command {

    private OfferInsertionView receiver;

    public CreateOfferCommand(OfferInsertionView view) {

        receiver = view;
    }

    @Override
    public void execute() {

        receiver.harvest();
    }
}
