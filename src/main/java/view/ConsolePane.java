package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ConsolePane extends BorderPane {

    HBox toolbar;
    VBox drawer;

    public ConsolePane(String title) {

        try {

            toolbar = FXMLLoader.load(getClass().getClassLoader().getResource("toolbar.fxml"));
            ((Label)toolbar.getChildren().get(0)).setText(title);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTop(toolbar);
    }

    public void addToolbarButton() {

        Button button = new Button();
        button.setText("YEAH");

        toolbar.getChildren().add(button);
    }

    public void hideToolbarButton() {

        if (toolbar.getChildren().size() == 3) {

            toolbar.getChildren().remove(2, 3);
        }
    }

    public void setDrawer(VBox box) {

        drawer = box;
        setLeft(box);
    }
}
