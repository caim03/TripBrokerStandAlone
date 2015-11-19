package model.dao;

import model.DBManager;
import model.daoInterface.OffertaDaoInterface;
import org.hibernate.Session;
import model.entityDB.OffertaEntity;
import java.util.List;

public class OffertaDaoHibernate implements OffertaDaoInterface{

    public List<OffertaEntity> getAll() {
        Session session = DBManager.getSession();

        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity").list();
        session.close();
        return offertaEntities;
    }

    public void store(OffertaEntity offertaEntity) {
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(OffertaEntity offertaEntity) {
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(OffertaEntity offertaEntity) {
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
