package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.GruppoOffertaEntity;
import org.hibernate.Session;

import java.util.List;

public class GruppoOffertaDaoHibernate implements DAO {

    private static DAO singleton;

    protected GruppoOffertaDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new GruppoOffertaDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<GruppoOffertaEntity> getAll() {
        /** @result List; return a list of GruppoOffertaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<GruppoOffertaEntity> entities = session.createQuery("from GruppoOffertaEntity").list();

        // close connection
        session.close();

        return entities;
    }

    public synchronized List<GruppoOffertaEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of GruppoOffertaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<GruppoOffertaEntity> entities = session.createQuery("from GruppoOffertaEntity " + where).list();

        // close connection
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public synchronized List<GruppoOffertaEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of GruppoOffertaEntity, retrieved from the DataBase**/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<GruppoOffertaEntity> entities = session.createQuery(query).list();

        // close connection
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public synchronized GruppoOffertaEntity getById(int id) {
        /** @param int; this integer represents the identifier of GruppoOffertaEntity to be retrieved
         *  @result GruppoOffertaEntity; return a GruppoOffertaEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        GruppoOffertaEntity gruppoOffertaEntity = (GruppoOffertaEntity) session.createQuery("from GruppoOffertaEntity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (gruppoOffertaEntity == null){
            return null;
        }
        else {
            return gruppoOffertaEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entitie that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        GruppoOffertaEntity toBeStored = (GruppoOffertaEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(toBeStored);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return toBeStored.getIdGruppo();
    }

    public synchronized void delete(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be deleted to the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();
        session.beginTransaction();
        // delete the object
        session.delete(entity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();
        session.beginTransaction();
        // update the object
        session.update(entity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
