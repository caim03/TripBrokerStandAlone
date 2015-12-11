package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import org.controlsfx.control.Notifications;

import java.util.Optional;


public class RejectController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;

    public RejectController(CreaPacchettoEntity pacchettoEntity) {

        this.pacchettoEntity = pacchettoEntity;
    }

    @Override
    public void handle(MouseEvent event) {
        TextInputDialog dialog;

        dialog = new TextInputDialog();
        dialog.setTitle("Motivazione");
        dialog.setHeaderText("Inserisci i motivi per cui tale pacchetto deve essere modificato!");

        Optional<String> result = dialog.showAndWait();
        String entered = "";

        if (result.isPresent()) {

            entered = result.get();
        }

        if (!"".equals(entered)) {
            pacchettoEntity.setMotivazione(entered);
            pacchettoEntity.setStato(2);

            ((Node)event.getSource()).getScene().getWindow().hide();
            DAO dao = CreaPacchettoDaoHibernate.instance();
            DBManager.initHibernate();
            dao.update(pacchettoEntity);
            DBManager.shutdown();

            Notifications.create().title("Rejected").text("The packet has been rejected").show();
        }
    }
}