package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import org.hibernate.Session;
import model.entityDB.OffertaEntity;
import java.util.List;

public class OffertaDaoHibernate implements DAO {

    public List<OffertaEntity> getAll() {
        Session session = DBManager.getSession();

        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity").list();
        session.close();
        return offertaEntities;
    }

    public void store(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
