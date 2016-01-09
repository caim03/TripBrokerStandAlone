package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ProdottoEntity;
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
    public synchronized List<? extends AbstractEntity> getAll() {

        return null;
    }

    @Override
    public synchronized List<? extends AbstractEntity> getByCriteria(String where) {
        return null;
    }

    @Override
    public List<PrenotazioneEntity> getByQuery(String query) {
        return null;
    }

    @Override
    public synchronized PrenotazioneEntity getById(int id) {
        Session session = DBManager.getSession();

        PrenotazioneEntity prenotazioneEntity = (PrenotazioneEntity) session.createQuery("from PrenotazioneEntity where id = " + id).list().get(0);
        session.close();
        if (prenotazioneEntity == null){
            return null;
        } else {
            return prenotazioneEntity;
        }
    }

    @Override
    public synchronized int store(AbstractEntity entity) throws ClassCastException {

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();

        return ((PrenotazioneEntity) entity).getViaggioId();
    }

    @Override
    public synchronized void delete(AbstractEntity absEntity) throws ClassCastException {

        PrenotazioneEntity entity = (PrenotazioneEntity) absEntity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public synchronized void update(AbstractEntity entity) throws ClassCastException {

    }
}
