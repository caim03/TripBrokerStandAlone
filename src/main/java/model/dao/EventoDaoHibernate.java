package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.EventoEntity;
import org.hibernate.Session;
import java.util.List;

public class EventoDaoHibernate implements DAO {

    public List<EventoEntity> getAll(){
        Session session = DBManager.getSession();

        List<EventoEntity> eventoEntities = session.createQuery("from EventoEntity").list();
        session.close();
        return eventoEntities;
    }

    public void store(AbstractEntity entity) {

        EventoEntity eventoEntity = (EventoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity) {

        EventoEntity eventoEntity = (EventoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        EventoEntity eventoEntity = (EventoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
