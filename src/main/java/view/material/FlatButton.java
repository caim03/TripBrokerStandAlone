package view.material;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class FlatButton extends MaterialButton {

    public FlatButton() {

        setStyle("-fx-background-color: null; -fx-pref-width: 32px; -fx-pref-height: 32px; -fx-text-fill: #303F9F");
        setMaxWidth(Double.MAX_VALUE);
        setEffect(null);
    }

    public FlatButton(Image image) {

        this();

        Canvas canvas = new Canvas(48, 48);
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);

        setGraphic(canvas);
    }

    public FlatButton(String text) {

        super(text);

        setStyle("-fx-background-color: null; -fx-min-width: 64dpi; -fx-pref-height: 36dpi; " +
                 "-fx-effect: null; padding-left: 8dpi; padding-right: 8dpi; -fx-text-fill: #3F51B5;");
    }
}
