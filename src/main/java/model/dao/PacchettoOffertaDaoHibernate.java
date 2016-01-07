package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PacchettoOffertaEntity;
import model.entityDB.PernottamentoEntity;
import org.hibernate.Session;

import java.util.List;

public class PacchettoOffertaDaoHibernate implements DAO {

    private static DAO singleton;

    protected PacchettoOffertaDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new PacchettoOffertaDaoHibernate();
        return singleton;
    }

    public synchronized List<PacchettoOffertaEntity> getAll(){
        Session session = DBManager.getSession();

        List<PacchettoOffertaEntity> pacchettoOffertaEntities = session.createQuery("from PacchettoOffertaEntity").list();
        session.close();
        return pacchettoOffertaEntities;
    }

    public synchronized List<PacchettoOffertaEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<PacchettoOffertaEntity> pacchettoOffertaEntities = session.createQuery("from PacchettoOffertaEntity "+where).list();
        session.close();
        if(pacchettoOffertaEntities.isEmpty()){
            return null;
        }
        else{
            return pacchettoOffertaEntities;
        }
    }

    @Override
    public synchronized PacchettoOffertaEntity getById(int id) {
        Session session = DBManager.getSession();

        PacchettoOffertaEntity pacchettoOffertaEntity = (PacchettoOffertaEntity) session.createQuery("from PacchettoOffertaEntity where id = " + id).list().get(0);
        session.close();
        if (pacchettoOffertaEntity == null){
            return null;
        } else {
            return pacchettoOffertaEntity;
        }
    }

    public synchronized int store(AbstractEntity entity){

        PacchettoOffertaEntity pacchettoOffertaEntity = (PacchettoOffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();

        return pacchettoOffertaEntity.getIdPacchetto();
    }

    public synchronized void delete(AbstractEntity entity) {

        PacchettoOffertaEntity pacchettoOffertaEntity = (PacchettoOffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        PacchettoOffertaEntity pacchettoOffertaEntity = (PacchettoOffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
