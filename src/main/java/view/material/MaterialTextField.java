package view.material;

import com.sun.javafx.scene.control.behavior.PasswordFieldBehavior;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.accessibility.AccessibleText;

public class MaterialTextField extends TextField {

    Color original = Color.DARKGRAY, focused = Color.WHITE;
    Rectangle line = new Rectangle(0.1, 1, original);

    public static MaterialPasswordField passwordFieldInstance() {

        return new MaterialPasswordField();
    }

    public MaterialTextField() {

        super();

        setBackground(null);
        setBorder(null);

        focusedProperty().addListener((observable, oldValue, newValue) -> {

            if (oldValue == newValue) return;

            if (newValue) line.setFill(focused);
            else line.setFill(original);
        });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        Skin skin = super.createDefaultSkin();

        Rectangle aboveLine = new Rectangle(0.1, 1, original);
        VBox vBox = new VBox(aboveLine, line);
        vBox.setMaxHeight(2);
        StackPane stackPane = new StackPane(vBox);
        stackPane.setAlignment(Pos.BOTTOM_CENTER);
        stackPane.prefHeightProperty().bind(heightProperty());
        stackPane.setPadding(new Insets(0, 0, -4, 0));

        line.widthProperty().bind(vBox.widthProperty());
        aboveLine.widthProperty().bind(vBox.widthProperty());
        vBox.maxWidthProperty().bind(stackPane.maxWidthProperty());
        vBox.prefWidthProperty().bind(stackPane.prefWidthProperty());
        stackPane.prefWidthProperty().bind(prefWidthProperty());
        stackPane.maxWidthProperty().bind(maxWidthProperty());

        getChildren().add(0, stackPane);
        return skin;
    }

    static class MaterialPasswordField extends PasswordField {

        Color original = Color.DARKGRAY, focused = Color.WHITE;
        Rectangle line = new Rectangle(0.1, 1, original);

        public MaterialPasswordField() {

            super();

            setBackground(null);
            setBorder(null);

            focusedProperty().addListener((observable, oldValue, newValue) -> {

                if (oldValue == newValue) return;

                if (newValue) line.setFill(focused);
                else line.setFill(original);
            });
        }

        @Override
        protected Skin<?> createDefaultSkin() {
            Skin skin = super.createDefaultSkin();

            Rectangle aboveLine = new Rectangle(0.1, 1, original);
            VBox vBox = new VBox(aboveLine, line);
            vBox.setMaxHeight(2);
            StackPane stackPane = new StackPane(vBox);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            stackPane.prefHeightProperty().bind(heightProperty());
            stackPane.setPadding(new Insets(0, 0, -4, 0));

            line.widthProperty().bind(vBox.widthProperty());
            aboveLine.widthProperty().bind(vBox.widthProperty());
            vBox.maxWidthProperty().bind(stackPane.maxWidthProperty());
            vBox.prefWidthProperty().bind(stackPane.prefWidthProperty());
            stackPane.prefWidthProperty().bind(prefWidthProperty());
            stackPane.maxWidthProperty().bind(maxWidthProperty());

            getChildren().add(0, stackPane);
            return skin;
        }
    }
}
