package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.PrenotazioneEntity;
import org.hibernate.Session;

import java.util.List;

public class PrenotazioneDaoHibernate implements DAO {

    private static DAO singleton;

    protected PrenotazioneDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new PrenotazioneDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<? extends AbstractEntity> getAll() {
        /** @result List; return a list of PrenotazioneEntity, retrieved from the DataBase **/

        return null;
    }

    @Override
    public synchronized List<? extends AbstractEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of PrenotazioneEntity, retrieved from the DataBase **/

        return null;
    }

    @Override
    public List<PrenotazioneEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of PrenotazioneEntity, retrieved from the DataBase **/

        return null;
    }

    @Override
    public synchronized PrenotazioneEntity getById(int id) {
        /** @param int; this integer represents the identifier of PrenotazioneEntity to be retrieved
         *  @result PrenotazioneEntity; return a PrenotazioneEntity with the id used in the query **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        PrenotazioneEntity prenotazioneEntity = (PrenotazioneEntity) session.createQuery("from PrenotazioneEntity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (prenotazioneEntity == null){
            return null;
        } else {
            return prenotazioneEntity;
        }
    }

    @Override
    public synchronized int store(AbstractEntity entity) throws ClassCastException {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(entity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return ((PrenotazioneEntity) entity).getViaggioId();
    }

    @Override
    public synchronized void delete(AbstractEntity absEntity) throws ClassCastException {
        /** @param AbstractEntity; entity that must be deleted to the DataBase **/

        PrenotazioneEntity entity = (PrenotazioneEntity) absEntity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // delete the object
        session.delete(entity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }

    @Override
    public synchronized void update(AbstractEntity entity) throws ClassCastException {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/
    }
}
