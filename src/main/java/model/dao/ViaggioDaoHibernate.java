package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.ViaggioEntity;
import org.hibernate.Session;

import java.util.List;

public class ViaggioDaoHibernate extends OffertaDaoHibernate {

    private static DAO singleton;

    protected ViaggioDaoHibernate() { }

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new ViaggioDaoHibernate();
        return singleton;
    }

    public synchronized List<ViaggioEntity> getAll(){
        /** @result List; return a list of ViaggioEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<ViaggioEntity> viaggioEntities = session.createQuery("from ViaggioEntity").list();

        // close connection
        session.close();
        return viaggioEntities;
    }


    public synchronized List<ViaggioEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of ViaggioEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<ViaggioEntity> viaggioEntities = session.createQuery("from ViaggioEntity "+ where).list();

        // close connection
        session.close();
        if(viaggioEntities.isEmpty()){
            return null;
        }
        else{
            return viaggioEntities;
        }
    }

    @Override
    public synchronized List<ViaggioEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of ViaggioEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<ViaggioEntity> viaggioEntities = session.createQuery(query).list();

        // close connection
        session.close();
        if(viaggioEntities.isEmpty()){
            return null;
        }
        else{
            return viaggioEntities;
        }

    }

    @Override
    public synchronized ViaggioEntity getById(int id){
        /** @param int; this integer represents the identifier of ViaggioEntity to be retrieved
         *  @result ViaggioEntity; return a ViaggioEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        ViaggioEntity viaggioEntity = (ViaggioEntity) session.createQuery("from ViaggioEntity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (viaggioEntity == null){
            return null;
        } else {
            return viaggioEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        ViaggioEntity viaggioEntity = (ViaggioEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(viaggioEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return viaggioEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be deleted to the DataBase **/

        ViaggioEntity viaggioEntity = (ViaggioEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // delete the object
        session.delete(viaggioEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        ViaggioEntity viaggioEntity = (ViaggioEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(viaggioEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
