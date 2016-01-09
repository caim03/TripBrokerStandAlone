package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.GruppoOffertaEntity;
import org.hibernate.Session;

import java.util.List;

public class GruppoOffertaDaoHibernate implements DAO {

    private static DAO singleton;
    protected GruppoOffertaDaoHibernate() {}
    public static DAO instance() {

        if (singleton == null) singleton = new GruppoOffertaDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<GruppoOffertaEntity> getAll() {

        Session session = DBManager.getSession();
        List<GruppoOffertaEntity> entities = session.createQuery("from GruppoOffertaEntity").list();
        session.close();

        return entities;
    }

    public synchronized List<GruppoOffertaEntity> getByCriteria(String where) {

        Session session = DBManager.getSession();
        List<GruppoOffertaEntity> entities = session.createQuery("from GruppoOffertaEntity " + where).list();
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public synchronized List<GruppoOffertaEntity> getByQuery(String query) {
        Session session = DBManager.getSession();
        List<GruppoOffertaEntity> entities = session.createQuery(query).list();
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public synchronized GruppoOffertaEntity getById(int id) {
        Session session = DBManager.getSession();

        GruppoOffertaEntity gruppoOffertaEntity = (GruppoOffertaEntity) session.createQuery("from GruppoOffertaEntity where id = " + id).list().get(0);
        session.close();
        if (gruppoOffertaEntity == null){
            return null;
        }
        else {
            return gruppoOffertaEntity;
        }
    }

    public synchronized int store(AbstractEntity entity) {

        GruppoOffertaEntity toBeStored = (GruppoOffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(toBeStored);
        session.getTransaction().commit();
        session.close();

        return toBeStored.getIdGruppo();
    }

    public synchronized void delete(AbstractEntity entity) {

        Session session = DBManager.getSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        Session session = DBManager.getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }
}
