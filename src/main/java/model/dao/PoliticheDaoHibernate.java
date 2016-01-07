package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PoliticheEntity;
import org.hibernate.Session;

import java.util.List;

public class PoliticheDaoHibernate implements DAO {

    private static DAO singleton;

    protected PoliticheDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new PoliticheDaoHibernate();
        return singleton;
    }

    public synchronized List<PoliticheEntity> getAll(){
        Session session = DBManager.getSession();

        List<PoliticheEntity> politicheEntities = session.createQuery("from PoliticheEntity").list();
        session.close();
        return politicheEntities;
    }


    public synchronized List<PoliticheEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<PoliticheEntity> politicheEntities = session.createQuery("from PoliticheEntity "+where).list();
        session.close();
        if(politicheEntities.isEmpty()){
            return null;
        }
        else {
            return politicheEntities;
        }
    }

    @Override
    public synchronized PoliticheEntity getById(int id) {
        Session session = DBManager.getSession();

        PoliticheEntity politicheEntity = (PoliticheEntity) session.createQuery("from PoliticheEntity Entity where id = " + id).list().get(0);
        session.close();
        if (politicheEntity == null){
            return null;
        } else {
            return politicheEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(politicheEntity);
        session.getTransaction().commit();
        session.close();

        return politicheEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }
}
