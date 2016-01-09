package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.ViaggioEntity;
import org.hibernate.Session;

import java.util.List;

public class ViaggioDaoHibernate extends OffertaDaoHibernate {

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


    public synchronized List<ViaggioEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<ViaggioEntity> viaggioEntities = session.createQuery("from ViaggioEntity "+where).list();
        session.close();
        if(viaggioEntities.isEmpty()){
            return null;
        }
        else{
            return viaggioEntities;
        }
    }

    @Override
    public synchronized List<ViaggioEntity> getByQuery(String query) {
        Session session = DBManager.getSession();

        List<ViaggioEntity> viaggioEntities = session.createQuery(query).list();
        session.close();
        if(viaggioEntities.isEmpty()){
            return null;
        }
        else{
            return viaggioEntities;
        }

    }

    @Override
    public synchronized ViaggioEntity getById(int id){
        Session session = DBManager.getSession();

        ViaggioEntity viaggioEntity = (ViaggioEntity) session.createQuery("from ViaggioEntity where id = " + id).list().get(0);
        session.close();
        if (viaggioEntity == null){
            return null;
        } else {
            return viaggioEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {

        ViaggioEntity viaggioEntity = (ViaggioEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(viaggioEntity);
        session.getTransaction().commit();
        session.close();

        return viaggioEntity.getId();
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
