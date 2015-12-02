package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.CatalogView;
import view.ConsolePane;
import view.OfferInsertionView;
import view.material.FlatButton;

public class CatalogHandler implements EventHandler<MouseEvent> {

    ListView list;
    ConsolePane pane;
    static Stage stage;

    public CatalogHandler() {
        this(null, null);
    }

    public CatalogHandler(ListView list) {
        this(list, null);
    }

    public CatalogHandler(ListView list, ConsolePane pane) {

        setList(list);
        setPane(pane);
    }

    public void setList(ListView list) {

        this.list = list;
    }

    public void setPane(ConsolePane pane) {
        this.pane = pane;
    }

    public static void setStage(Stage stage) {

        CatalogHandler.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {

        if (stage== null || list == null || pane == null) return;

        if ("Visualizza Catalogo".equals(list.getSelectionModel().getSelectedItem())) {

            pane.setCenter(CatalogView.buildScene());
        }

        else if ("Inserisci offerta".equals(list.getSelectionModel().getSelectedItem())) {

            pane.hideToolbarButtons();

            Button done = new FlatButton();
            done.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
                InsertOfferController.handle();
            });
            pane.addToolbarButton(done);
            pane.setCenter(OfferInsertionView.getInstance());
        }
    }
}
