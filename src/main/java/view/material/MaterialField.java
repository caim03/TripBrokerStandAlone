package view.material;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MaterialField extends StackPane {

    TextField field;
    Paint fill, error = Color.RED;

    public Paint getPaint() { return this.fill; }

    public MaterialField(TextField field, Paint fill) {

        super(field);
        Rectangle line = new Rectangle();
        line.setHeight(2);
        line.setFill(fill);
        this.getChildren().add(line);
        StackPane.setAlignment(line, Pos.BOTTOM_CENTER);
        line.widthProperty().bind(this.widthProperty());

        this.field = field;
        this.fill = fill;

        this.field.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (oldValue == newValue) return;

                try {
                    MaterialField.this.getChildren().remove(1);
                }
                catch (Exception ignore) {}

                Color color = (Color) MaterialField.this.getPaint();
                ColorAdjust adjust = new ColorAdjust();
                adjust.brightnessProperty().setValue(0.5);

                if (newValue) {

                    Canvas canvas = new Canvas(MaterialField.this.getWidth(), 2);
                    GraphicsContext graphics = canvas.getGraphicsContext2D();
                    graphics.setFill(color);
                    graphics.fillRect(0, 0, canvas.getWidth(), 2);
                    graphics.setFill(Color.WHITE.deriveColor(0, 1, 1, 0.5));
                    graphics.fillRect(0, 1, canvas.getWidth(), 1);
                    canvas.widthProperty().bind(MaterialField.this.widthProperty());

                    MaterialField.this.getChildren().add(canvas);
                }

                else {

                    Rectangle line = new Rectangle(MaterialField.this.getWidth(), 2, MaterialField.this.getPaint());
                    line.widthProperty().bind(MaterialField.this.widthProperty());
                    MaterialField.this.getChildren().add(line);
                }

                StackPane.setAlignment(MaterialField.this.getChildren().get(1), Pos.BOTTOM_CENTER);
            }
        });
    }
}
