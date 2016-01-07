package controller;


import javafx.scene.control.TableView;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;

public class DeleteButtonController {

    public static void handle(TableView<DipendentiEntity> tableView, DipendentiEntity entity) {

        DAO dao = DipendentiDaoHibernate.instance();
        DBManager.initHibernate();
        dao.delete(entity);
        DBManager.shutdown();
    }
}
