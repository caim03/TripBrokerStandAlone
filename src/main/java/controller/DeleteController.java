package controller;

import javafx.application.Platform;
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

        new Thread(() -> {
            DAO dao = ProdottoDaoHibernate.instance();  // Primary key is set on Prodotto Table (ON DELETE CASCADE)
            DBManager.initHibernate();
            dao.delete(pacchettoEntity);
            DBManager.shutdown();

            Platform.runLater(() -> {
                Notifications.create().title("Cancellato").text("Il pacchetto è stato cancellato con successo").show();
                list.getItems().remove(this.pacchettoEntity);
                list.refresh();
            });
        }).start();
    }
}
