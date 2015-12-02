package view.material;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

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
