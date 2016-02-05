package controller.admin;


import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;

public class ModifyEmployeeController {

    public static boolean handle(DipendentiEntity entity) throws Exception {
        if (!checkEmployee(entity)) throw new Exception("Riempire tutti i campi obbligatori");
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
