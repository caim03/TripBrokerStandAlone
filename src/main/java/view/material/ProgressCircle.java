package view.material;

import com.sun.javaws.progress.Progress;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ProgressCircle extends Group {

    ProgressBarArc arc;

    public static ProgressCircle circleElevated() {

        return new ProgressCircle(true);
    }

    public ProgressCircle() {

        super();

        Rectangle rectangle = new Rectangle(56, 56, Color.TRANSPARENT);

        Circle mask = new Circle(12, Color.WHITE);
        arc = new ProgressBarArc();

        mask.setCenterX(28);
        mask.setCenterY(28);
        arc.setCenterX(28);
        arc.setCenterY(28);

        getChildren().addAll(rectangle, arc, mask);
    }

    public ProgressCircle(boolean elevated) {

        this();

        if (elevated) {

            Circle background = new Circle(24, Color.WHITE);
            background.setCenterX(28);
            background.setCenterY(28);

            getChildren().add(1, background);

            setEffect(Shadow.getStaticInstance());
        }
    }

    public void start() { arc.start(); }

    private static class ProgressBarArc extends Arc {

        static Duration duration = Duration.millis(500);

        Timeline rotation, expand, shrink;

        public ProgressBarArc() {

            super(0, 0, 16, 16, 0, 20);
            setFill(Color.RED);
            setCenterX(64);
            setCenterY(64);
            setType(ArcType.ROUND);

            rotation = new Timeline();
            rotation.getKeyFrames().clear();
            KeyValue rotValue = new KeyValue(startAngleProperty(), getStartAngle() + 90);
            KeyFrame rotFrame = new KeyFrame(duration.divide(2), rotValue);
            rotation.getKeyFrames().add(rotFrame);

            expand = new Timeline();
            KeyValue expValue = new KeyValue(lengthProperty(), 270);
            KeyFrame expFrame = new KeyFrame(duration, expValue);
            expand.getKeyFrames().clear();
            expand.getKeyFrames().add(expFrame);

            KeyValue rotValue1 = new KeyValue(startAngleProperty(), getStartAngle() + 90);
            KeyFrame rotFrame1 = new KeyFrame(duration, rotValue1);
            expand.getKeyFrames().add(rotFrame1);

            shrink = new Timeline();

            KeyValue shrValue = new KeyValue(lengthProperty(), 20);
            KeyFrame shrFrame = new KeyFrame(duration, shrValue);

            rotation.setOnFinished(event -> {

                expand.getKeyFrames().clear();
                expand.getKeyFrames().add(expFrame);

                KeyValue rotValue2 = new KeyValue(startAngleProperty(), getStartAngle() + 90 + 45);
                KeyFrame rotFrame2 = new KeyFrame(duration.multiply(1.5), rotValue2);
                expand.getKeyFrames().add(rotFrame2);

                expand.playFromStart();
            });

            expand.setOnFinished(event -> {

                KeyValue rotValue2 = new KeyValue(startAngleProperty(), getStartAngle() + 250 + 180);
                KeyFrame rotFrame2 = new KeyFrame(duration, rotValue2);

                shrink.getKeyFrames().clear();
                shrink.getKeyFrames().add(rotFrame2);
                shrink.getKeyFrames().add(shrFrame);

                shrink.playFromStart();
            });

            shrink.setOnFinished(event -> {

                rotation.getKeyFrames().clear();
                KeyValue rotValue01 = new KeyValue(startAngleProperty(), getStartAngle() + 90);
                KeyFrame rotFrame01 = new KeyFrame(duration.divide(2), rotValue01);
                rotation.getKeyFrames().add(rotFrame01);

                rotation.playFromStart();
            });

            rotation.playFromStart();
        }

        void start() { rotation.playFromStart(); }
    }
}
