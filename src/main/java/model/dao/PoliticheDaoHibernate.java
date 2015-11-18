package model.dao;

import model.DBManager;
import model.entityDB.PoliticheEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Christian on 18/11/2015.
 */
public class PoliticheDaoHibernate {

    public static List<PoliticheEntity> getAll(){
        Session session = DBManager.getSession();

        List<PoliticheEntity> politicheEntities = session.createQuery("from PoliticheEntity").list();
        session.close();
        return politicheEntities;
    }

    public static void store(PoliticheEntity politicheEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(PoliticheEntity politicheEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void update(PoliticheEntity politicheEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(politicheEntity);
        session.getTransaction().commit();
        session.close();
    }
}
