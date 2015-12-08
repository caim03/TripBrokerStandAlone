package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;

import java.util.List;

public class PernottamentoDaoHibernate implements DAO {

    private static DAO singleton;

    protected PernottamentoDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new PernottamentoDaoHibernate();
        return singleton;
    }

    public synchronized List<PernottamentoEntity> getAll(){
        Session session = DBManager.getSession();

        List<PernottamentoEntity> pernottamentoEntities = session.createQuery("from PernottamentoEntity").list();
        session.close();
        return pernottamentoEntities;
    }

    @Override
    public List<PernottamentoEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<PernottamentoEntity> pernottamentoEntities = session.createQuery("from PernottamentoEntity "+where).list();
        session.close();
        if(pernottamentoEntities.isEmpty()){
            return null;
        }
        else {
            return pernottamentoEntities;
        }
    }

    public synchronized void store(AbstractEntity entity) {

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void delete(AbstractEntity entity){

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
