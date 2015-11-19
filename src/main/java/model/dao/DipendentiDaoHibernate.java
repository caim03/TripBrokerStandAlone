package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import org.hibernate.Session;
import model.entityDB.DipendentiEntity;
import java.util.List;

public class DipendentiDaoHibernate implements DAO {

    public List<DipendentiEntity> getAll(){
        Session session = DBManager.getSession();

        List<DipendentiEntity> dipendentiEntities = session.createQuery("from DipendentiEntity").list();
        session.close();
        return dipendentiEntities;
    }

    public void store(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void delete(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }

    public void update(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }
}
