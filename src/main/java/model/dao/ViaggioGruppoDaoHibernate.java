package model.dao;

import model.DBManager;
import model.entityDB.*;
import org.hibernate.Session;

import java.util.List;

public class ViaggioGruppoDaoHibernate extends ProdottoDaoHibernate {

    private static DAO singleton;

    protected ViaggioGruppoDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new ViaggioGruppoDaoHibernate();
        return singleton;
    }

    @Override
    public synchronized List<ViaggioGruppoEntity> getAll() {
        /** @result List; return a list of ViaggioGruppoEntity, retrieved from the DataBase **/

        Session session = DBManager.getSession();
        List<ViaggioGruppoEntity> entities = session.createQuery("from ViaggioGruppoEntity").list();
        session.close();

        if (entities != null) {
            for (ViaggioGruppoEntity entity : entities)
                ((PacchettoDaoHibernate) PacchettoDaoHibernate.instance()).populate(entity);
        }
        return entities;
    }

    @Override
    public synchronized List<ViaggioGruppoEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of ViaggioGruppoEntity, retrieved from the DataBase **/

        Session session = DBManager.getSession();
        List<ViaggioGruppoEntity> entities = session.createQuery("from ViaggioGruppoEntity " + where).list();
        session.close();

        if (entities == null || entities.isEmpty()) return null;
        else if (entities != null) {
            for (ViaggioGruppoEntity entity : entities)
                ((PacchettoDaoHibernate) PacchettoDaoHibernate.instance()).populate(entity);
        }
        return entities;  // return first
    }

    @Override
    public synchronized ViaggioGruppoEntity getById(int id){
        /** @param int; this integer represents the identifier of ViaggioGruppoEntity to be retrieved
         *  @result ViaggioGruppoEntity; return a ViaggioGruppoEntity with the id used in the query **/

        Session session = DBManager.getSession();

        ViaggioGruppoEntity viaggioGruppoEntity = (ViaggioGruppoEntity) session.createQuery("from ViaggioGruppoEntity where id = " + id).list().get(0);
        session.close();
        if (viaggioGruppoEntity == null) return null;
        else {
            ((PacchettoDaoHibernate) PacchettoDaoHibernate.instance()).populate(viaggioGruppoEntity);
            return viaggioGruppoEntity;
        }
    }

    @Override
    public int store(AbstractEntity entity) throws ClassCastException {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();

        return ((ProdottoEntity) entity).getId();
    }

    @Override
    public void update(AbstractEntity entity) throws ClassCastException {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        ViaggioGruppoEntity viaggioGruppoEntity = (ViaggioGruppoEntity) entity;

        Session session = DBManager.getSession();

        session.beginTransaction();
        session.update(viaggioGruppoEntity);
        session.getTransaction().commit();

        session.close();
    }
}
