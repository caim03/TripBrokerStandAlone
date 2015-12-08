package controller;

import model.DBManager;
import model.dao.DipendentiDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.DipendentiEntity;

import java.util.List;

public class LoginController {

    public static class Credentials {

        String name, surname, password;

        public Credentials(String name, String surname, String password) {

            this.name = name;
            this.surname = surname;
            this.password = password;
        }

        public boolean areValid() {
            return name != null && surname != null && password != null &&
                   !"".equals(name) && !"".equals(surname) && !"".equals(password);
        }

        public String getQuery() {
            return "where nome='" + name + "' AND cognome='" + surname + "' AND password_login='" + password + "'";
        }
    }

    public static AbstractEntity handle(Credentials credentials) {

        System.out.println("HANDLING");

        if (!credentials.areValid()) return null;

        System.out.println("VALID");

        List<DipendentiEntity> dipendentiEntity;
        DAO dao = DipendentiDaoHibernate.instance();
        DBManager.initHibernate();
        dipendentiEntity = (List<DipendentiEntity>) (dao.getByCriteria(credentials.getQuery()));
        DBManager.shutdown();
        if (dipendentiEntity == null) return AbstractEntity.getInvalidEntity();
        else return dipendentiEntity.get(0);
    }
}
