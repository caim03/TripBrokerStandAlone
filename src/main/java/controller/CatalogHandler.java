package controller;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.CatalogView;
import view.ConsolePane;
import view.OfferInsertionView;

public class CatalogHandler implements EventHandler<MouseEvent> {

    ListView list;
    BorderPane pane;
    static Stage stage;

    public CatalogHandler() {
        this(null, null);
    }

    public CatalogHandler(ListView list) {
        this(list, null);
    }

    public CatalogHandler(ListView list, BorderPane pane) {

        setList(list);
        setPane(pane);
    }

    public void setList(ListView list) {

        this.list = list;
    }

    public void setPane(BorderPane pane) {
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

            ((ConsolePane)pane).addToolbarButton();
            pane.setCenter(OfferInsertionView.getInstance());
        }
    }
}
