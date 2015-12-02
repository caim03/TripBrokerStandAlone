package view;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import view.material.FlatButton;
import view.material.Toolbar;

public class ConsolePane extends BorderPane {

    Toolbar toolbar;
    VBox drawer;

    public ConsolePane(String title) {

        toolbar = new Toolbar(title);
        setTop(toolbar);
    }

    public void addToolbarButton(Button button) {

        toolbar.addToolbarButton(button);
    }

    public void hideToolbarButtons() {

        toolbar.removeButtons();
    }

    public void setDrawer(VBox box) {

        drawer = box;
        setLeft(box);
    }

    public void setContent() {

        Pane pane = new Pane();

        Button btn = new Button();
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                pane.getChildren();
            }
        });
    }
}
