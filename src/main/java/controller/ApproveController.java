package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;

/** This controller is used to approve a packet and change its state on DataBase;
 *  the state is changed to 'approved' in order to display it in the public catalog **/

public class ApproveController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;
    private TableView<ProdottoEntity> list;

    public ApproveController(CreaPacchettoEntity pacchettoEntity, TableView<ProdottoEntity> list) {
        /** @param CreaPacchettoEntity; this is the packet to approve
         *  @param TableView; this table view represents the list of all packet;
         *  in this class it is used to refresh it after the approvation **/

        this.pacchettoEntity = pacchettoEntity;
        this.list = list;
    }

    @Override
    public void handle(MouseEvent event) {

        new Thread(() -> {

            // set state to 'approved'
            pacchettoEntity.setStato(1);

            DAO dao = CreaPacchettoDaoHibernate.instance();

            try {
                DBManager.initHibernate();
                dao.update(pacchettoEntity);
                DBManager.shutdown();
            }
            catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> Notifications.create().text("Errore interno al database").showError());
                return;
            }

            Platform.runLater(() -> {
                Notifications.create().title("Approvato").text("Il pacchetto è stato approvato con successo").show();
                list.getItems().remove(this.pacchettoEntity);
                list.refresh();
            });
        }).start();
    }
}
