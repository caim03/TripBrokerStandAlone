package view.material;

import javafx.scene.control.ListCell;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class MaterialCell<T> extends ListCell<T> {

    Ripple ripple = new Ripple();

    MaterialCell() {

        focusTraversableProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == oldValue) return;
            if (newValue == null || !newValue) removeEventHandler(MouseEvent.MOUSE_CLICKED, ripple.getPlayer());
            else addEventHandler(MouseEvent.MOUSE_CLICKED, ripple.getPlayer());
        });
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        Skin skin = super.createDefaultSkin();
        getChildren().add(0, ripple);
        return skin;
    }

    protected void setGraphic(Region e) {

        super.setGraphic(e);

        if (e == null || !isFocusTraversable()) return;

        getChildren().remove(ripple);
        removeEventHandler(MouseEvent.MOUSE_CLICKED, ripple.getPlayer());

        Rectangle clip = new Rectangle();

        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());

        e.widthProperty().addListener((obs, old, newbie) -> ripple.setRippleRadius(newbie.doubleValue()));
        e.heightProperty().addListener((obs, old, newbie) -> ripple.setRippleRadius(newbie.doubleValue()));

        try {
            double arc = e.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius();
            clip.setArcHeight(arc);
            clip.setArcWidth(arc);
        } catch (NullPointerException ignore) {}

        ripple.setClip(clip);

        getChildren().add(ripple);
        addEventHandler(MouseEvent.MOUSE_CLICKED, ripple.getPlayer());
    }
}
