package controller;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.CatalogView;

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

        if(list.getSelectionModel().getSelectedItem().equals("Visualizza Catalogo")) {

            pane.setCenter(CatalogView.buildScene());
            /*

            CatalogView catalogView = new CatalogView();
            stage.close();
            try {
                catalogView.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        }
    }
}
