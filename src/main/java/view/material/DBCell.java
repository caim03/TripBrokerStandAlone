package view.material;

import controller.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.entityDB.*;
import org.controlsfx.control.Notifications;

public class DBCell<T extends AbstractEntity> extends MaterialCell<T> {

    /*
     * Custom ListCell, used in combination with CellFactory and Entities from Hibernate DB to
     * properly display record information. It extends MaterialCell in order to display ripple animations
     * on mouse click.
     * Can currentlly represent: OffertaEntities and subclasses, ViaggioGruppoEntities, PrenotazioneEntities.
     * An InvalidEntity is used to display an IndeterminateProgressCircle during data loading processes.
     */

    @Override
    protected void updateItem(T item, boolean empty) {

        super.updateItem(item, empty);

        setFocusTraversable(true); //Enable ripple effect (dismissed later if required)

        if (empty) {

            setText(null);
            setGraphic(null);
            return;
        }

        Region node; //Region superclass grants access to width and height properties
        //node is then properly initialized
        if (!item.isValid()) node = buildProgress(); //ProgressCircle
        else if (item instanceof OffertaEntity) node = buildOffer((OffertaEntity) item);
        else if (item instanceof ViaggioGruppoEntity)
            node = new EmptyCell(((ProdottoEntity) item).getNome(), "group.png"); //Direct initialization
        else if (item instanceof PrenotazioneEntity) {
            setFocusTraversable(false); //disable cell ripple
            node = buildBooking((PrenotazioneEntity) item);
        }
        else node = new Label("NOT IMPLEMENTED"); //T not supported

        setGraphic(node); //Region is ultimately attached to DBCell via overloaded setGraphic method
    }

    private Region buildProgress() {
        //Progress placeholder for loading purposes

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(ProgressCircle.circleElevated());
        pane.prefWidthProperty().bind(getListView().widthProperty());
        pane.prefHeightProperty().bind(getListView().heightProperty());

        return pane;
    }

    private Region buildOffer(OffertaEntity item) {
        //OfferCell creator method

        String type = item.getTipo(); //retrieve item type

        String image; //Declare image path String, initialized based on subclass membership
        if (Constants.stay.equals(type)) image = "stay.png"; //Pernottamento
        else if (Constants.event.equals(type)) image = "event.png"; //Evento
        else if (Constants.travel.equals(type)) { //Viaggio

            String vehicle = ((ViaggioEntity) item).getMezzo();

            if (Constants.plane.equals(vehicle)) image = "airplane.png"; //Aereo
            else if (Constants.bus.equals(vehicle)) image = "bus.png"; //Bus
            else if (Constants.train.equals(vehicle)) image = "train.png"; //Treno
            else if (Constants.boat.equals(vehicle)) image = "boat.png"; //Nave
            else image = "create.png"; //Default
        }
        else image = "create.png"; //Default

        return new EmptyCell(item.getNome(), image); //Automated cell creation
    }

    private Region buildBooking(PrenotazioneEntity entity) {
        //BookingCell creator method

        //Manual cell creation
        VBox cell = new VBox();
        cell.setAlignment(Pos.CENTER_LEFT);
        cell.setPrefHeight(48);
        cell.setPadding(new Insets(10, 8, 10, 8));
        cell.setStyle("-fx-vgap: 2px");

        //Booker credentials
        Label name = new Label(entity.getNome() + " " + entity.getCognome());
        name.setFont(new Font(16));
        name.setTextFill(Color.BLACK);

        //Reservation quantity
        Label qu = new Label("Posti prenotati: " + entity.getQuantità());
        qu.setFont(new Font(14));
        qu.setTextFill(Color.DARKGRAY);

        cell.getChildren().addAll(name, qu);

        //Booking management Buttons
        Button confirm = new FlatButton("Conferma"),
                cancel = new FlatButton("Elimina");

        confirm.setOnMouseClicked(event -> Notifications.create().text("Prenotazione confermata").show()); //TODO actual confirmation
        cancel.setOnMouseClicked(event -> {
            Notifications.create().text("Prenotazione cancellata").show();
            getListView().getItems().remove(entity);
            //TODO actual elimination
        });

        HBox actualCell = new HBox(cell, confirm, cancel); //Assemble actual cell

        actualCell.widthProperty().addListener((observable, oldValue, newValue) -> {
            //Bind ListView width to cell width

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
        });

        actualCell.setPadding(new Insets(16, 16, 16, 16));
        actualCell.setAlignment(Pos.CENTER_LEFT);

        return actualCell;
    }

    class EmptyCell extends HBox {

        /*
         * Utility class used to automate cell creation.
         * Only supports Thumbnail + Label pattern though.
         */

        private Label lbl;
        private Canvas thumbnail;

        EmptyCell() {
            //Default constructor for Style and elements initialization

            setAlignment(Pos.CENTER_LEFT);
            setPrefHeight(32);
            setMaxHeight(32);
            setPadding(new Insets(10, 8, 10, 8));
            setStyle("-fx-hgap: 2px");

            lbl = new Label();
            thumbnail = new Canvas(48, 48); //Cache thumbnail

            getChildren().addAll(thumbnail, lbl);
        }

        EmptyCell(String text, String location) {

            this();
            setLabel(text); //Set Label message
            setThumbnail(location); //Draw thumbnail
        }

        void setLabel(String text) {

            lbl.setText(text);
            lbl.setTextFill(Color.DARKGRAY);
        }

        void setThumbnail(String location) {

            setThumbnail(new Image(location));
        }

        void setThumbnail(Image image) {

            fillThumbnail(Color.RED); //Thumbnail background color
            fillImage(image); //Actually draw image
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
    }
}
