package controller;

import javafx.application.Platform;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.dao.ProdottoDaoHibernate;
import model.dao.DAO;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import org.controlsfx.control.Notifications;

/** This controller is used to approve a packet and change its state on DataBase;
 *  the state is changed to 'approved' in order to display it in the public catalog **/

public class ApproveController {

    public static void handle(CreaPacchettoEntity entity, int id) {

        String message;
        if (id == 0) message = delete(entity);
        else {
            entity.setStato(id);
            message = update(entity);
        }
        Platform.runLater(() -> Notifications.create().text(message).show());
    }

    private static String delete(ProdottoEntity entity) {

        try {
            DAO dao = ProdottoDaoHibernate.instance();  // Primary key is set on Prodotto Table (ON DELETE CASCADE)
            DBManager.initHibernate();
            dao.delete(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Errore interno al database";
        }
        finally { DBManager.shutdown(); }

        return "Il pacchetto è stato cancellato";
    }

    private static String update(CreaPacchettoEntity entity) {
        DAO dao = CreaPacchettoDaoHibernate.instance();
        try {
            DBManager.initHibernate();
            dao.update(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Errore interno al database";
        }
        finally { DBManager.shutdown(); }
        return entity.getStato() == 1 ? "Il pacchetto è stato approvato" :
                                        "Il pacchetto è stato rifiutato";
    }
}
