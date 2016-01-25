package view;

import controller.Constants;
import controller.agent.GroupTripAssembleController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.PoliticheEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;
import view.agent.GroupTripList;
import view.desig.PacketList;
import view.material.NumericField;
import view.observers.Subject;

public class GroupTripFormView extends PacketFormView {

    private ObservantSpinner minSpinner, maxSpinner;
    private GridPane participants;

    public GroupTripFormView() {

        super();

        Label min = new Label("Minimo "), max = new Label("Massimo ");

        participants = new GridPane();
        participants.setPadding(new Insets(16));
        participants.add(min, 0, 0);
        participants.add(max, 0, 1);

        new Thread(() -> {
            DBManager.initHibernate();
            int minimum = (int) ((PoliticheEntity) PoliticheDaoHibernate.instance().getById(Constants.minGroup)).getValore();
            DBManager.shutdown();

            Platform.runLater(() -> {
                minSpinner = new ObservantSpinner(minimum, 99, minimum);
                maxSpinner = new ObservantSpinner(minimum, 99, minimum);

                ((Subject)list.getItems()).subscribe(minSpinner.getObserverAdapter());
                ((Subject)list.getItems()).subscribe(maxSpinner.getObserverAdapter());

                participants.add(minSpinner, 1, 0);
                participants.add(maxSpinner, 1, 1);
            });
        }).start();

        getChildren().add(4, participants);
    }

    @Override
    protected ObservableList initList() {

        PacketList packetList = new GroupTripList();
        packetList.subscribe(basePrice.getObserverAdapter());
        packetList.subscribe(maxPrice.getObserverAdapter());

        return packetList;
    }

    @Override
    public void harvest() {

        String name = nameField.getText();
        double price = ((NumericField)priceField).getNumber();
        Integer min = minSpinner.getValue(),
                max = maxSpinner.getValue();

        if ("".equals(name) || "".equals(priceField.getText()))
            Notifications.create().text("Riempire tutti i campi obbligatori").showWarning();

        else if (price < basePrice.getNumber() || price > maxPrice.getNumber())
            Notifications.create().text("Il prezzo deve essere compreso tra i suoi limiti").showWarning();

        else if (min == null || max == null)
            Notifications.create().text("Specificare il minimo e/o il massimo di prenotazioni disponibili").showWarning();

        else if (min.compareTo(max) > 0)
            Notifications.create().text("Attenzione, il numero minimo di biglietti prenotabili è maggiore del numero massimo di biglietti prenotabili").showWarning();

        else if (list.getItems().size() == 0)
            Notifications.create().text("Pacchetto vuoto").showWarning();

        else {

            int ids[] = new int[list.getItems().size()], i = 0;
            for (AbstractEntity entity : list.getItems()) {
                ids[i] = ((ProdottoEntity) entity).getId();
                ++i;
            }
            if (GroupTripAssembleController.create(name, price, min, max, ids))
                Notifications.create().text("Il pacchetto '" + name + "' è stato aggiunto al catalogo").show();
            else
                Notifications.create().text("Errore interno al database").showError();
        }
    }
}
