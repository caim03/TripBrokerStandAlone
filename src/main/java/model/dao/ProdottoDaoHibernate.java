package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;

import java.util.List;

public class ProdottoDaoHibernate implements DAO {

    public List<ProdottoEntity> getAll(){
        Session session = DBManager.getSession();

        List<ProdottoEntity> prodottoEntities = session.createQuery("from ProdottoEntity").list();
        session.close();
        return prodottoEntities;
    }

    public void store(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        ProdottoEntity prodottoEntity = (ProdottoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(prodottoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
