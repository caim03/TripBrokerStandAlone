package view.material;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DrawerCell extends ListCell<String> {

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) return;

        HBox cell = new HBox();
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefHeight(48);
        cell.setPadding(new Insets(10, 8, 10, 8));

        Label lbl = new Label(item);
        lbl.setTextFill(Color.CRIMSON);

        ChangeListener listener = new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (isSelected()) return;
                if (newValue) lbl.setTextFill(Color.WHITE);
                else lbl.setTextFill(Color.CRIMSON);
            }
        };

        hoverProperty().addListener(listener);
        focusedProperty().addListener(listener);
        selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) lbl.setTextFill(Color.WHITE);
                else lbl.setTextFill(Color.CRIMSON);
            }
        });

        Canvas round = new Canvas(48, 48);
        round.getGraphicsContext2D().setFill(Color.web("#FF5252"));
        round.getGraphicsContext2D().fillOval(4, 4, 40, 40);

        cell.getChildren().addAll(round, lbl);

        setGraphic(cell);
    }
}
