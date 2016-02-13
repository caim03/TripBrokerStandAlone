package model.dao;

import model.DBManager;
import model.entityDB.*;
import org.hibernate.Session;

import java.util.List;

public class PacchettoDaoHibernate extends ProdottoDaoHibernate {

    private static DAO singleton;

    protected PacchettoDaoHibernate() {}

    public static DAO instance() {
        /** @result DAO; return the DAO **/

        /* This method is used to implement the Singleton Pattern;
         * in fact the constructor is protected and the object DAO is instantiate only one time */

        if (singleton == null) singleton = new PacchettoDaoHibernate();
        return singleton;
    }

    public synchronized List<PacchettoEntity> getAll(){
        /** @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PacchettoEntity> entities = session.createQuery("from PacchettoEntity").list();
        for (PacchettoEntity entity : entities) populate(entity);

        // close connection
        session.close();
        return entities;
    }

    public synchronized List<PacchettoEntity> getByCriteria(String where) {
        /** @param String; this string contains the where clause to be used in the query
         *  @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PacchettoEntity> entities = session.createQuery("from PacchettoEntity " + where).list();
        for (PacchettoEntity entity : entities) populate(entity);

        // close connection
        session.close();
        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public List<PacchettoEntity> getByQuery(String query) {
        /** @param String; this string contains the entire query to be used to retrieving the entities
         *  @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        List<PacchettoEntity> entities = session.createQuery(query).list();
        for (PacchettoEntity entity : entities) populate(entity);

        // close connection
        session.close();

        if (entities.isEmpty()) return null;
        else return entities;
    }

    @Override
    public synchronized PacchettoEntity getById(int id) {
        /** @param int; this integer represents the identifier of ProdottoEntity to be retrieved
         *  @result List; return the list of packets, retrieved from the DataBase **/

        // get session (connection)
        Session session = DBManager.getSession();

        // performs the query
        PacchettoEntity entity = (PacchettoEntity) session.createQuery("from PacchettoEntity where id = " + id).list().get(0);

        // close connection
        session.close();

        if (entity == null) return null;

        populate(entity);
        return entity;
    }

    public synchronized int store(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be saved to the DataBase
         *  @result int; return the identifier of the saved product **/

        PacchettoEntity pacchettoEntity = (PacchettoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // save the object
        session.save(pacchettoEntity);
        // commit transaction
        session.getTransaction().commit();
        session.close(); // close connection

        return pacchettoEntity.getId();
    }

    public synchronized void update(AbstractEntity entity) {
        /** @param AbstractEntity; entity that must be updated to the DataBase **/

        PacchettoEntity pacchettoEntity = (PacchettoEntity) entity;

        // get session (connection)
        Session session = DBManager.getSession();

        session.beginTransaction();
        // update the object
        session.update(pacchettoEntity);
        // commit the transaction
        session.getTransaction().commit();
        // close connection
        session.close();

        disrupt(pacchettoEntity.getId());
    }

    public void bindAll(int id, List<OffertaEntity> offers) {

        int i = 0;
        for (OffertaEntity offer : offers) {
            bind(id, offer.getId(), i);
            ++i;
        }
    }
    private void bind(int idPacchetto, int idOfferta, int position) {

        PacchettoOffertaEntity couple = new PacchettoOffertaEntity();
        couple.setIdPacchetto(idPacchetto);
        couple.setIdOfferta(idOfferta);
        couple.setPosizione(position);
        PacchettoOffertaDaoHibernate.instance().store(couple);
    }

    protected void disrupt(int id) {
        PacchettoOffertaDaoHibernate.instance().query("DELETE FROM PacchettoOffertaEntity where idPacchetto = " + id);
    }

    public void populate(PacchettoEntity entity) {

        List<PacchettoOffertaEntity> ids = (List<PacchettoOffertaEntity>) PacchettoOffertaDaoHibernate.instance().
                getByCriteria("where idPacchetto = " + entity.getId() + " order by posizione");

        for (PacchettoOffertaEntity id : ids) {
            OffertaEntity offer = (OffertaEntity) OffertaDaoHibernate.instance().getById(id.getIdOfferta());
            entity.addOffer(id.getPosizione(), offer);
        }
    }
}
