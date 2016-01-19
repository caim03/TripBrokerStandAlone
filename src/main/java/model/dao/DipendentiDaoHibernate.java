package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import org.hibernate.Session;
import model.entityDB.DipendentiEntity;
import java.util.List;

public class DipendentiDaoHibernate implements DAO {

    private static DAO singleton;

    protected DipendentiDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new DipendentiDaoHibernate();
        return singleton;
    }

    public synchronized List<DipendentiEntity> getAll(){
        Session session = DBManager.getSession();

        List<DipendentiEntity> dipendentiEntities = session.createQuery("from DipendentiEntity").list();
        session.close();
        return dipendentiEntities;
    }

    public synchronized List<DipendentiEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<DipendentiEntity> dipendentiEntities = session.createQuery("from DipendentiEntity " + where).list();
        session.close();
        if (dipendentiEntities.isEmpty()) {
            return null;
        } else {
            return dipendentiEntities;
        }
    }

    @Override
    public synchronized List<DipendentiEntity> getByQuery(String query) {
        Session session = DBManager.getSession();

        List<DipendentiEntity> dipendentiEntities = session.createQuery(query).list();
        session.close();
        if (dipendentiEntities.isEmpty()) {
            return null;
        } else {
            return dipendentiEntities;
        }
    }

    @Override
    public synchronized DipendentiEntity getById(int id) {
        Session session = DBManager.getSession();

        DipendentiEntity dipendentiEntity = (DipendentiEntity) session.createQuery("from DipendentiEntity where id = " + id).list().get(0);
        session.close();
        if (dipendentiEntity == null){
            return null;
        } else {
            return dipendentiEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(dipendentiEntity);
        session.getTransaction().commit();
        session.close();

        return dipendentiEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }
}
