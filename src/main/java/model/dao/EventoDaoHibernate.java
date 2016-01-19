package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.EventoEntity;
import org.hibernate.Session;
import java.util.List;

public class EventoDaoHibernate extends OffertaDaoHibernate {

    private static DAO singleton;

    protected EventoDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new EventoDaoHibernate();
        return singleton;
    }

    public synchronized List<EventoEntity> getAll(){
        /** @result List; return the list of EventoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<EventoEntity> eventoEntities = session.createQuery("from EventoEntity").list();

        // close connection
        session.close();
        return eventoEntities;
    }


    public synchronized List<EventoEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of EventoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<EventoEntity> eventoEntities = session.createQuery("from EventoEntity " + where).list();

        // close connection
        session.close();
        if (eventoEntities.isEmpty()){
            return null;
        }
        else{
            return eventoEntities;
        }
    }

    @Override
    public synchronized List<EventoEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of EventoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<EventoEntity> eventoEntities = session.createQuery(query).list();

        // close connection
        session.close();
        if (eventoEntities.isEmpty()){
            return null;
        }
        else {
            return eventoEntities;
        }
    }

    @Override
    public synchronized EventoEntity getById(int id) {
        /** @param int; this integer represents the identifier of EventoEntity to be retrieved
         *  @result List; return the list of EventoEntity, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        EventoEntity eventoEntity = (EventoEntity) session.createQuery("from EventoEntity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (eventoEntity == null){
            return null;
        } else {
            return eventoEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        EventoEntity eventoEntity = (EventoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(eventoEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();

        return eventoEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be deleted to the DataBase **/

        EventoEntity eventoEntity = (EventoEntity) entity;

        // get session
        Session session = DBManager.getSession();

        session.beginTransaction();
        // delete the object
        session.delete(eventoEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        EventoEntity eventoEntity = (EventoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(eventoEntity);
        // commit transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
