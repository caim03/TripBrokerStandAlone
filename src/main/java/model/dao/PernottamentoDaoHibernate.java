package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.PrenotazioneEntity;
import org.hibernate.Session;

import java.util.List;

public class PernottamentoDaoHibernate extends OffertaDaoHibernate {

    private static DAO singleton;

    protected PernottamentoDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new PernottamentoDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<PernottamentoEntity> getAll(){
        /** @result List; return a list of PernottamentoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PernottamentoEntity> pernottamentoEntities = session.createQuery("from PernottamentoEntity").list();

        // close connection
        session.close();
        return pernottamentoEntities;
    }

    @Override
    public synchronized List<PernottamentoEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of PernottamentoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PernottamentoEntity> pernottamentoEntities = session.createQuery("from PernottamentoEntity "+where).list();

        // close connection
        session.close();
        if(pernottamentoEntities.isEmpty()){
            return null;
        }
        else {
            return pernottamentoEntities;
        }
    }

    @Override
    public synchronized List<PernottamentoEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of PernottamentoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PernottamentoEntity> pernottamentoEntities = session.createQuery(query).list();

        // close connection
        session.close();
        if(pernottamentoEntities.isEmpty()){
            return null;
        }
        else{
            return pernottamentoEntities;
        }

    }

    @Override
    public synchronized PernottamentoEntity getById(int id){
        /** @param int; this integer represents the identifier of PernottamentoEntity to be retrieved
         *  @result PernottamentoEntity; return a PernottamentoEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) session.createQuery("from PernottamentoEntity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (pernottamentoEntity == null){
            return null;
        } else {
            return pernottamentoEntity;
        }
    }

    @Override
    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(pernottamentoEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return pernottamentoEntity.getId();
    }

    @Override
    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(pernottamentoEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
