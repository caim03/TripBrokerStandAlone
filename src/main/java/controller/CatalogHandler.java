package controller;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.CatalogView;

public class CatalogHandler implements EventHandler<MouseEvent> {

    ListView list;
    static Stage stage;

    public CatalogHandler() {
        this(null);
    }

    public CatalogHandler(ListView list) {

        setList(list);
    }

    public void setList(ListView list) {

        this.list = list;
    }

    public static void setStage(Stage stage) {

        CatalogHandler.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {

        if (stage== null || list == null) return;

        if(list.getSelectionModel().getSelectedItem().equals("Visualizza Catalogo")){
            CatalogView catalogView = new CatalogView();
            stage.close();
            try {
                catalogView.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
