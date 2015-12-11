package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import org.controlsfx.control.Notifications;


public class DeleteController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;

    public DeleteController(CreaPacchettoEntity pacchettoEntity) {

        this.pacchettoEntity = pacchettoEntity;
    }

    @Override
    public void handle(MouseEvent event) {

        ((Node)event.getSource()).getScene().getWindow().hide();

        DAO dao = CreaPacchettoDaoHibernate.instance();
        DBManager.initHibernate();
        dao.delete(pacchettoEntity);
        DBManager.shutdown();

        Notifications.create().title("Deleted").text("The packet has been deleted").show();

    }
}
