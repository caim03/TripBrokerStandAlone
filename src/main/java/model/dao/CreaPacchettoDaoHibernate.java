package model.dao;

import model.DBManager;
import model.daoInterface.CreaPacchettoDaoInterface;
import model.entityDB.CreaPacchettoEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Christian on 18/11/2015.
 */
public class CreaPacchettoDaoHibernate implements CreaPacchettoDaoInterface{

    public List<CreaPacchettoEntity> getAll(){
        Session session = DBManager.getSession();

        List<CreaPacchettoEntity> creaPacchettoEntities = session.createQuery("from CreaPacchettoEntity").list();
        session.close();
        return creaPacchettoEntities;
    }

    public void store(CreaPacchettoEntity creaPacchettoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(creaPacchettoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(CreaPacchettoEntity creaPacchettoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(creaPacchettoEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(CreaPacchettoEntity creaPacchettoEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(creaPacchettoEntity);
        session.getTransaction().commit();
        session.close();
    }
}
