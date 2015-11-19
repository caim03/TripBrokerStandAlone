package model;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

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
                FXCollections.<String>observableArrayList("OPERATION 1", "OPERATION 2",  "OPERATION 3"));
        list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            public ListCell<String> call(ListView<String> param) {
                return new CardCell();
            }

            class CardCell extends ListCell<String> {

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    Canvas canvas = new Canvas(240, 48);
                    canvas.setStyle("-fx-background-color: snow");
                    GraphicsContext graphics = canvas.getGraphicsContext2D();
                    graphics.setStroke(Color.CORNFLOWERBLUE);
                    graphics.strokeText(item, 20, 20);
                    graphics.strokeLine(20, 45, 220, 48);

                    setGraphic(canvas);
                }
            }
        });

        VBox drawer = new VBox(25, list);
        drawer.setMaxWidth(240);

        BorderPane container = new BorderPane(new Pane(), toolbar, null, drawer, null);

        return new Scene(container);
    }

    @Override
    public String getRole() {
        return "Amministratore";
    }
}
