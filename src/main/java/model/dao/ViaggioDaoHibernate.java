package model.dao;

import model.DBManager;
import model.daoInterface.ViaggioDaoInterface;
import model.entityDB.ViaggioEntity;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Christian on 18/11/2015.
 */
public class ViaggioDaoHibernate implements ViaggioDaoInterface{

    public List<ViaggioEntity> getAll(){
        Session session = DBManager.getSession();

        List<ViaggioEntity> viaggioEntities = session.createQuery("from ViaggioEntity").list();
        session.close();
        return viaggioEntities;
    }

    public void store(ViaggioEntity viaggioEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(viaggioEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(ViaggioEntity viaggioEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(viaggioEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(ViaggioEntity viaggioEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(viaggioEntity);
        session.getTransaction().commit();
        session.close();
    }
}
