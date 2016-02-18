package controller.admin;

import javafx.scene.Node;
import model.DBManager;
import model.dao.PacchettoDaoHibernate;
import model.dao.DAO;
import model.dao.ProdottoDaoHibernate;
import model.entityDB.PacchettoEntity;
import model.entityDB.ProdottoEntity;
import view.admin.PacketApproveView;

/** Controller for a packet prototype approval use case;
 * it updates the packet state according to the Admin's decision. **/
public class ApproveController {

    public static Node getView() { return new PacketApproveView(); }

    /** @param entity; the packet whose state has to be updated
     *  @param state; integer representing the packet future state **/
    public static boolean handle(PacchettoEntity entity, int state) {

        boolean result;
        if (state == 0) result = delete(entity); //packet was rejected and has to be deleted
        else {
            entity.setStato(state);
            result = update(entity); //packet update
        }
        return result;
    }

    /** @param entity; PacchettoEntity to be deleted from DB
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

    /** @param entity; PacchettoEntity to be updated
     *  @return boolean; whether or not the operation was successful **/
    private static boolean update(PacchettoEntity entity) {
        DAO dao = PacchettoDaoHibernate.instance();
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
