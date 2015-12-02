package model;

import controller.CatalogHandler;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import view.material.ConsolePane;
import view.material.DrawerCell;
import view.material.NavigationDrawer;

public class Scout extends Ruolo {

    @Override
    public Scene generateView() {

        ListView<String> list = new ListView<String>(FXCollections.<String>observableArrayList("Inserisci offerta",
                "OPERATION 2",  "OPERATION 3", "Logout"));


        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        ConsolePane container = new ConsolePane("TripBroker Scout");
        container.setDrawer(FXCollections.<String>observableArrayList("Inserisci offerta",
                "OPERATION 2",  "OPERATION 3", "Logout"));
        list.setOnMouseClicked(new CatalogHandler(list, container));

        return new Scene(container);
    }

    @Override
    public String getRole() {
        return "Scout";
    }
}
