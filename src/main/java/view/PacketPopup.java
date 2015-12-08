package view;

import controller.ApproveController;
import controller.DeleteController;
import controller.RejectController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entityDB.CreaPacchettoEntity;


public class PacketPopup extends PopupView {
    CreaPacchettoEntity pacchettoEntity;

    public PacketPopup(CreaPacchettoEntity prodottoEntity) {
        this.pacchettoEntity = prodottoEntity;
    }

    @Override
    public void generatePopup() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(new Stage());

        dialog.setTitle("Pacchetto");

        Label nameLbl = new Label("Nome:"),
                priceLbl = new Label("Prezzo:"),
                stateLbl = new Label("Stato:"),
                motivLbl = new Label("Motivazione:"),
                creatLbl = new Label("Creatore:");

        Button approveButton = new Button("Approva");
        Button modifyButton = new Button("Respingi");
        Button deleteButton = new Button("Cancella");

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
        pane.add(approveButton, 0, 5);
        pane.add(modifyButton, 1, 5);
        pane.add(deleteButton, 2, 5);

        pane.add(new Text(pacchettoEntity.getNome()), 1, 0);
        pane.add(new Text(Double.toString(pacchettoEntity.getPrezzo())), 1, 1);
        pane.add(new Text(Integer.toString((pacchettoEntity.getStato()))), 1, 2);
        pane.add(new Text((pacchettoEntity.getMotivazione())), 1, 3);
        pane.add(new Text("Creatore"), 1, 4);

        VBox dialogVbox = new VBox(40, pane);
        Scene dialogScene = new Scene(dialogVbox, 325, 200);
        dialog.setScene(dialogScene);
        dialog.show();

        approveButton.setOnMouseClicked(new ApproveController(pacchettoEntity, dialog));
        modifyButton.setOnMouseClicked(new RejectController(pacchettoEntity, dialog));
        deleteButton.setOnMouseClicked(new DeleteController(pacchettoEntity, dialog));
    }
}
