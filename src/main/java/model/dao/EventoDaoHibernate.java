package model.dao;

import model.DBManager;
import model.daoInterface.EventoDaoInterface;
import model.entityDB.EventoEntity;
import org.hibernate.Session;
import java.util.List;

public class EventoDaoHibernate implements EventoDaoInterface{

    public List<EventoEntity> getAll(){
        Session session = DBManager.getSession();

        List<EventoEntity> eventoEntities = session.createQuery("from EventoEntity").list();
        session.close();
        return eventoEntities;
    }

    public void store(EventoEntity eventoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(EventoEntity eventoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(EventoEntity eventoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
