package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.ViaggioEntity;
import org.hibernate.Session;

import java.util.List;

public class ViaggioDaoHibernate implements DAO {

    private static DAO singleton;

    protected ViaggioDaoHibernate() { }

    public static DAO instance() {

        if (singleton == null) singleton = new ViaggioDaoHibernate();
        return singleton;
    }

    public synchronized List<ViaggioEntity> getAll(){
        Session session = DBManager.getSession();

        List<ViaggioEntity> viaggioEntities = session.createQuery("from ViaggioEntity").list();
        session.close();
        return viaggioEntities;
    }

    public synchronized void store(AbstractEntity entity) {

        ViaggioEntity viaggioEntity = (ViaggioEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(viaggioEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void delete(AbstractEntity entity) {

        ViaggioEntity viaggioEntity = (ViaggioEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(viaggioEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        ViaggioEntity viaggioEntity = (ViaggioEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(viaggioEntity);
        session.getTransaction().commit();
        session.close();
    }
}
