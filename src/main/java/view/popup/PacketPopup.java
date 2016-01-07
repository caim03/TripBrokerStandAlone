package view.popup;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.DipendentiEntity;

import java.util.List;


public class PacketPopup extends PopupView {

    private CreaPacchettoEntity pacchettoEntity;

    public PacketPopup(CreaPacchettoEntity prodottoEntity) {

        this.pacchettoEntity = prodottoEntity;
        this.title = "Pacchetto";

        getChildren().add(generatePopup());
    }

    @Override
    protected Parent generatePopup() {
        String state;

        Label nameLbl = new Label("Nome:"),
                priceLbl = new Label("Prezzo:"),
                stateLbl = new Label("Stato:"),
                motivLbl = new Label("Motivazione:"),
                creatLbl = new Label("Creatore:");

        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.setHgap(25);
        pane.setVgap(8);
        pane.setPadding(new Insets(25, 25, 25, 25));

        pane.add(nameLbl, 0, 0);
        pane.add(priceLbl, 0, 1);
        pane.add(stateLbl, 0, 2);
        pane.add(motivLbl, 0, 3);
        pane.add(creatLbl, 0, 4);

        pane.add(new Text(pacchettoEntity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(pacchettoEntity.getPrezzo())), 1, 1);
        if (pacchettoEntity.getStato() == 0){
            state = "In attesa di approvazione";
        }

        else if (pacchettoEntity.getStato() == 1){
            state = "Approvato";
        }

        else{
            state = "Rifiutato";
        }
        pane.add(new Text(state), 1, 2);
        pane.add(new Text((pacchettoEntity.getMotivazione())), 1, 3);
        pane.add(new Text(getEmployee(pacchettoEntity.getCreatore())), 1, 4);

        VBox dialogVbox = new VBox(40, pane);

        return dialogVbox;
    }
}