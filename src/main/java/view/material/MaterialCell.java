package view.material;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MaterialCell<T> extends ListCell<T> {

    Ripple ripple = new Ripple();
    Rectangle divider = new Rectangle(0.1, 1, Color.GREY.deriveColor(0, 1, 1, 0.1));

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

        if (e == null) {
            super.setGraphic(null);
            return;
        }

        StackPane stack = new StackPane();
        stack.maxHeightProperty().bind(e.heightProperty());
        stack.setAlignment(Pos.BOTTOM_CENTER);
        stack.setPadding(new Insets(-4, -4, -4, -4));
        stack.getChildren().add(e);

        if ((e instanceof Pane) && getListView().getItems().indexOf(getItem()) < getListView().getItems().size() - 1) {

            divider.widthProperty().bind(e.widthProperty());
            stack.getChildren().add(divider);
        }

        super.setGraphic(stack);

        if (!isFocusTraversable()) return;

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
