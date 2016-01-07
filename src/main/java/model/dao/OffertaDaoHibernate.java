package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import org.hibernate.Session;
import model.entityDB.OffertaEntity;
import view.desig.OffersTabPane;

import java.util.List;

public class OffertaDaoHibernate extends ProdottoDaoHibernate {

    private static DAO singleton;

    protected OffertaDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new OffertaDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<? extends OffertaEntity> getAll() {
        Session session = DBManager.getSession();

        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity").list();
        session.close();
        return offertaEntities;
    }

    @Override
    public synchronized List<? extends OffertaEntity> getByCriteria(String where) {

        Session session = DBManager.getSession();

        List<OffertaEntity> offertaEntities = session.createQuery("from OffertaEntity "+ where).list();
        session.close();
        if(offertaEntities.isEmpty()){
            return null;
        }
        else{
            return offertaEntities;
        }
    }

    @Override
    public  synchronized OffertaEntity getById(int id) {
        Session session = DBManager.getSession();

        OffertaEntity offertaEntity = (OffertaEntity) session.createQuery("from OffertaEntity where id = " + id).list().get(0);
        if (offertaEntity == null){
            return null;
        }
        else {
            return offertaEntity;
        }
    }

    @Override
    public synchronized int store(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(offertaEntity);
        session.getTransaction().commit();
        session.close();

        return offertaEntity.getId();
    }

    @Override
    public synchronized void update(AbstractEntity entity) {

        OffertaEntity offertaEntity = (OffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(offertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
