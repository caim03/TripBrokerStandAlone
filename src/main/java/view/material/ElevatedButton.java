package view.material;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class ElevatedButton extends MaterialButton {

    protected Animation elevation;
    protected DropShadow ds;
    protected static double expansion = 1.3;
    private EventHandler<MouseEvent> shadowHandler = event -> {

        elevation.stop();
        elevation.setOnFinished(action -> {

            ds.setOffsetY(3.0);
            ds.setOffsetX(3.0);
        });

        elevation.playFromStart();
    };

    public ElevatedButton() {

        super();
        bindShadow();
    }

    public ElevatedButton(Shape shape) {

        super(shape);
        bindShadow();
    }

    public ElevatedButton(String text) {
        super(text);
        bindShadow();
    }

    protected void bindShadow() {

        ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.web("#464646"));

        setEffect(ds);

        createElevation();

        addEventFilter(MouseEvent.MOUSE_CLICKED, shadowHandler);
    }

    public void createElevation() {

        double restShadowWidth = ds.getWidth(), restShadowHeight = ds.getHeight();

        Timeline expand = new Timeline();
        KeyValue keyValue00 = new KeyValue(ds.widthProperty(), restShadowWidth * expansion, Interpolator.EASE_OUT);
        KeyValue keyValue01 = new KeyValue(ds.heightProperty(), restShadowHeight * expansion, Interpolator.EASE_OUT);
        KeyFrame keyFrame0 = new KeyFrame(new Duration(125), keyValue00, keyValue01);
        expand.getKeyFrames().clear();
        expand.getKeyFrames().add(keyFrame0);

        Timeline shrink = new Timeline();
        KeyValue keyValue10 = new KeyValue(ds.widthProperty(), restShadowWidth, Interpolator.EASE_OUT);
        KeyValue keyValue11 = new KeyValue(ds.heightProperty(), restShadowHeight, Interpolator.EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(new Duration(125), keyValue10, keyValue11);
        shrink.getKeyFrames().clear();
        shrink.getKeyFrames().add(keyFrame1);

        elevation = new SequentialTransition(this, expand, shrink);
    }
}
