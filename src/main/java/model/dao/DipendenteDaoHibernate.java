package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.DipendenteEntity;
import org.hibernate.Session;

import java.util.List;

public class DipendenteDaoHibernate implements DAO {

    private static DAO singleton;

    protected DipendenteDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new DipendenteDaoHibernate();
        return singleton;
    }

    public synchronized List<DipendenteEntity> getAll(){
        Session session = DBManager.getSession();

        List<DipendenteEntity> dipendentiEntities = session.createQuery("from DipendenteEntity").list();
        session.close();
        return dipendentiEntities;
    }

    public synchronized List<DipendenteEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<DipendenteEntity> dipendentiEntities = session.createQuery("from DipendenteEntity " + where).list();
        session.close();
        if (dipendentiEntities.isEmpty()) {
            return null;
        } else {
            return dipendentiEntities;
        }
    }

    @Override
    public synchronized List<DipendenteEntity> getByQuery(String query) {
        Session session = DBManager.getSession();

        List<DipendenteEntity> dipendentiEntities = session.createQuery(query).list();
        session.close();
        if (dipendentiEntities.isEmpty()) {
            return null;
        } else {
            return dipendentiEntities;
        }
    }

    @Override
    public synchronized DipendenteEntity getById(int id) {
        Session session = DBManager.getSession();

        DipendenteEntity dipendenteEntity = (DipendenteEntity) session.createQuery("from DipendenteEntity where id = " + id).list().get(0);
        session.close();
        if (dipendenteEntity == null){
            return null;
        } else {
            return dipendenteEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {

        DipendenteEntity dipendenteEntity = (DipendenteEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(dipendenteEntity);
        session.getTransaction().commit();
        session.close();

        return dipendenteEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {

        DipendenteEntity dipendenteEntity = (DipendenteEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(dipendenteEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        DipendenteEntity dipendenteEntity = (DipendenteEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(dipendenteEntity);
        session.getTransaction().commit();
        session.close();
    }
}
