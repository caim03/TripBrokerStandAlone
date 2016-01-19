package controller;


import javafx.scene.control.TableView;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;

/*** This controller delete a dependent from DataBase ***/

public class DeleteButtonController {

    public static void handle(TableView<DipendentiEntity> tableView, DipendentiEntity entity) {
        /** @param TableView; list of dependent; this param is used here to refresh the table
         *  @param DipendentiEntity; the dependent that must be deleted **/

        DAO dao = DipendentiDaoHibernate.instance();
        DBManager.initHibernate();
        // delete the dependent
        dao.delete(entity);
        DBManager.shutdown();

        // refresh table
        tableView.refresh();
    }
}
