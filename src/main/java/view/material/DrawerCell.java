package view.material;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DrawerCell extends MaterialCell<String> {

    /*
     * Navigation Drawer ListCell for options ListView. MaterialCell subclass.
     */

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {

            setText(null);
            setGraphic(null);
            return;
        }

        setFocusTraversable(true); //Enable ripple effect

        HBox cell = new HBox();
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefHeight(48);
        cell.setPadding(new Insets(10, 16, 10, 16));
        //Container

        Label lbl = new Label(item);
        lbl.setTextFill(Color.CRIMSON);
        //Option tag

        Canvas round = new Canvas(48, 48);
        round.getGraphicsContext2D().setFill(Color.web("#FF5252"));
        round.getGraphicsContext2D().fillOval(4, 4, 40, 40);
        //Option thumbnail

        cell.getChildren().addAll(round, lbl);

        setGraphic(cell);
    }
}
