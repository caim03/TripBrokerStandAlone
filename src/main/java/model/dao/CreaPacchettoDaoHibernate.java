package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;

import java.util.List;

public class CreaPacchettoDaoHibernate extends ProdottoDaoHibernate {

    private static DAO singleton;

    protected CreaPacchettoDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new CreaPacchettoDaoHibernate();
        return singleton;
    }

    public synchronized List<CreaPacchettoEntity> getAll(){
        Session session = DBManager.getSession();

        List<CreaPacchettoEntity> creaPacchettoEntities = session.createQuery("from CreaPacchettoEntity").list();
        session.close();
        return creaPacchettoEntities;
    }

    public synchronized List<CreaPacchettoEntity> getByCriteria(String where) {

        Session session = DBManager.getSession();

        List<CreaPacchettoEntity> entities = session.createQuery("from CreaPacchettoEntity " + where).list();
        session.close();
        if (entities.isEmpty()) {
            return null;
        } else {
            return entities;  // return first
        }
    }

    public synchronized int store(AbstractEntity entity) {

        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(creaPacchettoEntity);
        session.getTransaction().commit();
        session.close();

        return creaPacchettoEntity.getId();
    }

    public synchronized void update(AbstractEntity entity) {

        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(creaPacchettoEntity);
        session.getTransaction().commit();

        session.close();
    }
}
