package controller;


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;
import view.material.ProgressCircle;

public class DeleteButtonController implements EventHandler<MouseEvent>{
    TableView<DipendentiEntity> list;
    DipendentiEntity entity;

    public DeleteButtonController(TableView<DipendentiEntity> list, DipendentiEntity entity){
        this.list = list;
        this.entity = entity;
    }

    @Override
    public void handle(MouseEvent event) {

        new Thread(() -> {
            DAO dao = DipendentiDaoHibernate.instance();
            DBManager.initHibernate();
            dao.delete(entity);
            DBManager.shutdown();

            Platform.runLater(() -> {
                Notifications.create().title("Deleted").text("The employee has been deleted").show();
                list.getItems().remove(entity);
                list.refresh();

            });
        }).start();
    }
}
