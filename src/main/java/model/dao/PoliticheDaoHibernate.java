package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PoliticheEntity;
import org.hibernate.Session;

import java.util.List;

public class PoliticheDaoHibernate implements DAO {

    public List<PoliticheEntity> getAll(){
        Session session = DBManager.getSession();

        List<PoliticheEntity> politicheEntities = session.createQuery("from PoliticheEntity").list();
        session.close();
        return politicheEntities;
    }

    public void store(AbstractEntity entity) {

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity) {

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }
}
