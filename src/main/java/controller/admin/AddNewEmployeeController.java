package controller.admin;

import javafx.scene.Node;
import model.DBManager;
import model.dao.DipendenteDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendenteEntity;
import view.admin.AddNewEmployeeView;

/*** Controller class for the employee additions use case. ***/

public class AddNewEmployeeController {

    public static Node getView() { return new AddNewEmployeeView(); }

    /** @param name; String representing the employee's name
     *  @param surname; String representing the employee's surname
     *  @param password; String representing the employee's password
     *  @param role; String representing the employee's role into TripBroker Agency
     *  @param mail; String representing the employee's mail address
     *  @return boolean: whether or not the operation was successful;
     *  @throws Exception: incomplete/invalid submitted input is handled via Exception
     *  **/
    public static boolean handle(String name, String surname, String password, String role, String mail) throws Exception {

        //Checking for input coherence
        if (!checkStrings(name, surname, password, role, mail)) throw new Exception("Riempire tutti i campi obbligatori");

        //Employee entity
        DipendenteEntity entity = new DipendenteEntity();
        entity.setNome(name);
        entity.setCognome(surname);
        entity.setPasswordLogin(password);
        entity.setRuolo(role);
        entity.setMail(mail);

        DAO dao = DipendenteDaoHibernate.instance();

        try {
            //DB interaction
            DBManager.initHibernate();
            dao.store(entity);
        }
        catch (Exception e) {
            //Something went wrong; catching the Exception and returning false
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut DB when done

        return true; //successful operation
    }

    /**
     * Utility method for input validation.
     * @param str: String to check.
     * @return boolean
     */
    private static boolean checkString(String str) { return str != null && !"".equals(str); }

    /**
     * Utility method for input validation.
     * @param strings: Strings to check.
     * @return boolean
     */
    private static boolean checkStrings(String... strings) {
        for (String str: strings) if (!checkString(str)) return false;
        return true;
    }
}
