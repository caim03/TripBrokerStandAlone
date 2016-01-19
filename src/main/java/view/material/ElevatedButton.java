package view.material;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.awt.*;

public class ElevatedButton extends MaterialButton {

    protected Animation expand;
    protected DropShadow ds;
    protected static double expansion = 1.3;
    protected EventHandler<MouseEvent> shadowHandler = event -> expand.play();

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

        ds = new DropShadow();
        ds.setColor(Color.web("#646464"));
        ds.setBlurType(BlurType.ONE_PASS_BOX);

        bindShadowSize();

        setEffect(ds);

        addEventFilter(MouseEvent.MOUSE_CLICKED, shadowHandler);
    }

    protected void bindShadowSize() {
        final boolean[] sized = { false, false };
        heightProperty().addListener((obs, o, n) -> {
            if (n != null && n.doubleValue() > 0) {
                ds.setHeight(n.doubleValue() / 3);
                if (sized[0]) createElevation();
                sized[1] = true;
            }
        });
        widthProperty().addListener((obs, o, n) -> {
            if (n != null && n.doubleValue() > 0) {
                ds.setWidth(n.doubleValue() / 3);
                if (sized[1]) createElevation();
                sized[0] = true;
            } });
    }

    public void createElevation() {

        double restShadowWidth = ds.getWidth(), restShadowHeight = ds.getHeight();


        Timeline expand = new Timeline();
        KeyValue keyValue00 = new KeyValue(ds.widthProperty(), restShadowWidth * expansion, Interpolator.EASE_OUT);
        KeyValue keyValue01 = new KeyValue(ds.heightProperty(), restShadowHeight * expansion, Interpolator.EASE_OUT);
        KeyFrame keyFrame0 = new KeyFrame(Duration.millis(250), keyValue00, keyValue01);
        expand.getKeyFrames().clear();
        expand.getKeyFrames().add(keyFrame0);

        Timeline shrink = new Timeline();
        KeyValue keyValue10 = new KeyValue(ds.widthProperty(), restShadowWidth, Interpolator.EASE_OUT);
        KeyValue keyValue11 = new KeyValue(ds.heightProperty(), restShadowHeight, Interpolator.EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(250), keyValue10, keyValue11);
        shrink.getKeyFrames().clear();
        shrink.getKeyFrames().add(keyFrame1);

        expand.setOnFinished(event -> shrink.playFromStart());

        this.expand = new SequentialTransition(this, expand, shrink);
    }
}
