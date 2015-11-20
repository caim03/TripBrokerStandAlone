package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CatalogView extends Application {
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;

        primaryStage.setScene(buildScene());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene buildScene(){

        Label title = new Label("Trip Broker Administration");
        title.setStyle("-fx-text-fill: snow");
        ToolBar toolbar = new ToolBar(title);
        toolbar.setStyle("-fx-background-color: cornflowerblue");
        toolbar.setMinHeight(72);

        ListView<String> list = new ListView<String>(
                FXCollections.<String>observableArrayList());
        list.setCellFactory(ComboBoxListCell.forListView(list.getItems()));
        VBox drawer = new VBox(25, list);
        drawer.setMaxWidth(240);

        BorderPane container = new BorderPane(new Pane(), toolbar, null, drawer, null);

        Scene scene = new Scene(drawer);
        scene.getStylesheets().add("material.css");
        return scene;
    }
}
