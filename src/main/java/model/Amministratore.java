package model;

import controller.CatalogHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import view.CatalogView;

import java.util.List;

public class Amministratore extends Ruolo {

    @Override
    public Scene generateView() {

        Stage stage = new Stage();

        Label title = new Label("Trip Broker Administration");
        title.setStyle("-fx-text-fill: snow");
        ToolBar toolbar = new ToolBar(title);
        toolbar.setStyle("-fx-background-color: cornflowerblue");
        toolbar.setMinHeight(72);

        ListView<String> list = new ListView<String>(
                FXCollections.<String>observableArrayList("Visualizza Catalogo", "OPERATION 2",  "OPERATION 3"));
        list.setCellFactory(ComboBoxListCell.forListView(list.getItems()));

        VBox drawer = new VBox(25, list);
        drawer.setMaxWidth(240);

        BorderPane container = new BorderPane(new Pane(), toolbar, null, drawer, null);
        list.setOnMouseClicked(new CatalogHandler(list));

        return new Scene(container);
    }

    @Override
    public String getRole() {
        return "Amministratore";
    }
}
