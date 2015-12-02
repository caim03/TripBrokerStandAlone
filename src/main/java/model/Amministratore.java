package model;

import controller.CatalogHandler;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.paint.Color;

import view.material.ConsolePane;
import view.material.NavigationDrawer;

public class Amministratore extends Ruolo {

    @Override
    public Scene generateView() {

        Label welcome = new Label("Welcome");
        welcome.setPadding(new Insets(25, 25, 25, 25));
        welcome.setTextFill(Color.CRIMSON);
        welcome.setStyle("-fx-font-size: 128px");
        welcome.setAlignment(Pos.CENTER);

        ConsolePane container = new ConsolePane("Trip Broker Administration");
        container.setDrawer(FXCollections.<String>observableArrayList("Visualizza Catalogo",
                "OPERATION 2",  "OPERATION 3", "Logout"));
        container.setCenter(welcome);

        return new Scene(container);
    }

    @Override
    public String getRole() {
        return "Amministratore";
    }
}
