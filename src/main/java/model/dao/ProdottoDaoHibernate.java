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

    public synchronized List<ProdottoEntity> getAll(){
        Session session = DBManager.getSession();

        List<ProdottoEntity> prodottoEntities = session.createQuery("from ProdottoEntity").list();
        session.close();
        if (prodottoEntities.isEmpty()){
            return null;
        }
        return prodottoEntities;
    }

    @Override
    public synchronized List<ProdottoEntity> getByCriteria(String where) {

        Session session = DBManager.getSession();

        List<ProdottoEntity> prodottoEntities = session.createQuery("from ProdottoEntity " + where).list();
        session.close();
        if (prodottoEntities.isEmpty()) return null;
        else return prodottoEntities;
    }

    public synchronized int store(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(prodottoEntity);
        session.getTransaction().commit();
        session.close();

        return prodottoEntity.getId();
    }

    public synchronized void delete(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
