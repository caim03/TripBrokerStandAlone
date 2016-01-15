package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.ServiceRegistry;

import java.util.concurrent.locks.ReentrantLock;

/*** DBManager class is the main Hibernate DataBase interface;
 *   it contains important methods used to: initialize Hibernate, get new session and close connection.
 *
 *   This class uses a lock to prevent simultaneous access.
 *
 *   The Configuration object provides two keys components:
 *      1. Database Connection (Session object)
 *      2. Class Mapping Setup (xml files)
 *
 *   The SessionFactory object configures Hibernate for the application, and it is usually created at
 *   the start of application and there is only one object for database
 *
 *   A session is used to get a physical connection with a database; it is a lightweight object and it must be
 *   instantiated each time an interaction is needed with the databases.
 *   The session objects should not be kept open for a long time because they are not usually thread safe
 *   and they should be created and destroyed them as needed
 *
 *   The main purpose of a ServiceRegistry object is to hold, manage and provide access to services ***/

public class DBManager {
    private static Configuration configuration = null;
    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;
    private static ReentrantLock lock = new ReentrantLock();

    public static void initHibernate() throws JDBCConnectionException {
        /* This method is used to initialize and configure Hibernate variables;
         * considering that Configuration and SessionFactory are heavyweight object,
         * they are initialized only the first time (Singleton), and they are used for the
         * entire duration of the application */

        lock.lock();

        // configuration is an heavyweight object
        if (configuration == null) {
            configuration = new Configuration();
            configuration.configure();
        }

        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();

        // sessionFactory is an heavyweight object
        if (sessionFactory == null) {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        //sessionFactory.close();
        lock.unlock();
    }
}