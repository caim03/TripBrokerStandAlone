package view.material;

import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class MaterialButton extends Button {

    Ripple ripple = new Ripple();

    public MaterialButton(String text) {

        super(text);

        addEventFilter(MouseEvent.MOUSE_CLICKED, ripple.getPlayer());

        Rectangle clip = new Rectangle();

        widthProperty().addListener((observable, oldValue, newValue) -> {
            clip.setWidth(newValue.doubleValue());
            ripple.setRippleRadius(newValue.doubleValue());
        });
        heightProperty().addListener((observable, oldValue, newValue) -> {
            clip.setHeight(newValue.doubleValue());
            ripple.setRippleRadius(newValue.doubleValue());
        });

        try {
            double arc = getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius();
            clip.setArcHeight(arc);
            clip.setArcWidth(arc);
        } catch (NullPointerException ignore) {}
        ripple.setClip(clip);
    }

    public MaterialButton() {

        super(null);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        final Skin skin = super.createDefaultSkin();
        getChildren().add(0, ripple);
        return skin;
    }
}
