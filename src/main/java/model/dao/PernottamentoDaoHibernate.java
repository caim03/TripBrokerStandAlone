package model.dao;

import model.DBManager;
import model.entityDB.PernottamentoEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Christian on 18/11/2015.
 */
public class PernottamentoDaoHibernate {

    public static List<PernottamentoEntity> getAll(){
        Session session = DBManager.getSession();

        List<PernottamentoEntity> pernottamentoEntities = session.createQuery("from PernottamentoEntity").list();
        session.close();
        return pernottamentoEntities;
    }

    public static void store(PernottamentoEntity pernottamentoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(PernottamentoEntity pernottamentoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void update(PernottamentoEntity pernottamentoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(pernottamentoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
