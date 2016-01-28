package view.popup;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.entityDB.ViaggioEntity;

import java.text.SimpleDateFormat;

public class TravelPopup extends OfferPopup<ViaggioEntity> {

    protected TravelPopup(ViaggioEntity entity) { super(entity); }
    protected TravelPopup(ViaggioEntity entity, boolean selling) { super(entity, selling); }

    @Override
    protected Parent generatePopup() {

        GridPane pane = (GridPane) super.generatePopup();

        int i = selling ? 0 : 1;

        pane.add(new Label("Data partenza:"), 0, 4 + i);
        pane.add(new Label("Destinazione:"), 0, 5 + i);
        pane.add(new Label("Data arrivo:"), 0, 6 + i);
        pane.add(new Label("Mezzo:"), 0, 7 + i);
        pane.add(new Label("Classe:"), 0, 8 + i);

        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(entity.getDataInizio())), 1, 4 + i);
        pane.add(new Text(entity.getDestinazione()), 1, 5 + i);
        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(entity.getDataArrivo())), 1, 6 + i);
        pane.add(new Text(entity.getMezzo()), 1, 7 + i);
        pane.add(new Text(entity.getClasse()), 1, 8 + i);

        return pane;
    }
}
