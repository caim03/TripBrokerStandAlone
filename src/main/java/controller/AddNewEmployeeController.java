package controller;


import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.DipendentiEntity;

public class AddNewEmployeeController {

    public static void handle(String name, String surname, String password, String role, String mail){

        DipendentiEntity entity = new DipendentiEntity();

        entity.setNome(name);
        entity.setCognome(surname);
        entity.setPasswordLogin(password);
        entity.setRuolo(role);
        entity.setMail(mail);

        DAO dao = DipendentiDaoHibernate.instance();
        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }
}
