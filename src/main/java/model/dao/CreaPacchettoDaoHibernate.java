package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.CreaPacchettoEntity;
import org.hibernate.Session;

import java.util.List;

public class CreaPacchettoDaoHibernate implements DAO{

    public List<CreaPacchettoEntity> getAll(){
        Session session = DBManager.getSession();

        List<CreaPacchettoEntity> creaPacchettoEntities = session.createQuery("from CreaPacchettoEntity").list();
        session.close();
        return creaPacchettoEntities;
    }

    public void store(AbstractEntity entity) {

        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(creaPacchettoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity) {

        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(creaPacchettoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        CreaPacchettoEntity creaPacchettoEntity = (CreaPacchettoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(creaPacchettoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
