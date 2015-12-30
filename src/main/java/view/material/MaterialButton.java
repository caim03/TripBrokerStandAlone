package view.material;

import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class MaterialButton extends Button {

    /*
     * Basic Material Design Button class. It does not define new behaviours, which are handled by the superclass.
     * It basically sets the Button style and adds a Ripple object on its skin.
     */

    Ripple ripple = new Ripple(); //Personal Ripple object

    public MaterialButton(String text) {

        super(text);

        clipRipple(); //Bind Ripple clip
        addEventFilter(MouseEvent.MOUSE_CLICKED, ripple.getPlayer()); //Play Ripple effect on mouse click
    }

    public MaterialButton() {

        clipRipple();
        addEventFilter(MouseEvent.MOUSE_CLICKED, ripple.getPlayer());
    }

    public MaterialButton(Shape shape) {

        setShape(shape);
        clipRipple();
        addEventFilter(MouseEvent.MOUSE_CLICKED, ripple.getPlayer());
    }

    protected void clipRipple() {

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

    @Override
    protected Skin<?> createDefaultSkin() {
        final Skin skin = super.createDefaultSkin();
        getChildren().add(0, ripple);
        return skin;
    }
}
