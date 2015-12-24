package view.material;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Ripple extends Circle {

    private Timeline scaleRipple;
    private double radius = 64;
    private static Duration duration =  Duration.millis(250);
    private Animation ripple;
    private EventHandler<MouseEvent> player = event -> {

        System.out.println("RIPPLE: \nx " + event.getX() + "\ny " + event.getY() + "\nw " + ((Rectangle)getClip()).getWidth()
                + "\nh " + ((Rectangle)getClip()).getHeight());
        ripple.stop();

        setCenterX(event.getX());
        setCenterY(event.getY());

        ripple.playFromStart();
    };

    public Ripple() {

        this(Color.GRAY);
    }

    public Ripple(Color color) {

        super(0.1, color.deriveColor(0, 1, 1, 0.4));

        setOpacity(0);
        setEffect(new BoxBlur(3, 3, 2));

        scaleRipple = new Timeline();
        resetExpansion();

        FadeTransition fadeRipple = new FadeTransition(duration, this);
        fadeRipple.setInterpolator(Interpolator.EASE_OUT);
        fadeRipple.setFromValue(1.0);
        fadeRipple.setToValue(0.0);

        ripple = new SequentialTransition(this, scaleRipple, fadeRipple);
        ripple.setOnFinished(action -> {
            setOpacity(0.0);
            setRadius(0.1);
        });
    }

    public void setRippleRadius(double radius) {

        if (radius > this.radius) {
            this.radius = radius;
            resetExpansion();
        }
    }

    protected void resetExpansion() {

        KeyValue keyValue = new KeyValue(radiusProperty(), radius, Interpolator.EASE_OUT);
        KeyFrame keyFrame = new KeyFrame(duration, keyValue);
        scaleRipple.getKeyFrames().clear();
        scaleRipple.getKeyFrames().add(keyFrame);
    }

    public EventHandler<MouseEvent> getPlayer() { return player; }
}
