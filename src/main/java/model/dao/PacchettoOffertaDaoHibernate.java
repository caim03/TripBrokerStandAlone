package model.dao;

import model.DBManager;
import model.daoInterface.PacchettoOffertaDaoInterface;
import model.entityDB.PacchettoOffertaEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Christian on 19/11/2015.
 */
public class PacchettoOffertaDaoHibernate implements PacchettoOffertaDaoInterface{

    public List<PacchettoOffertaEntity> getAll(){
        Session session = DBManager.getSession();

        List<PacchettoOffertaEntity> pacchettoOffertaEntities = session.createQuery("from PacchettoOffertaEntity").list();
        session.close();
        return pacchettoOffertaEntities;
    }

    public void store(PacchettoOffertaEntity pacchettoOffertaEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(PacchettoOffertaEntity pacchettoOffertaEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(PacchettoOffertaEntity pacchettoOffertaEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(pacchettoOffertaEntity);
        session.getTransaction().commit();
        session.close();
    }
}
