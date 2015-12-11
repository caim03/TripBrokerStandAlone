package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.EventoEntity;
import org.hibernate.Session;
import java.util.List;

public class EventoDaoHibernate implements DAO {

    private static DAO singleton;

    protected EventoDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new EventoDaoHibernate();
        return singleton;
    }

    public synchronized List<EventoEntity> getAll(){
        Session session = DBManager.getSession();

        List<EventoEntity> eventoEntities = session.createQuery("from EventoEntity").list();
        session.close();
        return eventoEntities;
    }


    public synchronized List<EventoEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<EventoEntity> eventoEntities = session.createQuery("from EventoEntity "+where).list();
        session.close();
        if (eventoEntities.isEmpty()){
            return null;
        }
        else{
            return eventoEntities;
        }
    }

    public synchronized int store(AbstractEntity entity) {

        EventoEntity eventoEntity = (EventoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(eventoEntity);
        session.getTransaction().commit();
        session.close();

        return eventoEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {

        EventoEntity eventoEntity = (EventoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        EventoEntity eventoEntity = (EventoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
