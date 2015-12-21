package view.popup;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.entityDB.ViaggioGruppoEntity;

public class GroupTripPopup extends PopupView {

    private ViaggioGruppoEntity entity;

    public GroupTripPopup(ViaggioGruppoEntity entity) {

        this.entity = entity;
        this.title = "Viaggio di gruppo";
    }

    @Override
    protected Parent generatePopup() {
        String state;

        Label nameLbl = new Label("Nome:"),
                priceLbl = new Label("Prezzo:"),
                stateLbl = new Label("Prenotazioni:"),
                creatLbl = new Label("Creatore:");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 0);
        pane.add(priceLbl, 0, 1);
        pane.add(stateLbl, 0, 2);
        pane.add(creatLbl, 0, 3);

        pane.add(new Text(entity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(entity.getPrezzo())), 1, 1);
        if (entity.getMax() > entity.getPrenotazioni()) state = (entity.getMax() - entity.getPrenotazioni()) + " posti disponibili";
        else state = "chiuse";

        pane.add(new Text(state), 1, 2);
        pane.add(new Text("Creatore"), 1, 3);

        VBox dialogVbox = new VBox(40, pane);

        return dialogVbox;
    }
}
