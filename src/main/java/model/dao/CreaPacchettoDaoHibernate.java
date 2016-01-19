package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.CreaPacchettoEntity;
import org.hibernate.Session;

import java.util.List;

public class CreaPacchettoDaoHibernate extends ProdottoDaoHibernate {

    private static DAO singleton;

    protected CreaPacchettoDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new CreaPacchettoDaoHibernate();
        return singleton;
    }

    public synchronized List<CreaPacchettoEntity> getAll(){
        /** @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<CreaPacchettoEntity> creaPacchettoEntities = session.createQuery("from CreaPacchettoEntity").list();

        // close connection
        session.close();
        return creaPacchettoEntities;
    }

    public synchronized List<CreaPacchettoEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<CreaPacchettoEntity> entities = session.createQuery("from CreaPacchettoEntity " + where).list();

        // close connection
        session.close();
        if (entities.isEmpty()) {
            return null;
        } else {
            return entities;
        }
    }

    @Override
    public List<CreaPacchettoEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<CreaPacchettoEntity> entities = session.createQuery(query).list();

        // close connection
        session.close();
        if (entities.isEmpty()) {
            return null;
        } else {
            return entities;
        }
    }

    @Override
    public synchronized CreaPacchettoEntity getById(int id) {
        /** @param int; this integer represents the identifier of ProdottoEntity to be retrieved
         *  @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) session.createQuery("from CreaPacchettoEntity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (creaPacchettoEntity == null){
            return null;
        } else {
            return creaPacchettoEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(creaPacchettoEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return creaPacchettoEntity.getId();
    }

    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(creaPacchettoEntity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
