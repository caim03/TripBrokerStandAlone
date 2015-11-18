package model.dao;

import model.DBManager;
import model.entityDB.EventoEntity;
import org.hibernate.Session;
import java.util.List;

public class EventoDaoHibernate {

    public static List<EventoEntity> getAll(){
        Session session = DBManager.getSession();

        List<EventoEntity> eventoEntities = session.createQuery("from EventoEntity").list();
        session.close();
        return eventoEntities;
    }

    public static void store(EventoEntity eventoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(EventoEntity eventoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void update(EventoEntity eventoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(eventoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
