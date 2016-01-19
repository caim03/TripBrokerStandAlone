package controller;


import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.dao.DAO;
import model.entityDB.DipendentiEntity;

/*** This controller receives data from 'AddNewEmployeeView', creates a new employee
 *   and adds it into DataBase ***/

public class AddNewEmployeeController {

    public static void handle(String name, String surname, String password, String role, String mail){
        /** @param String; this string represents the name of the new dependent
         *  @param String; this string represents the surname of the new dependent
         *  @param String; this string represents the password of the new dependent
         *  @param String; this string represents the role of the new dependent
         *  @param String; this string represents the mail of the new dependent **/

        // new dependent
        DipendentiEntity entity = new DipendentiEntity();

        // set the parameters
        entity.setNome(name);
        entity.setCognome(surname);
        entity.setPasswordLogin(password);
        entity.setRuolo(role);
        entity.setMail(mail);

        // use dao of dependent to add it into DB
        DAO dao = DipendentiDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }
}
