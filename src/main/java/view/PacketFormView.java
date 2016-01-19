package view;

import controller.PacketAssembleController;
import controller.PacketOverseer;
import controller.command.Command;
import controller.command.TransferRecordCommand;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.PacchettoOffertaDaoHibernate;
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;
import view.material.*;

import java.util.Collection;
import java.util.List;

public class PacketFormView extends VBox implements Collector {

    ListView<AbstractEntity> list;
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

        list = new ListView(new PacketList());
        list.setPadding(new Insets(16, 16, 16, 16));
        list.setCellFactory(callback -> new DBCell());
        list.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
                int selected = list.getSelectionModel().getSelectedIndex();
                list.getItems().remove(selected, list.getItems().size());
            }
            else event.consume();
            list.getSelectionModel().clearSelection();
        });
        addListener();

        getChildren().addAll(nameBox, basePrice, maxPrice, priceBox, list);

        setStyle("-fx-vgap: 8px; -fx-fill-height: true");
    }

    public PacketFormView(CreaPacchettoEntity entity) {
        /** @param CreaPacchettoEntity; new packet that must be added in database **/

        this();

        nameField.setText(entity.getNome());
        priceField.setText(Double.toString(entity.getPrezzo()));

        list.getItems().add(AbstractEntity.getInvalidEntity());

        new Thread(() -> {
            DBManager.initHibernate();
            List<PacchettoOffertaEntity> ids = (List<PacchettoOffertaEntity>)
                    PacchettoOffertaDaoHibernate.instance().
                            getByCriteria("where idPacchetto = " + entity.getId() + " order by posizione");
            if (ids != null) {
                List<OffertaEntity> buffer;
                boolean trick = true;
                for (PacchettoOffertaEntity e : ids) {
                    if (trick) { trick = false; Platform.runLater(() -> list.getItems().remove(0)); }
                    buffer = (List<OffertaEntity>) OffertaDaoHibernate.instance().getByCriteria("where id = " + e.getIdOfferta());
                    final List finalBuffer = buffer;
                    if (buffer != null) Platform.runLater(() -> list.getItems().addAll(finalBuffer));
                }
            }
            DBManager.shutdown();
        }).start();

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

                    if (c.getList().size() == 0) {
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

    public TransferRecordCommand getCommand() {
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

            List<AbstractEntity> offers = list.getItems();
            AbstractEntity beginning = offers.get(0), end = offers.get(offers.size() - 1);

            if (!(beginning instanceof ViaggioEntity) || !(end instanceof ViaggioEntity) ||
                !((ViaggioEntity) beginning).getCittà().equals(((ViaggioEntity) end).getDestinazione()))
                Notifications.create().text("I pacchetti dovrebbero iniziare e terminare con un viaggio, check-in e check-out nella stessa location").showWarning();

            else {

                for (AbstractEntity entity : list.getItems()) {
                    ids[i] = ((ProdottoEntity) entity).getId();
                    ++i;
                }

                new Thread(() -> {
                    boolean result = PacketAssembleController.create(name, price, ids);
                    Platform.runLater(()-> {

                        if (result)
                            Notifications.create().text("Il pacchetto '" + name + "' è stato aggiunto al catalogo").show();
                        else
                            Notifications.create().text("Errore interno al database").showError();
                    });
                }).start();
            }
        }
    }
}
