package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.PacchettoOffertaEntity;
import model.entityDB.PernottamentoEntity;
import org.hibernate.Session;

import java.util.List;

public class PacchettoOffertaDaoHibernate implements DAO {

    public List<PacchettoOffertaEntity> getAll(){
        Session session = DBManager.getSession();

        List<PacchettoOffertaEntity> pacchettoOffertaEntities = session.createQuery("from PacchettoOffertaEntity").list();
        session.close();
        return pacchettoOffertaEntities;
    }

    public void store(AbstractEntity entity){

        PacchettoOffertaEntity pacchettoOffertaEntity = (PacchettoOffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity) {

        PacchettoOffertaEntity pacchettoOffertaEntity = (PacchettoOffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        PacchettoOffertaEntity pacchettoOffertaEntity = (PacchettoOffertaEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
