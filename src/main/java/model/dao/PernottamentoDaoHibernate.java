package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PernottamentoEntity;
import org.hibernate.Session;

import java.util.List;

public class PernottamentoDaoHibernate implements DAO {

    public List<PernottamentoEntity> getAll(){
        Session session = DBManager.getSession();

        List<PernottamentoEntity> pernottamentoEntities = session.createQuery("from PernottamentoEntity").list();
        session.close();
        return pernottamentoEntities;
    }

    public void store(AbstractEntity entity) {

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity){

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        PernottamentoEntity pernottamentoEntity = (PernottamentoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
