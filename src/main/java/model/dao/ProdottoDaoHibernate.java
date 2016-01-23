package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;

import java.util.List;

public class ProdottoDaoHibernate implements DAO {

    private static DAO singleton;

    protected ProdottoDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new ProdottoDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<? extends ProdottoEntity> getAll() {
        /** @result List; return a list of ProdottoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<ProdottoEntity> entities = session.createQuery("from ProdottoEntity").list();
        // close connection
        session.close();

        if (entities.isEmpty()) return null;
        return entities;
    }

    @Override
    public synchronized List<? extends ProdottoEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of ProdottoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<ProdottoEntity> entities = session.createQuery("from ProdottoEntity " + where).list();

        // close connection
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public synchronized List<? extends ProdottoEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of ProdottoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<ProdottoEntity> entities = session.createQuery(query).list();
        // close connection
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public synchronized ProdottoEntity getById(int id) {
        /** @param int; this integer represents the identifier of ProdottoEntity to be retrieved
         *  @result ProdottoEntity; return a ProdottoEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        List<ProdottoEntity> entities = session.createQuery("from ProdottoEntity where id = " + id).list();
        // performs the query
        ProdottoEntity entity = entities.get(0);

        // close connection
        session.close();

        if (entity == null) return null;
        else return entity;
    }

    @Override
    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(prodottoEntity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return prodottoEntity.getId();
    }

    @Override
    public synchronized void delete(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be deleted to the DataBase **/

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // delete the object
        session.delete(prodottoEntity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }

    @Override
    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(prodottoEntity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
