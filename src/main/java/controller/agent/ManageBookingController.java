package controller.agent;

import javafx.scene.Node;
import model.entityDB.PrenotazioneEntity;
import view.agent.ManageBookingView;

public class ManageBookingController {

    public static Node getView() { return new ManageBookingView(); }

    public static boolean confirm(PrenotazioneEntity entity) {
        return ConfirmBookingController.handle(entity);
    }

    public static boolean cancel(PrenotazioneEntity entity) {
        return CancelBookingController.handle(entity);
    }
}
