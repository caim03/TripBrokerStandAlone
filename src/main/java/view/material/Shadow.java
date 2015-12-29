package view.material;

import javafx.animation.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Shadow extends DropShadow implements Cloneable {

    protected static double expansion = 1.5;
    protected Timeline expand;
    private static Shadow instance = new Shadow();

    Shadow() {

        super();

        setOffsetY(2.0);
        setColor(Color.web("#212121"));
        setBlurType(BlurType.ONE_PASS_BOX);
        setSpread(0);

        setAnimation();
    }

    public static Shadow getInstance() {

        try { return (Shadow) instance.clone(); }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    void setAnimation() {

        double restShadowWidth = getWidth(), restShadowHeight = getHeight();

        expand = new Timeline();
        KeyValue keyValue00 = new KeyValue(widthProperty(), restShadowWidth * expansion, Interpolator.EASE_OUT);
        KeyValue keyValue01 = new KeyValue(heightProperty(), restShadowHeight * expansion, Interpolator.EASE_OUT);
        KeyFrame keyFrame0 = new KeyFrame(Duration.millis(250), keyValue00, keyValue01);
        expand.getKeyFrames().clear();
        expand.getKeyFrames().add(keyFrame0);

        Timeline shrink = new Timeline();
        KeyValue keyValue10 = new KeyValue(widthProperty(), restShadowWidth, Interpolator.EASE_OUT);
        KeyValue keyValue11 = new KeyValue(heightProperty(), restShadowHeight, Interpolator.EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(200), keyValue10, keyValue11);
        shrink.getKeyFrames().clear();
        shrink.getKeyFrames().add(keyFrame1);
        shrink.setDelay(Duration.millis(250));

        expand.setOnFinished(event -> shrink.playFromStart());
    }

    public void play() { expand.stop(); expand.playFromStart(); }
}
