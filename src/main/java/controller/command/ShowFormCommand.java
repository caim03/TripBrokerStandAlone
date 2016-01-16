package controller.command;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import view.Collector;
import view.material.ConsolePane;
import view.material.FlatButton;

/**
 * ShowCommand subclass used for particular Collector GUI elements; it ensures
 * ShowCommand functionality but also instantiate a Toolbar Button whose responsibility
 * is to execute the harvest() routine codified into a CollectorCommand instance.
 */
public class ShowFormCommand extends ShowCommand {

    /**
     * Main constructor. It basically calls for the super constructor.
     * @param container ConsolePane.
     * @param c Class: a Node Class, implementing the Collector interface.
     */
    public ShowFormCommand(ConsolePane container, Class c) { super(container, c); }

    /**
     * Execution firstly calls for super method; once the generated Node instance
     * is stored into the 'view' attribute, it can be referred to by the Toolbar
     * confirmation Button.
     * THIS CLASS SHOULD BE USED CAREFULLY: it fully works with Node instances that
     * implement the Collector interface, whose abstract method abstract is called back on
     * Toolbar Button click.
     */
    @Override
    public void execute() {

        super.execute();

        try {
            if (view == null) return; //There probably was an error with Node instantiation

            Collector collector = (Collector) view; //Casting to access harvest()
            Button done = new FlatButton(new Image("create.png")); //Toolbar Button creation
            Command command = new CollectCommand(collector); //CollectorCommand
            done.addEventFilter(MouseEvent.MOUSE_CLICKED, new ButtonInvoker(command));

            container.addToolbarButton(done); //attaching button to the Toolbar
        }
        catch (ClassCastException e) { e.printStackTrace(); }
    }
}
