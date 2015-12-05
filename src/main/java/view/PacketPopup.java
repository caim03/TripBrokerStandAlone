package view;

import javafx.scene.Scene;
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
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
