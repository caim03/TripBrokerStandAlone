package controller.admin;


import javafx.scene.control.TableView;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;

/*** This controller delete a dependent from DataBase ***/

public class DeleteButtonController {

    /**
     * @param entity ; the dependent that must be deleted   **/
    public static boolean handle(DipendentiEntity entity) {

        try {
            DAO dao = DipendentiDaoHibernate.instance();
            DBManager.initHibernate();
            // delete the employee
            dao.delete(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        // refresh table
        return true;
    }
}
