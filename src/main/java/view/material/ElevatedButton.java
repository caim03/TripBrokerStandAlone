package view.material;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public class ElevatedButton extends MaterialButton {

    protected Shadow ds;
    protected EventHandler<MouseEvent> shadowHandler = event -> ds.play();

    public ElevatedButton() {

        super();
        bindShadow();
        setStyle("-fx-background-color: red; -fx-text-fill: white");
    }

    public ElevatedButton(Shape shape) {

        super(shape);
        bindShadow();
        setStyle("-fx-background-color: red; -fx-text-fill: white");
    }

    public ElevatedButton(String text) {
        super(text);
        bindShadow();
        setStyle("-fx-background-color: red; -fx-text-fill: white");
    }

    protected void bindShadow() {

        ds = Shadow.getInstance();

        setEffect(ds);

        addEventFilter(MouseEvent.MOUSE_CLICKED, shadowHandler);
    }
}
