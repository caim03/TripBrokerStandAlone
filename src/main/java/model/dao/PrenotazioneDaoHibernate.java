package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PrenotazioneEntity;
import org.hibernate.Session;

import java.util.List;

public class PrenotazioneDaoHibernate implements DAO {

    private static DAO singleton;

    protected PrenotazioneDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new PrenotazioneDaoHibernate();
        return singleton;
    }

    @Override
    public List<? extends AbstractEntity> getAll() {

        return null;
    }

    @Override
    public List<? extends AbstractEntity> getByCriteria(String where) {
        return null;
    }

    @Override
    public int store(AbstractEntity entity) throws ClassCastException {

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();

        return ((PrenotazioneEntity) entity).getViaggioId();
    }

    @Override
    public void delete(AbstractEntity entity) throws ClassCastException {

    }

    @Override
    public void update(AbstractEntity entity) throws ClassCastException {

    }
}
