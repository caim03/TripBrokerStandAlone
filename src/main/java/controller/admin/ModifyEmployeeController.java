package controller.admin;


import controller.exception.EmptyFormException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;
import org.controlsfx.control.Notifications;
import view.material.MaterialSpinner;

public class ModifyEmployeeController {

    public static boolean handle(DipendentiEntity entity) throws EmptyFormException {
        if (!checkEmployee(entity)) throw new EmptyFormException();
        try {
            DBManager.initHibernate();
            update(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }

    private static void update(DipendentiEntity entity) {
        DAO dao = DipendentiDaoHibernate.instance();
        dao.update(entity);
    }

    private static boolean checkEmployee(DipendentiEntity entity) {
        return checkStrings(entity.getNome(), entity.getCognome(), entity.getRuolo(), entity.getPasswordLogin(), entity.getMail());
    }

    private static boolean checkString(String string) { return string != null && !"".equals(string); }
    private static boolean checkStrings(String... strings) {
        for (String string : strings) if (!checkString(string)) return false;
        return true;
    }
}
