package view.material;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class DrawerCell extends MaterialCell<String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {

            setText(null);
            setGraphic(null);
            return;
        }

        setFocusTraversable(true);

        HBox cell = new HBox();
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefHeight(48);
        cell.setPadding(new Insets(10, 16, 10, 16));

        Label lbl = new Label(item);
        lbl.setTextFill(Color.CRIMSON);

        Canvas round = new Canvas(48, 48);
        round.getGraphicsContext2D().setFill(Color.web("#FF5252"));
        round.getGraphicsContext2D().fillOval(4, 4, 40, 40);

        cell.getChildren().addAll(round, lbl);

        setGraphic(cell);
    }
}
