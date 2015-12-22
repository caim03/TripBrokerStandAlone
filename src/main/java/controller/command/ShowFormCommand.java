package controller.command;

import controller.ButtonInvoker;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import view.Collector;
import view.material.ConsolePane;
import view.material.FlatButton;

public class ShowFormCommand extends ShowCommand {

    public ShowFormCommand(ConsolePane container, Class c) {

        super(container, c);
    }

    @Override
    public void execute() {

        super.execute();

        try {

            if (view == null) return;

            Collector collector = (Collector) view;
            Button done = new FlatButton(new Image("create.png"));
            Command command = new CollectCommand(collector);
            done.addEventFilter(MouseEvent.MOUSE_CLICKED, new ButtonInvoker(command));

            container.addToolbarButton(done);
        }
        catch (ClassCastException e) { e.printStackTrace(); }
    }
}
