package view.material;

import controller.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.entityDB.*;
import org.controlsfx.control.Notifications;

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
        else if (item instanceof OffertaEntity)       node = buildOffer((OffertaEntity) item);
        else if (item instanceof ViaggioGruppoEntity) node = new EmptyCell(((ProdottoEntity) item).getNome(), "group.png");
        else if (item instanceof PrenotazioneEntity)  node = buildBooking((PrenotazioneEntity) item);
        else node = new Label("NOT IMPLEMENTED");

        setGraphic(node);
    }

    private Node buildProgress() { return new ProgressBar(ProgressIndicator.INDETERMINATE_PROGRESS); }

    private Node buildOffer(OffertaEntity item) {

        String type = item.getTipo();

        String image;

        if (Constants.stay.equals(type)) image = "stay.png";
        else if (Constants.event.equals(type)) image = "event.png";
        else if (Constants.travel.equals(type)) {

            String vehicle = ((ViaggioEntity) item).getMezzo();

            if (Constants.plane.equals(vehicle)) image = "airplane.png";
            else if (Constants.bus.equals(vehicle)) image = "bus.png";
            else if (Constants.train.equals(vehicle)) image = "train.png";
            else if (Constants.boat.equals(vehicle)) image = "boat.png";
            else image = "create.png";
        }
        else image = "create.png";

        return new EmptyCell(item.getNome(), image);
    }

    private Node buildBooking(PrenotazioneEntity entity) {

        VBox cell = new VBox();
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefHeight(48);
        cell.setPadding(new Insets(10, 8, 10, 8));
        cell.setStyle("-fx-vgap: 2px");

        Label name = new Label(entity.getNome() + " " + entity.getCognome());
        name.setFont(new Font(16));
        name.setTextFill(Color.BLACK);

        Label qu = new Label("Posti prenotati: " + entity.getQuantità());
        qu.setFont(new Font(14));
        qu.setTextFill(Color.DARKGRAY);

        cell.getChildren().addAll(name, qu);

        Button confirm = new FlatButton("Conferma"), cancel = new FlatButton("Elimina");
        confirm.setOnMouseClicked(event -> {
            Notifications.create().text("Prenotazione confermata").show();
        });
        cancel.setOnMouseClicked(event -> {
            Notifications.create().text("Prenotazione cancellata").show();
            getListView().getItems().remove(entity);
        });

        HBox actualCell = new HBox(cell, confirm, cancel);

        actualCell.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                double w = getListView().getWidth();

                if (oldValue != null) {
                    if (newValue != null && oldValue.doubleValue() < newValue.doubleValue() && newValue.doubleValue() > w) {
                        getListView().minWidthProperty().setValue(newValue.doubleValue() + 24);
                        getListView().prefWidthProperty().setValue(newValue.doubleValue() + 24);
                        getListView().prefWidthProperty().get();
                    }
                }

                else if (newValue != null && w < newValue.doubleValue()) {
                    getListView().minWidthProperty().setValue(newValue.doubleValue() + 24);
                    getListView().prefWidthProperty().setValue(newValue.doubleValue() + 24);
                    getListView().prefWidthProperty().get();
                }
            }
        });

        actualCell.setPadding(new Insets(16, 16, 16, 16));
        actualCell.setAlignment(Pos.CENTER_LEFT);

        return actualCell;
    }

    class EmptyCell extends HBox {

        private Label lbl;
        private Canvas thumbnail;

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
