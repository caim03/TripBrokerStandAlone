package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.PoliticaEntity;
import org.hibernate.Session;

import java.util.List;

public class PoliticaDaoHibernate implements DAO {

    private static DAO singleton;

    protected PoliticaDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new PoliticaDaoHibernate();
        return singleton;
    }

    public synchronized List<PoliticaEntity> getAll(){
        /** @result List; return a list of PoliticaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PoliticaEntity> politicheEntities = session.createQuery("from PoliticaEntity").list();

        // close connection
        session.close();
        return politicheEntities;
    }


    public synchronized List<PoliticaEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of PoliticaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PoliticaEntity> politicheEntities = session.createQuery("from PoliticaEntity "+where).list();

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
    public List<PoliticaEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of PoliticaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PoliticaEntity> politicheEntities = session.createQuery(query).list();

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
    public synchronized PoliticaEntity getById(int id) {
        /** @param int; this integer represents the identifier of PoliticaEntity to be retrieved
         *  @result PoliticaEntity; return a PoliticaEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        PoliticaEntity politicaEntity = (PoliticaEntity) session.createQuery("from PoliticaEntity Entity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (politicaEntity == null){
            return null;
        } else {
            return politicaEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        PoliticaEntity politicaEntity = (PoliticaEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(politicaEntity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return politicaEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be deleted to the DataBase **/

        PoliticaEntity politicaEntity = (PoliticaEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // delete object
        session.delete(politicaEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        PoliticaEntity politicaEntity = (PoliticaEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update object
        session.update(politicaEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
