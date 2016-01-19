package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.PoliticheEntity;
import org.hibernate.Session;

import java.util.List;

public class PoliticheDaoHibernate implements DAO {

    private static DAO singleton;

    protected PoliticheDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new PoliticheDaoHibernate();
        return singleton;
    }

    public synchronized List<PoliticheEntity> getAll(){
        /** @result List; return a list of PoliticheEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PoliticheEntity> politicheEntities = session.createQuery("from PoliticheEntity").list();

        // close connection
        session.close();
        return politicheEntities;
    }


    public synchronized List<PoliticheEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of PoliticheEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PoliticheEntity> politicheEntities = session.createQuery("from PoliticheEntity "+where).list();

        // close connection
        session.close();
        if(politicheEntities.isEmpty()){
            return null;
        }
        else {
            return politicheEntities;
        }
    }

    @Override
    public List<PoliticheEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of PoliticheEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PoliticheEntity> politicheEntities = session.createQuery(query).list();

        // close connection
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
        /** @param int; this integer represents the identifier of PoliticheEntity to be retrieved
         *  @result PoliticheEntity; return a PoliticheEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        PoliticheEntity politicheEntity = (PoliticheEntity) session.createQuery("from PoliticheEntity Entity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (politicheEntity == null){
            return null;
        } else {
            return politicheEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(politicheEntity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return politicheEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be deleted to the DataBase **/

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // delete object
        session.delete(politicheEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        PoliticheEntity politicheEntity = (PoliticheEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update object
        session.update(politicheEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
