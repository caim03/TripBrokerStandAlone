package view.material;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import view.popup.PopupView;

public class MaterialPopup extends GridPane {

    LayerPane parent;
    PopupView popup;
    EventHandler defaultEventHandler;

    public MaterialPopup(LayerPane parent, PopupView popup) { this(parent, popup, false); }

    public MaterialPopup(LayerPane parent, PopupView popup, boolean fullscreen) {
        this.parent = parent;
        this.popup = popup;

        Region view = this.popup.getGui();
        if (fullscreen) {
            view.prefHeightProperty().bind(heightProperty());
            view.prefWidthProperty().bind(widthProperty());
        }
        getChildren().add(view);

        this.popup.setParent(this);

        setStyle("-fx-background-color: #82828282");
        setAlignment(Pos.CENTER);

        defaultEventHandler = Event::consume;
        this.popup.getGui().setOnMouseClicked(getListener(defaultEventHandler, false));

        setOnMouseClicked(event -> {
            double x = event.getSceneX(), y = event.getSceneY();
            if (!this.popup.getGui().contains(x, y)) hide();
        });
    }


    public void show() { parent.attach(this); }
    public void hide() { parent.pop(); }

    public PopupEventHandler getListener(EventHandler handler, boolean dismiss) { return new PopupEventHandler(handler, dismiss); }

    class PopupEventHandler implements EventHandler<MouseEvent> {

        EventHandler<MouseEvent> handler;
        boolean dismiss;

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
