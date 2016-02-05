package controller.admin;


import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;

/*** This controller receives data from 'AddNewEmployeeView', creates a new employee
 *   and adds it into DataBase ***/

public class AddNewEmployeeController {

    /** @param name; this string represents the name of the new dependent
     *  @param surname; this string represents the surname of the new dependent
     *  @param password; this string represents the password of the new dependent
     *  @param role; this string represents the role of the new dependent
     *  @param mail; this string represents the mail of the new dependent **/
    public static boolean handle(String name, String surname, String password, String role, String mail) throws Exception {

        if (!checkStrings(name, surname, password, role, mail)) throw new Exception("Riempire tutti i campi obbligatori");
        // new dependent
        DipendentiEntity entity = new DipendentiEntity();

        // set the parameters
        entity.setNome(name);
        entity.setCognome(surname);
        entity.setPasswordLogin(password);
        entity.setRuolo(role);
        entity.setMail(mail);

        DAO dao = DipendentiDaoHibernate.instance();

        try {
            // use dao of dependent to add it into DB
            DBManager.initHibernate();
            dao.store(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }

    private static boolean checkString(String str) { return str != null && !"".equals(str); }
    private static boolean checkStrings(String... strings) {
        for (String str: strings) if (!checkString(str)) return false;
        return true;
    }
}
