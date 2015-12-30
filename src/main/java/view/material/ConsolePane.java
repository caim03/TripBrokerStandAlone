package view.material;

import controller.command.Command;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import view.desig.PacketAssembleView;

public class ConsolePane extends BorderPane {

    /*
     * Custom Pane class already equipped with a Toolbar and a Navigation Drawer.
     * It delegates Command attachment to the Toolbar and NavigationDrawer objects.
     */

    Toolbar toolbar; //GUI Toolbar
    NavigationDrawer drawer; //GUI Navigation Drawer, always visible and to the left

    public ConsolePane(String title) {

        toolbar = new Toolbar("Trip Broker " + title); //Toolbar init
        drawer = new NavigationDrawer(title); //Nav init
        toolbar.setAlignment(Pos.TOP_CENTER);

        setLeft(drawer);
        setTop(toolbar);
        //GUI elements attached
        setRight(null);
        setBottom(null);

        centerProperty().addListener((observable, oldValue, newValue) -> {

            if (!(newValue instanceof PacketAssembleView)) {
                setTop(null);
                //DropShadows are covered by fullscreen views attached at center; forcing Toolbar re-attachment
                //allows DropShadows to be properly cast upon center views, unless they contain another Toolbar-like
                //GUI element
                setTop(toolbar);
            }
        });
    }

    public void addToolbarButton(Button button) { toolbar.addToolbarButton(button); }
    public void hideToolbarButtons() { toolbar.removeButtons(); }
    public void addCommands(Command... commands) { drawer.addCommands(commands); }
}
