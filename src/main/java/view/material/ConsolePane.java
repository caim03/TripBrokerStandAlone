package view.material;

import controller.command.Command;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;

public class ConsolePane extends BorderPane {

    Toolbar toolbar;
    NavigationDrawer drawer;

    public ConsolePane(String title) {

        toolbar = new Toolbar("Trip Broker " + title);
        drawer = new NavigationDrawer(title);
        toolbar.setAlignment(Pos.TOP_CENTER);

        setLeft(drawer);
        setTop(toolbar);
        setRight(null);
        setBottom(null);

        centerProperty().addListener(new ChangeListener<Node>() {
            @Override
            public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                setTop(null);
                setTop(toolbar);
            }
        });
    }

    public void addToolbarButton(Button button) { toolbar.addToolbarButton(button); }
    public void hideToolbarButtons() { toolbar.removeButtons(); }
    public void addCommands(Command... commands) { drawer.addCommands(commands); }
}
