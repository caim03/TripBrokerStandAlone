package model;

import controller.CatalogHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.Collation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import view.CatalogView;

import java.util.List;

public class Amministratore extends Ruolo {

    @Override
    public Scene generateView() {

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
        drawer.setAlignment(Pos.TOP_CENTER);

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
        return "Amministratore";
    }
}
