package model.dao;

import model.DBManager;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Christian on 18/11/2015.
 */
public class ProdottoDaoHibernate {

    public static List<ProdottoEntity> getAll(){
        Session session = DBManager.getSession();

        List<ProdottoEntity> prodottoEntities = session.createQuery("from ProdottoEntity").list();
        session.close();
        return prodottoEntities;
    }

    public static void store(ProdottoEntity prodottoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void delete(ProdottoEntity prodottoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public static void update(ProdottoEntity prodottoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
