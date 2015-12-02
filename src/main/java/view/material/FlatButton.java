package view.material;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class FlatButton extends Button {

    public FlatButton() {

        setStyle("-fx-background-color: null; -fx-pref-width: 48px; -fx-pref-height: 48px");

        Canvas canvas = new Canvas(48, 48);
        canvas.getGraphicsContext2D().drawImage(new Image("create.png"), 0, 0);

        this.setGraphic(canvas);
    }
}
