package view.material;

import com.jfoenix.controls.JFXProgressBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.entityDB.*;

public class DBCell<T extends AbstractEntity> extends ListCell<AbstractEntity> {

    @Override
    protected void updateItem(AbstractEntity item, boolean empty) {

        super.updateItem(item, empty);

        if (empty) {

            setText(null);
            setGraphic(null);
            return;
        }

        Node node;
        if (!item.isValid()) node = buildProgress();
        else if (item instanceof OffertaEntity) node = buildOffer((OffertaEntity) item);
        else if (item instanceof ViaggioGruppoEntity) node = new EmptyCell(((ProdottoEntity) item).getNome(), "group.png");
        else node = new Label("NOT IMPLEMENTED");

        setGraphic(node);
    }

    private Node buildProgress() {

        JFXProgressBar bar = new JFXProgressBar(JFXProgressBar.INDETERMINATE_PROGRESS);

        return bar;
    }

    private Node buildOffer(OffertaEntity item) {

        String type = item.getTipo();

        String image;

        if ("Pernottamento".equals(type)) {
            image = "stay.png";
        }
        else if ("Evento".equals(type)) {
            image = "event.png";
        }
        else if ("Viaggio".equals(type)) {

            String vehicle = ((ViaggioEntity) item).getMezzo();

            if ("Aereo".equals(vehicle)) image = "airplane.png";
            else if ("Bus".equals(vehicle)) image = "bus.png";
            else if ("Treno".equals(vehicle)) image = "train.png";
            else image = "boat.png";
        }
        else image = "create.png";

        return new EmptyCell(item.getNome(), image);
    }

    class EmptyCell extends HBox {

        private Label lbl;
        private Canvas thumbnail;

        Label getLbl() { return lbl; }
        Canvas getThumbnail() { return thumbnail; }

        EmptyCell() {

            setAlignment(Pos.CENTER_LEFT);
            setPrefHeight(48);
            setPadding(new Insets(10, 8, 10, 8));
            setStyle("-fx-hgap: 2px");

            lbl = new Label();
            thumbnail = new Canvas(48, 48);

            setListeners();

            getChildren().addAll(thumbnail, lbl);
        }

        EmptyCell(String text, String location) {

            this();
            setLabel(text);
            setThumbnail(location);
        }

        void setLabel(String text) {

            lbl.setText(text);
            lbl.setTextFill(Color.CRIMSON);
        }

        void setThumbnail(String location) {

            setThumbnail(new Image(location));
        }

        void setThumbnail(Image image) {

            fillThumbnail(Color.web("#FF5252"));
            fillImage(image);
        }

        void fillThumbnail(Paint paint) {

            GraphicsContext context = thumbnail.getGraphicsContext2D();
            context.setFill(paint);
            context.fillOval(4, 4, 40, 40);
        }

        void fillImage(Image image) {

            GraphicsContext context = thumbnail.getGraphicsContext2D();
            context.setFill(Color.WHITE);
            context.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 8, 8, 32, 32);
        }

        void setListeners() {

            ChangeListener listener = new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (isSelected()) return;
                    if (newValue) lbl.setTextFill(Color.WHITE);
                    else lbl.setTextFill(Color.CRIMSON);
                }
            };

            DBCell.this.hoverProperty().addListener(listener);
            DBCell.this.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) lbl.setTextFill(Color.WHITE);
                else lbl.setTextFill(Color.CRIMSON);
            });
        }
    }
}
