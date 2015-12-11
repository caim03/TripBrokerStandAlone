package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import org.hibernate.Session;
import model.entityDB.OffertaEntity;
import java.util.List;

public class OffertaDaoHibernate implements DAO {

    private static DAO singleton;

    protected OffertaDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new OffertaDaoHibernate();
        return singleton;
    }

    public synchronized List<OffertaEntity> getAll() {
        Session session = DBManager.getSession();

        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity").list();
        session.close();
        return offertaEntities;
    }

    public synchronized List<OffertaEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity "+where).list();
        session.close();
        if(offertaEntities.isEmpty()){
            return null;
        }
        else{
            return offertaEntities;
        }
    }

    public synchronized int store(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(offertaEntity);
        session.getTransaction().commit();
        session.close();

        return offertaEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
