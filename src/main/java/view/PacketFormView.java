package view;

import controller.PacketAssembleController;
import controller.PacketOverseer;
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
import model.entityDB.OffertaEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;
import view.material.DBCell;
import view.material.MaterialField;
import view.material.NumberLabel;
import view.material.NumericField;

import java.util.Collection;
import java.util.List;

public class PacketFormView extends VBox implements Collector {

    ListView<? extends AbstractEntity> list;
    NumberLabel basePrice, maxPrice;
    TextField nameField, priceField;

    double criteria = 2.0;

    public PacketFormView() {

        Label name = new Label("Name"), price = new Label("Price");
        name.setAlignment(Pos.CENTER_LEFT);
        price.setAlignment(Pos.CENTER_LEFT);

        basePrice = new NumberLabel("Base price: ");
        basePrice.setPadding(new Insets(16, 16, 16, 16));
        maxPrice = new NumberLabel("Maximum price: ", 0, 0, criteria);
        maxPrice.setPadding(new Insets(16, 16, 16, 16));

        nameField = new TextField();
        priceField = new NumericField();

        HBox nameBox = new HBox(name, new MaterialField(nameField, Color.GREY)),
                priceBox = new HBox(price, new MaterialField(priceField, Color.GREY));

        nameBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");
        priceBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");

        list = new ListView<>(new PacketList());
        list.setCellFactory(param -> new DBCell());
        list.setPadding(new Insets(16, 16, 16, 16));
        addListener();

        getChildren().addAll(nameBox, basePrice, maxPrice, priceBox, list);

        setStyle("-fx-vgap: 8px; -fx-fill-height: true");
    }

    public void addListener() {

        list.getItems().addListener(new ListChangeListener() {

            @Override
            public void onChanged(Change c) {

                c.next();

                if (c.wasRemoved()) {

                    int len = c.getRemovedSize();
                    List<OffertaEntity> removed = c.getRemoved();

                    System.out.println("REM " + len + " SIZE " + c.getList().size());
                    if (len == c.getList().size()) {

                        basePrice.reset();
                        maxPrice.reset();
                    }

                    else {

                        for (int i = 0; i < len; ++i) {

                            basePrice.updateNumber(-removed.get(i).getPrezzo());
                            maxPrice.updateNumber(-removed.get(i).getPrezzo());
                        }
                    }
                }
            }
        });

        list.getItems().addListener(new PacketOverseer(basePrice, maxPrice));
    }

    public Command getCommand() {
        return new TransferRecordCommand(list);
    }
    public void clear() { list.getItems().remove(0, list.getItems().size()); }

    public void harvest() {

        String name = nameField.getText();
        double price = ((NumericField)priceField).getNumber();

        if ("".equals(name) || "".equals(priceField.getText()))
            Notifications.create().text("Empty fields detected").showWarning();

        else if (price < basePrice.getNumber() || price > maxPrice.getNumber())
            Notifications.create().text("Price outside its bounds").showWarning();

        else if (list.getItems().size() == 0)
            Notifications.create().text("Empty packet").showWarning();

        else {

            int ids[] = new int[list.getItems().size()], i = 0;
            for (AbstractEntity entity : list.getItems()) {
                ids[i] = ((ProdottoEntity) entity).getId();
                ++i;
            }
            if (PacketAssembleController.create(name, price, ids))
                Notifications.create().text("Packet '" + name + "' has been added to catalog").show();
            else
                Notifications.create().text("Internal Database error").showError();
        }
    }
}
