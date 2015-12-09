package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import org.controlsfx.control.Notifications;


public class DeleteController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;
    private Stage stage;

    public DeleteController(CreaPacchettoEntity pacchettoEntity, Stage stage) {

        this.pacchettoEntity = pacchettoEntity;
        this.stage = stage;
    }

    @Override
    public void handle(MouseEvent event) {
        DAO dao = CreaPacchettoDaoHibernate.instance();
        DBManager.initHibernate();
        dao.delete(pacchettoEntity);
        DBManager.shutdown();

        Notifications.create().title("Deleted").text("The packet has been deleted").show();
        //this.stage.close();
    }
}
