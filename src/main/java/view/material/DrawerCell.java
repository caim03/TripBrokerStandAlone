package view.material;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.CssMetaData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DrawerCell extends ListCell<String> {

    Label lbl;

    public DrawerCell() {

        ChangeListener listener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                onFocusedItem(newValue);
            }
        };

        hoverProperty().addListener(listener);
        selectedProperty().addListener(listener);
    }

    public void onFocusedItem(boolean focused) {

        if (lbl != null) {
            if (focused) lbl.setTextFill(Color.WHITE);
            else lbl.setTextFill(Color.CRIMSON);
        }
    }



    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) return;

        HBox cell = new HBox();
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefHeight(48);
        cell.setPadding(new Insets(10, 8, 10, 8));

        lbl = new Label(item);
        lbl.setStyle("-fx-text-fill: crimson");

        Canvas round = new Canvas(48, 48);
        round.getGraphicsContext2D().setFill(Color.ORANGE);
        round.getGraphicsContext2D().fillOval(4, 4, 40, 40);

        cell.getChildren().addAll(round, lbl);

        setGraphic(cell);
    }


}
