package controller;

import model.DBManager;
import model.dao.DAO;
import model.dao.DipendenteDaoHibernate;
import model.entityDB.DipendenteEntity;
import org.hibernate.exception.JDBCConnectionException;

import java.util.List;

/**
 * Controller class for login use case.
 */
public class LoginController {

    /**
     * Main controller interface.
     * @param credentials: submitted user Credentials
     * @return DipendenteEntity: a DB entity representing the logging employee
     * @throws JDBCConnectionException: DB connection failure
     * @throws Exception: incomplete/invalid input management via Exceptions
     */
    public static DipendenteEntity handle(Credentials credentials) throws Exception {
        //Incomplete/invalid credentials
        if (!credentials.areValid()) throw new Exception("Riempire tutti i campi obbligatori");

        List<DipendenteEntity> entities = null;
        DAO dao = DipendenteDaoHibernate.instance();

        try {
            DBManager.initHibernate();
            entities = (List<DipendenteEntity>) dao.getByCriteria(credentials.getQuery()); //DB interaction
        }
        finally { DBManager.shutdown(); } //always shut the DB down

        //no luck; there was no match
        if (entities == null || entities.size() == 0)
            throw new Exception("Dipendente non registrato");

        return entities.get(0);
    }

    /**
     * Utility class for credentials management.
     */
    public static class Credentials {

        //User name, surname and password
        private String name, surname, password;

        public Credentials(String name, String surname, String password) {
            this.name = name;
            this.surname = surname;
            this.password = password;
        }

        /**
         * Utility method for credential validation/coherence checking
         * @return boolean: whether or not the application can go on logging the user in
         */
        public boolean areValid() {
            return name != null && surname != null && password != null &&
                    !"".equals(name) && !"".equals(surname) && !"".equals(password);
        }

        /**
         * Utility method returning a query wrapping up submitted credentials.
         * @return String: ad-hoc DB query
         */
        public String getQuery() {
            return "where nome='" + name + "' AND cognome='" + surname + "' AND password_login='" + password + "'";
        }
    }
}
