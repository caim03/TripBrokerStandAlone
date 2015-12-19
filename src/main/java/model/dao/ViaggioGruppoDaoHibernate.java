package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.ProdottoEntity;
import model.entityDB.ViaggioGruppoEntity;
import org.hibernate.Session;

import java.util.List;

public class ViaggioGruppoDaoHibernate implements DAO {

    private static DAO singleton;

    protected ViaggioGruppoDaoHibernate() {}
    public static DAO instance() {

        if (singleton == null) singleton = new ViaggioGruppoDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<? extends AbstractEntity> getAll() {

        Session session = DBManager.getSession();

        List<ViaggioGruppoEntity> entities = session.createQuery("from ViaggioGruppoEntity").list();
        session.close();
        return entities;
    }

    @Override
    public synchronized List<? extends AbstractEntity> getByCriteria(String where) {

        Session session = DBManager.getSession();
        List<ViaggioGruppoEntity> entities = session.createQuery("from ViaggioGruppoEntity " + where).list();
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;  // return first
    }

    @Override
    public int store(AbstractEntity entity) throws ClassCastException {

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();

        return ((ProdottoEntity) entity).getId();
    }

    @Override
    public void delete(AbstractEntity entity) throws ClassCastException {

        Session session = DBManager.getSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(AbstractEntity entity) throws ClassCastException {

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();

        session.close();
    }
}
