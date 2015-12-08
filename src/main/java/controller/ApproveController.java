package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import org.controlsfx.control.Notifications;


public class ApproveController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;
    private Stage stage;

    public ApproveController(CreaPacchettoEntity pacchettoEntity, Stage stage) {

        this.pacchettoEntity = pacchettoEntity;
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        pacchettoEntity.setStato(1);

        DAO dao = CreaPacchettoDaoHibernate.instance();
        DBManager.initHibernate();
        dao.update(pacchettoEntity);
        DBManager.shutdown();

        Notifications.create().title("Approved").text("The packet has been approved").show();
        //this.stage.close();
    }
}
