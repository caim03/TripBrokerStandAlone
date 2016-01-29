package view.popup;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.entityDB.PernottamentoEntity;

import java.text.SimpleDateFormat;

public class StayPopup extends OfferPopup<PernottamentoEntity> {

    protected StayPopup(PernottamentoEntity entity, boolean selling) { super(entity, selling); }

    @Override
    protected Parent generatePopup() {

        GridPane pane = (GridPane) super.generatePopup();

        int i = selling ? 0 : 1;

        pane.add(new Label("Presso:"), 0, 4 + i);
        pane.add(new Label("Data check-in:"), 0, 5 + i);
        pane.add(new Label("Data check-out:"), 0, 6 + i);
        pane.add(new Label("Qualità:"), 0, 7 + i);
        pane.add(new Label("Servizio:"), 0, 8 + i);

        pane.add(new Text(entity.getLuogo()), 1, 4 + i);
        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(entity.getDataInizio())), 1, 5 + i);
        pane.add(new Text(new SimpleDateFormat("dd/MM/yyyy").format(entity.getDataFinale())), 1, 6 + i);
        pane.add(new Text(entity.getQualità()),1, 7 + i);
        pane.add(new Text(entity.getServizio()),1, 8 + i);

        return pane;
    }
}
