package view.desig;

import controller.command.Command;
import controller.command.TransferRecordCommand;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import view.material.DBCell;
import view.material.MaterialField;
import view.material.NumericField;

import java.util.List;

public class PacketFormView extends VBox {

    ListView<AbstractEntity> list;
    Label basePrice, maxPrice;
    TextField nameField, priceField;

    double base = 0, criteria = 2.0;

    public PacketFormView() {

        Label name = new Label("Name"), price = new Label("Price");
        name.setAlignment(Pos.CENTER_LEFT);
        price.setAlignment(Pos.CENTER_LEFT);

        basePrice = new Label("Base price: " + base);
        basePrice.setPadding(new Insets(16, 16, 16, 16));
        maxPrice = new Label("Maximum price: " + base * criteria);
        maxPrice.setPadding(new Insets(16, 16, 16, 16));

        nameField = new TextField();
        priceField = new NumericField();

        HBox nameBox = new HBox(name, new MaterialField(nameField, Color.GREY)),
                priceBox = new HBox(price, new MaterialField(priceField, Color.GREY));

        nameBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");
        priceBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");

        list = new ListView<>();
        list.setCellFactory(param -> new DBCell());
        list.setPadding(new Insets(16, 16, 16, 16));

        list.getItems().addListener(new ListChangeListener() {

            @Override
            public void onChanged(Change c) {

                c.next();

                if (c.wasAdded()) {

                    int len = c.getAddedSize();
                    List added = c.getAddedSubList();
                    for (int i = 0; i < len; ++i) base += ((ProdottoEntity) added.get(i)).getPrezzo();

                    basePrice.setText("Base price: " + base);
                    maxPrice.setText("Maximum price: " + base * criteria);
                }

                else if (c.wasRemoved()) {

                    int len = c.getRemovedSize();
                    List removed = c.getRemoved();
                    for (int i = 0; i < len; ++i) base -= ((ProdottoEntity) removed.get(i)).getPrezzo();

                    basePrice.setText("Base price: " + base);
                    maxPrice.setText("Maximum price: " + base * criteria);
                }
            }
        });

        getChildren().addAll(nameBox, basePrice, maxPrice, priceBox, list);

        setStyle("-fx-vgap: 8px; -fx-fill-height: true");
    }

    public Command getCommand() {
        return new TransferRecordCommand(list);
    }
}
