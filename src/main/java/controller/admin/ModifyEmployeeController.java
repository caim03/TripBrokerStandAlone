package controller.admin;

import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;

/**
 * Controller class for employee credential management use case.
 */
public class ModifyEmployeeController {

    /**
     * @param entity: DipendentiEntity instance to update
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    public static boolean handle(DipendentiEntity entity) throws Exception {
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
     * @param entity: DipendentiEntity to update
     */
    private static void update(DipendentiEntity entity) {
        DAO dao = DipendentiDaoHibernate.instance();
        dao.update(entity);
    }

    /**
     * Utility method for input validation.
     * @param entity: DipendentiEntity whose fields have to be checked before updating
     * @return boolean
     */
    private static boolean checkEmployee(DipendentiEntity entity) {
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
