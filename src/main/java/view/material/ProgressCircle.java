package view.material;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ProgressCircle extends Group {

    private final static double NORMAL_RADIUS = 16;
    private final static double NORMAL_MASK = 12;
    private final static double NORMAL_BASE = 56;
    private final static double NORMAL_SHADOW = 24;
    private final static double MINI_RADIUS = 8;
    private final static double MINI_MASK = 6;
    private final static double MINI_BASE = 20;
    private final static double MINI_SHADOW = 12;

    private final static String MINI = "mini";
    private final static String NORMAL = "normal";

    ProgressBarArc arc;

    public static ProgressCircle circleElevated() {

        return new ProgressCircle(true);
    }

    public static ProgressCircle miniCircle() {

        return new ProgressCircle(MINI);
    }

    public ProgressCircle() { this(NORMAL); }

    public ProgressCircle(boolean elevated) {

        this();

        if (elevated) {

            Circle background = new Circle(NORMAL_SHADOW, Color.WHITE);
            background.setCenterX(NORMAL_BASE / 2);
            background.setCenterY(NORMAL_BASE / 2);

            getChildren().add(1, background);

            setEffect(Shadow.getStaticInstance());
        }
    }

    public ProgressCircle(String size) {

        super();

        Rectangle rectangle;
        Circle mask;

        if (MINI.equals(size)) {

            rectangle = new Rectangle(MINI_BASE, MINI_BASE, Color.TRANSPARENT);
            mask = new Circle(MINI_MASK, Color.WHITE);
            arc = ProgressBarArc.mini();

            mask.setCenterX(MINI_BASE / 2);
            mask.setCenterY(MINI_BASE / 2);
            arc.setCenterX(MINI_BASE / 2);
            arc.setCenterY(MINI_BASE / 2);
        }

        else {

            rectangle = new Rectangle(NORMAL_BASE, NORMAL_BASE, Color.TRANSPARENT);
            mask = new Circle(NORMAL_MASK, Color.WHITE);
            arc = ProgressBarArc.normal();

            mask.setCenterX(NORMAL_BASE / 2);
            mask.setCenterY(NORMAL_BASE / 2);
            arc.setCenterX(NORMAL_BASE / 2);
            arc.setCenterY(NORMAL_BASE / 2);
        }

        getChildren().addAll(rectangle, arc, mask);

        start();
    }

    public void start() { arc.start(); }

    private static class ProgressBarArc extends Arc {

        static Duration duration = Duration.millis(450);

        Timeline rotation, expand, shrink;

        ProgressBarArc(boolean normal) {

            super(0, 0, normal ? NORMAL_RADIUS : MINI_RADIUS, normal ? NORMAL_RADIUS : MINI_RADIUS, 0, 20);
            setFill(Color.RED);
            setType(ArcType.ROUND);
            setAnimations();
        }

        void setAnimations() {

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
        }

        void start() { rotation.playFromStart(); }

        static ProgressBarArc mini() { return new ProgressBarArc(false); }
        static ProgressBarArc normal() { return new ProgressBarArc(true); }
    }
}
