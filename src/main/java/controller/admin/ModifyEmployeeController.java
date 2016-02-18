package controller.admin;

import javafx.scene.Node;
import model.DBManager;
import model.dao.DipendenteDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendenteEntity;
import view.admin.ManageRolesView;

/**
 * Controller class for employee credential management use case.
 */
public class ModifyEmployeeController {

    public static Node getView() { return new ManageRolesView(); }

    /**
     * @param entity: DipendenteEntity instance to update
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    public static boolean handle(DipendenteEntity entity) throws Exception {
        if (!checkEmployee(entity)) throw new Exception("Riempire tutti i campi obbligatori");
        try {
            DBManager.initHibernate();
            update(entity);
        }
        catch (Exception e) {
            //failure
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut the DB down

        return true;
    }

    /**
     * Utility method realizing the actual update.
     * @param entity: DipendenteEntity to update
     */
    private static void update(DipendenteEntity entity) {
        DAO dao = DipendenteDaoHibernate.instance();
        dao.update(entity);
    }

    /**
     * Utility method for input validation.
     * @param entity: DipendenteEntity whose fields have to be checked before updating
     * @return boolean
     */
    private static boolean checkEmployee(DipendenteEntity entity) {
        return checkStrings(entity.getNome(), entity.getCognome(), entity.getRuolo(), entity.getPasswordLogin(), entity.getMail());
    }

    /**
     * Utility method for input validation.
     * @param string: String to check.
     * @return boolean
     */
    private static boolean checkString(String string) { return string != null && !"".equals(string); }

    /**
     * Utility method for input validation.
     * @param strings: Strings to check.
     * @return boolean
     */
    private static boolean checkStrings(String... strings) {
        for (String string : strings) if (!checkString(string)) return false;
        return true;
    }
}
