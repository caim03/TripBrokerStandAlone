package model;

import controller.CatalogHandler;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Scout extends Ruolo {

    @Override
    public Scene generateView() {

        Label title = new Label("Trip Broker Scout");
        title.setStyle("-fx-text-fill: snow");
        ToolBar toolbar = new ToolBar(title);
        toolbar.setStyle("-fx-background-color: cornflowerblue");
        toolbar.setMinHeight(72);

        ListView<String> list = new ListView<String>(
                FXCollections.<String>observableArrayList("Inserisci offerta", "OPERATION 2",  "OPERATION 3"));
        list.setCellFactory(ComboBoxListCell.forListView(list.getItems()));
        list.minHeight(Double.MAX_VALUE);

        VBox drawer = new VBox(25, list);
        drawer.setMaxWidth(240);
        drawer.setAlignment(Pos.TOP_CENTER);
        drawer.setStyle("-fx-background-color: white; -fx-border-color: null");

        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        BorderPane container = new BorderPane(welcome, toolbar, null, null, drawer);
        list.setOnMouseClicked(new CatalogHandler(list, container));

        return new Scene(container);
    }

    @Override
    public String getRole() {
        return "Scout";
    }
}
