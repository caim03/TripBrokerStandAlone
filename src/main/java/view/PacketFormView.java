package view;

import controller.Constants;
import controller.command.TransferRecordCommand;
import controller.desig.PacketAssembleController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.dao.PoliticheDaoHibernate;
import model.entityDB.*;
import org.controlsfx.control.Notifications;
import view.desig.PacketList;
import view.material.DBCell;
import view.material.MaterialTextField;
import view.material.NumberLabel;
import view.material.NumericField;

import java.util.ArrayList;
import java.util.List;

public class PacketFormView extends VBox implements Collector {

    private CreaPacchettoEntity entity = new CreaPacchettoEntity();
    protected ListView<AbstractEntity> list;
    protected NumberLabel basePrice, maxPrice;
    protected TextField nameField, priceField;

    public PacketFormView() {

        entity.setId(0);

        Label name = new Label("Nome: "), price = new Label("Prezzo: ");
        name.setAlignment(Pos.CENTER_LEFT);
        price.setAlignment(Pos.CENTER_LEFT);

        basePrice = new NumberLabel("Prezzo base: ");
        basePrice.setPadding(new Insets(16, 16, 16, 16));
        maxPrice = new NumberLabel("Prezzo massimo: ");
        maxPrice.setPadding(new Insets(16, 16, 16, 16));

        new Thread(() -> {
            DBManager.initHibernate();
            PoliticheEntity entity0 = (PoliticheEntity) PoliticheDaoHibernate.instance().getById(Constants.minOverprice),
                            entity1 = (PoliticheEntity) PoliticheDaoHibernate.instance().getById(Constants.maxOverprice);
            DBManager.shutdown();
            Platform.runLater(() -> {
                basePrice.setMod(entity0.getValore());
                maxPrice.setMod(entity1.getValore());
            });
        }).start();

        nameField = new MaterialTextField();
        priceField = new NumericField();

        HBox nameBox = new HBox(name, nameField),
                priceBox = new HBox(price, priceField);

        nameBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");
        priceBox.setStyle("-fx-hgap: 8px; -fx-padding: 16px");

        initializeList();

        getChildren().addAll(nameBox, basePrice, maxPrice, priceBox, list);

        setStyle("-fx-vgap: 8px; -fx-fill-height: true");
    }

    public PacketFormView(CreaPacchettoEntity entity) {
        /** @param CreaPacchettoEntity; new packet that must be added in database **/

        this();

        this.entity = entity;

        nameField.setText(entity.getNome());
        priceField.setText(Double.toString(entity.getPrezzo()));

        if (entity.getStato() == 2) {

            Label motivation = new Label("Respinto poiché: " + entity.getMotivazione());
            motivation.setPadding(new Insets(16, 16, 0, 16));
            getChildren().add(0, motivation);
        }

        list.getItems().add(AbstractEntity.getInvalidEntity());
        new Thread(() -> {
            List<OffertaEntity> offers = entity.retrieveOffers();
            if (offers.size() == 0) {
                DBManager.initHibernate();
                ((CreaPacchettoDaoHibernate) CreaPacchettoDaoHibernate.instance()).populate(entity);
                DBManager.shutdown();
                offers = entity.retrieveOffers();
            }

            if (offers != null) {
                final List<OffertaEntity> finalOffers = offers;
                Platform.runLater(() -> {
                    list.getItems().clear();
                    list.getItems().addAll(finalOffers);
                });
            }
        }).start();

        setStyle("-fx-background-color: white");
    }

    private void initializeList() {

        list = new ListView(initList());
        list.setPadding(new Insets(16));
        list.setCellFactory(callback -> new DBCell());
        list.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (MouseButton.SECONDARY.equals(event.getButton())) {
                int selected = list.getSelectionModel().getSelectedIndex();
                if (selected >= 0 && selected < list.getItems().size())
                    list.getItems().remove(selected, list.getItems().size());
            }
            else event.consume();
            list.getSelectionModel().clearSelection();
        });
    }

    protected ObservableList initList() {

        PacketList packetList = new PacketList();
        packetList.subscribe(basePrice.getObserverAdapter());
        packetList.subscribe(maxPrice.getObserverAdapter());

        return packetList;
    }

    public void addListener() { }

    public TransferRecordCommand getCommand() {
        /** @return Command; return the command use to complete the operation (Pattern Command). In this case a transfer
         *  command **/

        return new TransferRecordCommand(list);
    }
    public void clear() { list.getItems().remove(0, list.getItems().size()); }

    public void harvest() {

        CreaPacchettoEntity clone = (CreaPacchettoEntity) entity.clone();
        clone.setNome(nameField.getText());
        clone.setPrezzo(((NumericField) priceField).getNumber());
        clone.setTipo(Constants.packet);
        clone.setCreatore(TripBrokerConsole.getGuestID());
        clone.setStato(0);
        clone.setMotivazione("");

        List<OffertaEntity> entities = new ArrayList<>();
        for (AbstractEntity entity : list.getItems()) {
            if (entity instanceof OffertaEntity) entities.add((OffertaEntity) entity);
        }

        new Thread(() -> {
            String str = "";
            boolean result = false;
            try {
                result = PacketAssembleController.handle(clone, basePrice.getNumber(), maxPrice.getNumber(), entities);
                str += "Il pacchetto '" + clone.getNome() + "' è stato aggiunto al catalogo";
            }
            catch (Exception e) { str = e.getMessage(); }
            finally {
                final String msg = str;
                if (result) {
                    Platform.runLater(() -> {
                        Notifications.create().text(msg).show();
                        list.getItems().clear();
                        nameField.setText(null);
                        priceField.setText(null);
                    });
                }
                else Platform.runLater(() -> Notifications.create().text(msg).showWarning());
            }
        }).start();
    }
}
