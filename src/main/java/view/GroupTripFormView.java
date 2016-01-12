package view;

import controller.GroupTripAssembleController;
import controller.GroupTripOverseer;
import controller.PacketAssembleController;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;
import view.material.NumericField;

public class GroupTripFormView extends PacketFormView {

    private int current = 9999;
    private Spinner<Integer> minimum, maximum;
    private GridPane participants;

    public GroupTripFormView() {

        super();

        Label min = new Label("Minimum "), max = new Label("Maximum ");
        minimum = new Spinner<>();
        maximum = new Spinner<>();

        participants = new GridPane();
        participants.add(min, 0, 0);
        participants.add(max, 0, 1);
        participants.add(minimum, 1, 0);
        participants.add(maximum, 1, 1);

        getChildren().add(4, participants);
    }

    @Override
    public void addListener() {

        super.addListener();
        list.getItems().addListener(new GroupTripOverseer(this));
    }

    public void refreshSpinners(int max) {
        /** @param int; integer used to refresh spinner values **/

        int minPtr, maxPtr;
        try { minPtr = minimum.getValue(); } catch (NullPointerException e) { minPtr = 10; }
        try { maxPtr = maximum.getValue(); } catch (NullPointerException e) { maxPtr = 9999; }

        if (current > max) {

            minimum = new Spinner<>(10, max, minPtr > max ? max : minPtr);
            maximum = new Spinner<>(10, max, maxPtr > max ? max : maxPtr);

            current = max;

            participants.add(minimum, 1, 0);
            participants.add(maximum, 1, 1);
        }
    }

    @Override
    public void harvest() {

        String name = nameField.getText();
        double price = ((NumericField)priceField).getNumber();
        Integer min = minimum.getValue(), max = maximum.getValue();

        if ("".equals(name) || "".equals(priceField.getText()))
            Notifications.create().text("Empty fields detected").showWarning();

        else if (price < basePrice.getNumber() || price > maxPrice.getNumber())
            Notifications.create().text("Price outside its bounds").showWarning();

        else if (min == null || max == null)
            Notifications.create().text("Minimum/maximum booking number not specified").showWarning();

        else if (min.compareTo(max) > 0)
            Notifications.create().text("Minimum bookable tickets higher than maximum bookable tickets!").showWarning();

        else if (list.getItems().size() == 0)
            Notifications.create().text("Empty packet").showWarning();

        else {

            int ids[] = new int[list.getItems().size()], i = 0;
            for (AbstractEntity entity : list.getItems()) {
                ids[i] = ((ProdottoEntity) entity).getId();
                ++i;
            }
            if (GroupTripAssembleController.create(name, price, min, max, ids))
                Notifications.create().text("Packet '" + name + "' has been added to catalog").show();
            else
                Notifications.create().text("Internal Database error").showError();
        }
    }
}
