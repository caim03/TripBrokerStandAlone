package model.dao;

import model.DBManager;
import model.daoInterface.DAO;
import model.entityDB.AbstractEntity;
import model.entityDB.ProdottoEntity;
import org.hibernate.Session;
import model.entityDB.DipendentiEntity;
import java.util.List;

public class DipendentiDaoHibernate implements DAO {

    private static DAO singleton;

    protected DipendentiDaoHibernate() {}

    public static DAO instance() {

        if (singleton == null) singleton = new DipendentiDaoHibernate();
        return singleton;
    }

    public synchronized List<DipendentiEntity> getAll(){
        Session session = DBManager.getSession();

        List<DipendentiEntity> dipendentiEntities = session.createQuery("from DipendentiEntity").list();
        session.close();
        return dipendentiEntities;
    }

    public synchronized List<DipendentiEntity> getByCriteria(String where) {
        Session session = DBManager.getSession();

        List<DipendentiEntity> dipendentiEntities = session.createQuery("from DipendentiEntity " + where).list();
        session.close();
        if (dipendentiEntities.isEmpty()) {
            return null;
        } else {
            return dipendentiEntities;
        }
    }

    public synchronized void store(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void delete(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.delete(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }

    public synchronized void update(AbstractEntity entity) {

        DipendentiEntity dipendentiEntity = (DipendentiEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(dipendentiEntity);
        session.getTransaction().commit();
        session.close();
    }
}
