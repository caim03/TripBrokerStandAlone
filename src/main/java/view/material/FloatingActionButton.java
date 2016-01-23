package view.material;

import javafx.geometry.Pos;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import jfxtras.scene.control.ImageViewButton;

public class FloatingActionButton extends ElevatedButton {

    private static double fabSize = 64;
    private GridPane imagePane;

    public FloatingActionButton() {

        super(new Circle(fabSize / 2));

        setPrefSize(fabSize, fabSize);
        setMaxSize(fabSize, fabSize);
        setMinSize(fabSize, fabSize);
        setStyle("-fx-background-color: #FF5252");

        ripple.setRippleRadius(fabSize / 2);
    }

    public FloatingActionButton(Image image) {
        this();
        setImage(image);
    }

    private void setImage(Image image) {
        imagePane = new GridPane();
        imagePane.setAlignment(Pos.CENTER);
        ImageView view = new ImageView(image);
        view.setFitHeight(fabSize * 0.67);
        view.setFitWidth(fabSize * 0.67);
        imagePane.getChildren().add(view);
    }

    @Override
    protected void clipRipple() {

        Circle clip = new Circle(fabSize / 2);
        clip.centerXProperty().bind(((Circle)getShape()).centerXProperty().add(fabSize / 2));
        clip.centerYProperty().bind(((Circle)getShape()).centerYProperty().add(fabSize / 2));

        ripple.setClip(clip);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        Skin skin = super.createDefaultSkin();
        imagePane.setLayoutX(fabSize / 2);
        imagePane.setLayoutY(fabSize / 2);
        getChildren().add(1, imagePane);
        return skin;
    }
}
