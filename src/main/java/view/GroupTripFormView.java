package view;

import controller.Constants;
import controller.agent.GroupTripAssembleController;
import controller.desig.PacketAssembleController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.DBManager;
import model.dao.PoliticheDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PoliticheEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;
import view.agent.GroupTripList;
import view.desig.PacketList;
import view.material.NumericField;
import view.observers.Subject;

import java.util.ArrayList;
import java.util.List;

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
        double price = ((NumericField) priceField).getNumber();
        Integer min = minSpinner.getValue(),
                max = maxSpinner.getValue();

        List<OffertaEntity> entities = new ArrayList<>();
        for (AbstractEntity entity : list.getItems()) {
            if (entity instanceof OffertaEntity) entities.add((OffertaEntity) entity);
        }

        new Thread(() -> {
            boolean result = false;
            try { result = GroupTripAssembleController.create(name, price,
                        basePrice.getNumber(), maxPrice.getNumber(), min, max, entities); }
            catch (Exception e) {
                Platform.runLater(() -> Notifications.create().text(e.getMessage()).showWarning());
                return;
            }
            if (result) {
                Platform.runLater(() -> {
                    Notifications.
                            create().
                            text("Il viaggio di gruppo '" + name + "' è stato aggiunto al catalogo").
                            show();
                    list.getItems().clear();
                    nameField.setText(null);
                    priceField.setText(null);
                });
            }
            else Platform.runLater(() -> Notifications.create().text("Inserimento fallito").showError());
        }).start();
    }
}
