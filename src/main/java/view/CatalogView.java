package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;

import java.util.List;


public class CatalogView extends Application {
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        primaryStage.setScene(buildScene());
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private Scene buildScene(){

        Label title = new Label("Trip Broker Catalog");
        title.setStyle("-fx-text-fill: snow");
        ToolBar toolbar = new ToolBar(title);
        toolbar.setStyle("-fx-background-color: cornflowerblue");
        toolbar.setMinHeight(72);

        ListView<String> list = new ListView<String>();
        ObservableList<String> names = FXCollections.observableArrayList();
        list.setCellFactory(ComboBoxListCell.forListView(list.getItems()));
        VBox drawer = new VBox(25, list);
        drawer.setMaxWidth(240);

        BorderPane container = new BorderPane(new Pane(), toolbar, null, drawer, null);

        Scene scene = new Scene(container);
        scene.getStylesheets().add("material.css");

        List<ProdottoEntity> prodottoEntities;
        DAO dao = ProdottoDaoHibernate.instance();
        DBManager.initHibernate();
        prodottoEntities = (List<ProdottoEntity>) dao.getAll();
        DBManager.shutdown();

        if (prodottoEntities == null) {
            Notifications.create().title("Empty catalog").text("No products in catalog").show();
        }
        else{
            for (ProdottoEntity p : prodottoEntities){
                names.add(p.getNome());
            }
            list.setItems(names);
        }

        return scene;
    }
}
