package controller.command;

import controller.ButtonInvoker;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import view.OfferInsertionView;
import view.material.ConsolePane;
import view.material.FlatButton;

public class FormCommand extends Command {

    private ConsolePane pane;

    public FormCommand(ConsolePane pane) {

        this.pane = pane;
    }

    @Override
    public void execute() {

        OfferInsertionView view = OfferInsertionView.getInstance();
        Button done = new FlatButton();
        Command command = new CreateOfferCommand(view);
        done.addEventFilter(MouseEvent.MOUSE_CLICKED, new ButtonInvoker(command));

        pane.addToolbarButton(done);
        pane.setCenter(OfferInsertionView.getInstance());
    }
}
