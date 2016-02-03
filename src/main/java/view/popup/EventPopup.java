package view.popup;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.entityDB.EventoEntity;

import java.text.SimpleDateFormat;


public class EventPopup extends OfferPopup<EventoEntity> {

    protected EventPopup(EventoEntity entity, boolean selling) { super(entity, selling); }

    @Override
    protected Parent generatePopup() {

        GridPane pane = (GridPane) super.generatePopup();

        int i = selling ? 0 : 1;

        pane.add(new Label("Luogo:"), 0, 4 + i);
        pane.add(new Label("Data:"), 0, 5 + i);
        pane.add(new Label("Ora inizio:"), 0, 6 + i);
        pane.add(new Label("Ora Fine:"), 0, 7 + i);

        pane.add(new Text(entity.getLuogo()), 1, 4 + i);
        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(entity.getDataInizio())), 1, 5 + i);
        pane.add(new Text(new SimpleDateFormat("HH:mm").format(entity.getDataInizio())), 1, 6 + i);
        pane.add(new Text(new SimpleDateFormat("HH:mm").format(entity.getDataFine())), 1, 7 + i);

        return pane;
    }
}
