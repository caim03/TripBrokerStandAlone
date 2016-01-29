package view.popup;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import model.entityDB.ViaggioGruppoEntity;

public class GroupTripPopup extends PopupView {

    private ViaggioGruppoEntity entity;

    public GroupTripPopup(ViaggioGruppoEntity entity) { this.entity = entity; }

    @Override
    protected Parent generatePopup() {

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25));

        pane.add(new Label("Nome:"), 0, 0);
        pane.add(new Label("Prezzo:"), 0, 1);
        pane.add(new Label("Prenotazioni:"), 0, 2);
        pane.add(new Label("Creatore:"), 0, 3);

        pane.add(new Text(entity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(entity.getPrezzo())), 1, 1);

        String state;
        if (entity.getMax() > entity.getPrenotazioni())
            state = (entity.getMax() - entity.getPrenotazioni()) + "posti disponibili";
        else state = "chiuse";

        pane.add(new Text(state), 1, 2);
        pane.add(new Text(getEmployee(entity.getCreatore())), 1, 3);

        return pane;
    }
}
