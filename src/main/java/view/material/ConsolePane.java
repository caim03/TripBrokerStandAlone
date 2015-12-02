package view.material;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ConsolePane extends BorderPane {

    Toolbar toolbar;
    NavigationDrawer drawer;

    public ConsolePane(String title) {

        toolbar = new Toolbar(title);
        drawer = new NavigationDrawer();
        setTop(toolbar);
        setLeft(drawer);
    }

    public void addToolbarButton(Button button) {

        toolbar.addToolbarButton(button);
    }

    public void hideToolbarButtons() {

        toolbar.removeButtons();
    }

    public void setDrawer(ObservableList<String> opts) {

        drawer.setOptions(opts, this);
    }
}
