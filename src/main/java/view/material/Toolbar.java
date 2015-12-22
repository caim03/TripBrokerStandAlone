package view.material;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import jfxtras.scene.control.ImageViewButton;

import java.io.IOException;

public class Toolbar extends HBox {

    @FXML Label title;

    public Toolbar() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("toolbar.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.GRAY);

        setEffect(ds);
    }

    public Toolbar(String title) {

        this();
        setTitle(title);
    }

    public void setTitle(String title) {

        this.title.setText(title);
    }

    public void addToolbarButton(Button item) {

        getChildren().add(item);
    }

    public void removeButtons() {

        int size = getChildren().size();
        if (size > 2)
            getChildren().remove(2, size);
    }
}
