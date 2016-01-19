package view.material;

import javafx.animation.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Shadow extends DropShadow implements Cloneable {

    protected static double expansion = 1.2;
    protected Timeline expand;
    private static Shadow instance = new Shadow();

    Shadow() {

        super();

        setOffsetY(2.0);
        setColor(Color.web("#212121"));
        setBlurType(BlurType.ONE_PASS_BOX);
        setSpread(0);
    }

    public static Shadow getStaticInstance() {

        try { return (Shadow) instance.clone(); }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Shadow getInstance() {

        Shadow shadow = getStaticInstance();
        assert shadow != null;
        shadow.setAnimation();
        return shadow;
    }

    void setAnimation() {

        double restShadowWidth = getWidth(), restShadowHeight = getHeight();

        expand = new Timeline();
        KeyValue keyValue00 = new KeyValue(widthProperty(), restShadowWidth * expansion, Interpolator.EASE_OUT);
        KeyValue keyValue01 = new KeyValue(heightProperty(), restShadowHeight * expansion, Interpolator.EASE_OUT);
        KeyFrame keyFrame0 = new KeyFrame(Duration.millis(500), keyValue00, keyValue01);
        expand.getKeyFrames().clear();
        expand.getKeyFrames().add(keyFrame0);

        Timeline shrink = new Timeline();
        KeyValue keyValue10 = new KeyValue(widthProperty(), restShadowWidth, Interpolator.EASE_OUT);
        KeyValue keyValue11 = new KeyValue(heightProperty(), restShadowHeight, Interpolator.EASE_OUT);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(100), keyValue10, keyValue11);
        shrink.getKeyFrames().clear();
        shrink.getKeyFrames().add(keyFrame1);

        expand.setOnFinished(event -> shrink.playFromStart());
    }

    public void play() { expand.stop(); expand.playFromStart(); }
}
