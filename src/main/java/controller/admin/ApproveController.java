package controller.admin;

import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.dao.DAO;
import model.dao.ProdottoDaoHibernate;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;

/** Controller for a packet prototype approval use case;
 * it updates the packet state according to the Admin's decision. **/
public class ApproveController {

    /** @param entity; the packet whose state has to be updated
     *  @param state; integer representing the packet future state **/
    public static boolean handle(CreaPacchettoEntity entity, int state) {

        boolean result;
        if (state == 0) result = delete(entity); //packet was rejected and has to be deleted
        else {
            entity.setStato(state);
            result = update(entity); //packet update
        }
        return result;
    }

    /** @param entity; CreaPacchettoEntity to be deleted from DB
     *  @return boolean; whether or not the operation was successful **/
    private static boolean delete(ProdottoEntity entity) {

        try {
            DAO dao = ProdottoDaoHibernate.instance();  //Primary key is set on ProdottoEntity table (ON DELETE CASCADE)
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

    /** @param entity; CreaPacchettoEntity to be updated
     *  @return boolean; whether or not the operation was successful **/
    private static boolean update(CreaPacchettoEntity entity) {
        DAO dao = CreaPacchettoDaoHibernate.instance();
        try {
            DBManager.initHibernate();
            dao.update(entity);
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
