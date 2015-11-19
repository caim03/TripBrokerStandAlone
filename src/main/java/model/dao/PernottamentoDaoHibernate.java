package model.dao;

import model.DBManager;
import model.daoInterface.PernottamentoDaoInterface;
import model.entityDB.PernottamentoEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Christian on 18/11/2015.
 */
public class PernottamentoDaoHibernate implements PernottamentoDaoInterface{

    public List<PernottamentoEntity> getAll(){
        Session session = DBManager.getSession();

        List<PernottamentoEntity> pernottamentoEntities = session.createQuery("from PernottamentoEntity").list();
        session.close();
        return pernottamentoEntities;
    }

    public void store(PernottamentoEntity pernottamentoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(PernottamentoEntity pernottamentoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(PernottamentoEntity pernottamentoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
