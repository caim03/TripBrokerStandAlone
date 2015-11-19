package model.dao;

import model.DBManager;
import model.daoInterface.DipendentiDaoInterface;
import org.hibernate.Session;
import model.entityDB.DipendentiEntity;
import java.util.List;

public class DipendentiDaoHibernate implements DipendentiDaoInterface{

    public List<DipendentiEntity> getAll(){
        Session session = DBManager.getSession();

        List<DipendentiEntity> dipendentiEntities = session.createQuery("from DipendentiEntity").list();
        session.close();
        return dipendentiEntities;
    }

    public void store(DipendentiEntity dipendentiEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(DipendentiEntity dipendentiEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(DipendentiEntity dipendentiEntity){
        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }
}
