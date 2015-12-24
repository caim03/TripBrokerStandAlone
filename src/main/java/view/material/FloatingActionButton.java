package view.material;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

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

    @Override
    public void createElevation() {

        double restRadius = ds.getRadius();

        Timeline expand = new Timeline();
        KeyValue keyValue0 = new KeyValue(ds.radiusProperty(), expansion * restRadius, Interpolator.EASE_OUT);
        KeyFrame keyFrame0 = new KeyFrame(new Duration(125), keyValue0);
        expand.getKeyFrames().clear();
        expand.getKeyFrames().add(keyFrame0);

        Timeline shrink = new Timeline();
        KeyValue keyValue1 = new KeyValue(ds.radiusProperty(), restRadius, Interpolator.EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(new Duration(125), keyValue1);
        shrink.getKeyFrames().clear();
        shrink.getKeyFrames().add(keyFrame1);

        elevation = new SequentialTransition(this, expand, shrink);
    }
}
