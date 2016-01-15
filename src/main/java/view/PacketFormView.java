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
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;
import view.material.*;

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

        nameField = new MaterialTextField();
        priceField = new NumericField();

        HBox nameBox = new HBox(name, nameField),
                priceBox = new HBox(price, priceField);

        nameBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");
        priceBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");

        list = new DBListView(new PacketList());
        //list.setCellFactory(param -> new DBCell());
        list.setPadding(new Insets(16, 16, 16, 16));
        addListener();

        getChildren().addAll(nameBox, basePrice, maxPrice, priceBox, list);

        setStyle("-fx-vgap: 8px; -fx-fill-height: true");
    }

    public PacketFormView(CreaPacchettoEntity entity) {
        /** @param CreaPacchettoEntity; new packet that must be added in database **/

        this();

        nameField.setText(entity.getNome());
        priceField.setText(Double.toString(entity.getPrezzo()));

        ((DBListView)list).setWhere("from OffertaEntity where id in (select idOfferta from PacchettoOffertaEntity where idPacchetto = " + entity.getId() + " order by posizione)");
        ((DBListView)list).fill();

        if (entity.getStato() == 2) {

            Label motivation = new Label("Respinto poiché: " + entity.getMotivazione());
            motivation.setPadding(new Insets(16, 16, 0, 16));
            getChildren().add(0, motivation);
        }

        setStyle("-fx-background-color: white");
    }

    public void addListener() {

        list.getItems().addListener(new ListChangeListener() {

            @Override
            public void onChanged(Change c) {

                c.next();

                if (c.wasRemoved()) {

                    int len = c.getRemovedSize();
                    List<AbstractEntity> removed = c.getRemoved();

                    if (len == c.getList().size()) {

                        basePrice.reset();
                        maxPrice.reset();
                    }

                    else {

                        for (int i = 0; i < len; ++i) {

                            if (removed.get(i) instanceof OffertaEntity) {
                                OffertaEntity item = (OffertaEntity) removed.get(i);
                                basePrice.updateNumber(-item.getPrezzo());
                                maxPrice.updateNumber(-item.getPrezzo());
                            }
                        }
                    }
                }
            }
        });

        list.getItems().addListener(new PacketOverseer(basePrice, maxPrice));
    }

    public Command getCommand() {
        /** @return Command; return the command use to complete the operation (Pattern Command). In this case a transfer
         *  command **/

        return new TransferRecordCommand(list);
    }
    public void clear() { list.getItems().remove(0, list.getItems().size()); }

    public void harvest() {

        String name = nameField.getText();
        double price = ((NumericField)priceField).getNumber();

        if ("".equals(name) || "".equals(priceField.getText()))
            Notifications.create().text("Riempire tutti i campi obbligatori").showWarning();

        else if (price < basePrice.getNumber() || price > maxPrice.getNumber())
            Notifications.create().text("Il prezzo deve essere compreso tra i suoi limiti").showWarning();

        else if (list.getItems().size() == 0)
            Notifications.create().text("Pacchetto vuoto").showWarning();

        else {

            int ids[] = new int[list.getItems().size()], i = 0;

            List<AbstractEntity> offers = (List<AbstractEntity>) list.getItems();
            AbstractEntity beginning = offers.get(0), end = offers.get(offers.size() - 1);

            if (!(beginning instanceof ViaggioEntity) || !(end instanceof ViaggioEntity) ||
                !((ViaggioEntity) beginning).getCittà().equals(((ViaggioEntity) end).getDestinazione()))
                Notifications.create().text("I pacchetti dovrebbero iniziare e terminare con un viaggio, check-in e check-out nella stessa location").showWarning();

            else {
                for (AbstractEntity entity : list.getItems()) {
                    ids[i] = ((ProdottoEntity) entity).getId();
                    ++i;
                }
                if (PacketAssembleController.create(name, price, ids))
                    Notifications.create().text("Il pacchetto '" + name + "' è stato aggiunto al catalogo").show();
                else
                    Notifications.create().text("Errore interno al database").showError();
            }
        }
    }
}
