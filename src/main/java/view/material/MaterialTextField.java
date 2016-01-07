package view.material;

import com.sun.javafx.scene.control.behavior.PasswordFieldBehavior;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleAttribute;
import javafx.scene.AccessibleRole;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.accessibility.AccessibleText;

public class MaterialTextField extends TextField {

    Color original = Color.DARKGRAY, focused = Color.WHITE;
    Rectangle line = new Rectangle(0.1, 1, original);

    public static MaterialTextField passwordFieldInstance() {

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

    static class MaterialPasswordField extends MaterialTextField {

        @Override
        public Object queryAccessibleAttribute(AccessibleAttribute attribute, Object... parameters) {
            switch (attribute) {
                case TEXT: return null;
                default: return super.queryAccessibleAttribute(attribute, parameters);
            }
        }

        public MaterialPasswordField() {

            super();

            getStyleClass().add("password-field");
            setAccessibleRole(AccessibleRole.PASSWORD_FIELD);
        }

        @Override public void cut() { }
        @Override public void copy() { }
    }
}