package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.entityDB.ProdottoEntity;
import model.entityDB.ViaggioEntity;

public class PacketCell extends ListCell<ProdottoEntity> {

    @Override
    protected void updateItem(ProdottoEntity item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) return;

        System.out.println("NULL " + item == null);

        String type = item.getTipo();

        HBox cell = new HBox();
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefHeight(48);
        cell.setPadding(new Insets(10, 8, 10, 8));

        Label lbl = new Label(item.getNome());
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
        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) lbl.setTextFill(Color.WHITE);
            else lbl.setTextFill(Color.CRIMSON);
        });

        Canvas round = new Canvas(48, 48);
        GraphicsContext context = round.getGraphicsContext2D();
        context.setFill(Color.web("#FF5252"));
        context.fillOval(4, 4, 40, 40);
        context.setFill(Color.WHITE);

        Image image = null;

        if ("Pernottamento".equals(type)) {
            image = new Image("stay.png");
        }
        else if ("Evento".equals(type)) {
            image = new Image("event.png");
        }
        else if ("Viaggio".equals(type)) {

            String vehicle = ((ViaggioEntity) item).getMezzo();

            if ("Volo".equals(vehicle)) image = new Image("airplane.png");
            else if ("Bus".equals(vehicle)) image = new Image("bus.png");
            else if ("Treno".equals(vehicle)) image = new Image("train.png");
            else if ("Traghetto".equals(vehicle)) image = new Image("boat.png");
        }

        context.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 8, 8, 32, 32);

        cell.getChildren().addAll(round, lbl);

        setGraphic(cell);
    }
}