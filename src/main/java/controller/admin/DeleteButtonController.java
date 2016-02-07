package controller.admin;

import model.DBManager;
import model.dao.DAO;
import model.dao.DipendentiDaoHibernate;
import model.entityDB.DipendentiEntity;

/*** Controller class for employee deletion from DB ***/
public class DeleteButtonController {

    /**
     * @param entity ; employee to be deleted
     * @return boolean: whether or not the operation was successful
     * **/
    public static boolean handle(DipendentiEntity entity) {

        try {
            DAO dao = DipendentiDaoHibernate.instance();
            DBManager.initHibernate();
            dao.delete(entity);
        }
        catch (Exception e) {
            //failure
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut the DB down

        return true;
    }
}
