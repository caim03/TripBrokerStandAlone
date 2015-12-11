package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import org.controlsfx.control.Notifications;


public class ApproveController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;

    public ApproveController(CreaPacchettoEntity pacchettoEntity) {

        this.pacchettoEntity = pacchettoEntity;
    }

    @Override
    public void handle(MouseEvent event) {

        pacchettoEntity.setStato(1);

        DAO dao = CreaPacchettoDaoHibernate.instance();

        try {
            DBManager.initHibernate();
            dao.update(pacchettoEntity);
            DBManager.shutdown();
        }
        catch (Exception e) {
            e.printStackTrace();
            Notifications.create().text("Internal Database error").showError();
            return;
        }

        ((Node)event.getSource()).getScene().getWindow().hide();
        Notifications.create().title("Approved").text("The packet has been approved").show();
    }
}
