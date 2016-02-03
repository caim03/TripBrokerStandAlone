package model.dao;

import model.DBManager;
import model.entityDB.AbstractEntity;
import model.entityDB.StatusEntity;
import org.hibernate.Session;

import java.util.List;

public class StatusDaoHibenate implements DAO {

    private static DAO me;
    private StatusDaoHibenate() {}
    public static DAO getInstance() {
        if (me == null) me = new StatusDaoHibenate();
        return me;
    }

    @Override public List<? extends AbstractEntity> getAll() { return null; }
    @Override public List<? extends AbstractEntity> getByCriteria(String where) { return null; }
    @Override public List<? extends AbstractEntity> getByQuery(String query) { return null; }

    @Override public AbstractEntity getById(int id) {

        Session session = DBManager.getSession();

        // performs the query
        StatusEntity entity = (StatusEntity) session.createQuery("from StatusEntity where id = " + id).list().get(0);

        // close connection
        session.close();
        if (entity == null) return null;
        else return entity;
    }

    @Override public int store(AbstractEntity entity) throws ClassCastException { return 0; }
    @Override public void delete(AbstractEntity entity) throws ClassCastException { }

    @Override
    public void update(AbstractEntity entity) throws ClassCastException {
        StatusEntity statusEntity = (StatusEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(statusEntity);
        // commit the transaction
        session.getTransaction().commit();

        // close connection
        session.close();
    }
}
