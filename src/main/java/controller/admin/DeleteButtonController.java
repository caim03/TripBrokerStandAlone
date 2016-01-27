package controller.admin;


import javafx.scene.control.TableView;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;

/*** This controller delete a dependent from DataBase ***/

public class DeleteButtonController {

    /** @param tableView; list of dependent; this param is used here to refresh the table
     *  @param entity; the dependent that must be deleted **/
    public static void handle(TableView<DipendentiEntity> tableView, DipendentiEntity entity) {

        DAO dao = DipendentiDaoHibernate.instance();
        DBManager.initHibernate();
        // delete the employee
        dao.delete(entity);
        DBManager.shutdown();

        // refresh table
        tableView.refresh();
    }
}
