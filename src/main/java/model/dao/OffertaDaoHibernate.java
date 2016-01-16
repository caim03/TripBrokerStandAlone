package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import org.hibernate.Session;
import model.entityDB.OffertaEntity;
import view.desig.OffersTabPane;

import java.util.List;

public class OffertaDaoHibernate extends ProdottoDaoHibernate {

    private static DAO singleton;

    protected OffertaDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new OffertaDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<? extends OffertaEntity> getAll() {
        /** @result List; return a list of OffertaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity").list();

        // close connection
        session.close();
        return offertaEntities;
    }

    @Override
    public synchronized List<? extends OffertaEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of OffertaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity "+ where).list();

        // close connection
        session.close();
        if(offertaEntities.isEmpty()){
            return null;
        }
        else{
            return offertaEntities;
        }
    }

    @Override
    public synchronized List<? extends OffertaEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of OffertaEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<OffertaEntity> offertaEntities = session.createQuery(query).list();

        // close connection
        session.close();
        if(offertaEntities.isEmpty()){
            return null;
        }
        else{
            return offertaEntities;
        }
    }

    @Override
    public synchronized OffertaEntity getById(int id) {
        /** @param int; this integer represents the identifier of OffertaEntity to be retrieved
         *  @result OffertaEntity; return a OffertaEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        OffertaEntity offertaEntity = (OffertaEntity) session.createQuery("from OffertaEntity where id = " + id).list().get(0);
        if (offertaEntity == null){
            return null;
        }
        else {
            return offertaEntity;
        }
    }

    @Override
    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(offertaEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return offertaEntity.getId();
    }

    @Override
    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(offertaEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
