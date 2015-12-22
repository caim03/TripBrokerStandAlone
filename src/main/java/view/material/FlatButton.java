package view.material;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FlatButton extends Button {

    private Circle circleRipple;
    private Rectangle rippleClip = new Rectangle();
    private Duration rippleDuration =  Duration.millis(250);
    private double lastRippleHeight = 0;
    private double lastRippleWidth = 0;
    private Color rippleColor = new Color(0, 0, 0, 0.11);

    public FlatButton() {

        setStyle("-fx-background-color: null; -fx-pref-width: 32px; -fx-pref-height: 32px; -fx-text-fill: #303F9F");
        setMaxWidth(Double.MAX_VALUE);
        setEffect(null);
    }

    public FlatButton(Image image) {

        this();

        Canvas canvas = new Canvas(48, 48);
        canvas.getGraphicsContext2D().drawImage(image, 0, 0);

        this.setGraphic(canvas);

        createRippleEffect();
    }

    public FlatButton(String text) {

        super(text);

        setStyle("-fx-background-color: white; -fx-pref-width: inherit; -fx-pref-height: 32px; -fx-text-fill: #303F9F;-fx-effect: null");
        setMaxWidth(Double.MAX_VALUE);

        createRippleEffect();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        final ButtonSkin buttonSkin = new ButtonSkin(this);
        // Adding circleRipple as fist node of button nodes to be on the bottom
        this.getChildren().add(0, circleRipple);
        return buttonSkin;
    }
    private void createRippleEffect() {
        circleRipple = new Circle(0.1, rippleColor);
        circleRipple.setOpacity(0.0);
        // Optional box blur on ripple - smoother ripple effect
        //circleRipple.setEffect(new BoxBlur(3, 3, 2));
        // Fade effect bit longer to show edges on the end of animation
        final FadeTransition fadeTransition = new FadeTransition(rippleDuration, circleRipple);
        fadeTransition.setInterpolator(Interpolator.EASE_OUT);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        final Timeline scaleRippleTimeline = new Timeline();
        final SequentialTransition parallelTransition = new SequentialTransition();
        parallelTransition.getChildren().addAll(
                scaleRippleTimeline,
                fadeTransition
        );
        // When ripple transition is finished then reset circleRipple to starting point
        parallelTransition.setOnFinished(event -> {
            circleRipple.setOpacity(0.0);
            circleRipple.setRadius(0.1);
        });
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            parallelTransition.stop();
            // Manually fire finish event
            parallelTransition.getOnFinished().handle(null);
            circleRipple.setCenterX(event.getX());
            circleRipple.setCenterY(event.getY());
            // Recalculate ripple size if size of button from last time was changed
            if (getWidth() != lastRippleWidth || getHeight() != lastRippleHeight)
            {
                lastRippleWidth = getWidth();
                lastRippleHeight = getHeight();
                rippleClip.setWidth(lastRippleWidth);
                rippleClip.setHeight(lastRippleHeight);
                // try block because of possible null of Background, fills ...
                try {
                    rippleClip.setArcHeight(this.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius());
                    rippleClip.setArcWidth(this.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius());
                    circleRipple.setClip(rippleClip);
                } catch (Exception e) {
                }
                // Getting 45% of longest button's length, because we want edge of ripple effect always visible
                double circleRippleRadius = Math.max(getHeight(), getWidth()) * 0.45;
                final KeyValue keyValue = new KeyValue(circleRipple.radiusProperty(), circleRippleRadius, Interpolator.EASE_OUT);
                final KeyFrame keyFrame = new KeyFrame(rippleDuration, keyValue);
                scaleRippleTimeline.getKeyFrames().clear();
                scaleRippleTimeline.getKeyFrames().add(keyFrame);
            }
            parallelTransition.playFromStart();
        });
    }

    public void setRippleColor(Color color) {
        circleRipple.setFill(color);
    }

}
