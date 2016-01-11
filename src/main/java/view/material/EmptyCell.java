package view.material;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class EmptyCell extends HBox {

        /*
         * Utility class used to automate cell creation.
         * Only supports Thumbnail + Label pattern though.
         */

    private Label lbl;
    private VBox headers;
    private Canvas thumbnail;

    public EmptyCell() {
        //Default constructor for Style and elements initialization

        setAlignment(Pos.CENTER_LEFT);
        setPrefHeight(32);
        setMaxHeight(32);
        setPadding(new Insets(10, 8, 10, 8));
        setStyle("-fx-hgap: 4px");

        headers = new VBox();
        headers.setAlignment(Pos.CENTER_LEFT);
        headers.setStyle("-fx-vgap: 2px");
        getChildren().add(headers);
    }

    public EmptyCell(String text) {

        this();
        setLabel(text); //Set Label message
    }

    public EmptyCell(String text, String image) {

        this();
        setLabel(text); //Set Label message
        setThumbnail(image);
    }

    public void setLabel(String text) {

        if (lbl == null) {
            lbl = new Label();
            lbl.setTextFill(Color.BLACK);
            headers.getChildren().add(0, lbl);
        }

        lbl.setText(text);
    }

    public void addSubHeaders(String... subheaders) {

        for (String sub : subheaders) {

            Label subLbl = new Label(sub);
            subLbl.setTextFill(Color.GREY);
            subLbl.setFont(new Font(10));
            headers.getChildren().add(subLbl);
        }
    }

    public void setThumbnail(String location) {

        if (thumbnail == null) {
            thumbnail = new Canvas(48, 48); //Cache thumbnail
            getChildren().add(0, thumbnail);
        }

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
