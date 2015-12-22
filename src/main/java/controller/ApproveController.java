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


public class ApproveController implements EventHandler<MouseEvent> {

    private CreaPacchettoEntity pacchettoEntity;
    private TableView<ProdottoEntity> list;

    public ApproveController(CreaPacchettoEntity pacchettoEntity, TableView<ProdottoEntity> list) {
        this.pacchettoEntity = pacchettoEntity;
        this.list = list;
    }

    @Override
    public void handle(MouseEvent event) {

        new Thread(() -> {

            pacchettoEntity.setStato(1);

            DAO dao = CreaPacchettoDaoHibernate.instance();

            try {
                DBManager.initHibernate();
                dao.update(pacchettoEntity);
                DBManager.shutdown();
            }
            catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> Notifications.create().text("Internal Database error").showError());
                return;
            }

            Platform.runLater(() -> {
                Notifications.create().title("Approved").text("The packet has been approved").show();
                list.getItems().remove(this.pacchettoEntity);
                list.refresh();
            });
        }).start();
    }
}
