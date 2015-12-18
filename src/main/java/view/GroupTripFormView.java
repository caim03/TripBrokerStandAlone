package view;

import controller.GroupTripOverseer;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;

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

        list.getItems().addListener(new GroupTripOverseer(this));
    }

    public void refreshSpinners(int max) {

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
}
