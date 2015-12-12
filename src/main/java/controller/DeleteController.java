package controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.dao.ProdottoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;


public class DeleteController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;
    private TableView<ProdottoEntity> list;

    public DeleteController(CreaPacchettoEntity pacchettoEntity, TableView<ProdottoEntity> list) {
        this.pacchettoEntity = pacchettoEntity;
        this.list = list;
    }

    @Override
    public void handle(MouseEvent event) {

        ((Node)event.getSource()).getScene().getWindow().hide();

        DAO dao = ProdottoDaoHibernate.instance();  // Primary key is set on Prodotto Table (ON DELETE CASCADE)
        DBManager.initHibernate();
        dao.delete(pacchettoEntity);
        DBManager.shutdown();

        Notifications.create().title("Deleted").text("The packet has been deleted").show();
        list.getItems().remove(this.pacchettoEntity);
        list.refresh();
    }
}
