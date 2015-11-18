package model.dao;

import model.DBManager;
import org.hibernate.Session;
import model.entityDB.OffertaEntity;
import java.util.List;

public class OffertaDaoHibernate {

    public static List<OffertaEntity> getAll() {
        Session session = DBManager.getSession();

        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity").list();
        session.close();
        return offertaEntities;
    }

    public static void store(OffertaEntity offertaEntity) {
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(OffertaEntity offertaEntity) {
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void update(OffertaEntity offertaEntity) {
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
