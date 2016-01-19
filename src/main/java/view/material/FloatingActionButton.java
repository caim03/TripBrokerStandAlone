package view.material;

import javafx.scene.shape.Circle;

public class FloatingActionButton extends ElevatedButton {

    private static double fabSize = 64;

    public FloatingActionButton() {

        super(new Circle(fabSize / 2));

        setPrefSize(fabSize, fabSize);
        setMaxSize(fabSize, fabSize);
        setMinSize(fabSize, fabSize);
        setStyle("-fx-background-color: #FF5252");

        ripple.setRippleRadius(fabSize / 2);
    }

    @Override
    protected void clipRipple() {

        Circle clip = new Circle(fabSize / 2);
        clip.centerXProperty().bind(((Circle)getShape()).centerXProperty().add(fabSize / 2));
        clip.centerYProperty().bind(((Circle)getShape()).centerYProperty().add(fabSize / 2));

        ripple.setClip(clip);
    }
}
