package view.material;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import view.popup.PopupView;

public class MaterialPopup extends GridPane {

    LayerPane parent;
    PopupView popup;
    EventHandler defaultEventHandler;

    public MaterialPopup(LayerPane parent, PopupView popup) {

        this.parent = parent;
        this.popup = popup;
        this.popup.setParent(this);

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0);
        ds.setOffsetX(3.0);
        ds.setColor(Color.GRAY);
        popup.setEffect(ds);

        getChildren().add(this.popup);

        setStyle("-fx-background-color: #82828282");
        setAlignment(Pos.CENTER);

        defaultEventHandler = Event::consume;
        this.popup.setOnMouseClicked(getListener(defaultEventHandler, false));

        setOnMouseClicked(event -> {

            double x = event.getSceneX(), y = event.getSceneY();

            if (!this.popup.contains(x, y)) hide();
        });
    }

    public void show() { parent.attach(this); }
    public void hide() { parent.pop(); }

    public PopupEventHandler getListener(EventHandler handler) { return new PopupEventHandler(handler); }
    public PopupEventHandler getListener(EventHandler handler, boolean dismiss) { return new PopupEventHandler(handler, dismiss); }

    class PopupEventHandler implements EventHandler<MouseEvent> {

        EventHandler<MouseEvent> handler;
        boolean dismiss;
        PopupEventHandler(EventHandler<MouseEvent> handler) { this(handler, true); }
        PopupEventHandler(EventHandler<MouseEvent> handler, boolean dismiss) {
            this.handler = handler;
            this.dismiss = dismiss;
        }

        @Override
        public void handle(MouseEvent event) {

            event.consume();
            handler.handle(event);
            if (dismiss) hide();
        }
    }
}
