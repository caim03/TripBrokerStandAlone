package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;

import java.util.List;

public class ProdottoDaoHibernate implements DAO {

    private static DAO singleton;

    protected ProdottoDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new ProdottoDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<? extends ProdottoEntity> getAll() {
        Session session = DBManager.getSession();

        List<ProdottoEntity> prodottoEntities = session.createQuery("from ProdottoEntity").list();
        session.close();
        if (prodottoEntities.isEmpty()){
            return null;
        }
        return prodottoEntities;
    }

    @Override
    public synchronized List<? extends ProdottoEntity> getByCriteria(String where) {

        Session session = DBManager.getSession();

        List<ProdottoEntity> prodottoEntities = session.createQuery("from ProdottoEntity " + where).list();
        session.close();
        if (prodottoEntities.isEmpty()) return null;
        else return prodottoEntities;
    }

    @Override
    public synchronized ProdottoEntity getById(int id) {
        Session session = DBManager.getSession();

        ProdottoEntity prodottoEntity = (ProdottoEntity) session.createQuery("from ProdottoEntity where id = " + id).list().get(0);
        session.close();
        if (prodottoEntity == null){
            return null;
        } else {
            return prodottoEntity;
        }
    }

    @Override
    public synchronized int store(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(prodottoEntity);
        session.getTransaction().commit();
        session.close();

        return prodottoEntity.getId();
    }

    @Override
    public synchronized void delete(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void update(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
