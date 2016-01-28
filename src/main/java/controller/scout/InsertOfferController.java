package controller.scout;

import controller.builder.EntityBuilder;
import model.DBManager;
import model.dao.DAO;
import model.dao.EventoDaoHibernate;
import model.dao.PernottamentoDaoHibernate;
import model.dao.ViaggioDaoHibernate;
import model.entityDB.EventoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.ViaggioEntity;
import org.hibernate.HibernateException;

import java.sql.Timestamp;

public class InsertOfferController {

    /** @param name; represents the name of the new offer
     *  @param price; represents the price of the new offer
     *  @param qu; represents the quantity of the new offer
     *  @param type; represents the type of the new offer (travel, stay or event)
     *  @param city; represents the city of the new offer
     *  @param date; represents the start date of the new offer
     *  @param arguments; represent the all parameters of the specific offer
     *  @return boolean; return a boolean value that represents the result of operation **/
    public static boolean handle(String name, double price, int qu, String type,
                                 String city, Timestamp date, EntityBuilder.Arguments arguments) {
        if (!checkStrings(name, type, city) || qu < 1 || price <= 0 ||
                arguments == null || !dateCheck(date, arguments.getDate()))
            return false;

        EntityBuilder builder = EntityBuilder.getBuilder(type);

        builder.buildProduct(name, price,type);
        builder.buildOffer(city, qu, 0, date);
        builder.buildEntity(arguments);

        try { insert(builder.getEntity()); }
        catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /** @param start; the timestamp of the start date
     *  @param end; the timestamp of the end date
     *  @return boolean; return a boolean value that represents the result of operation **/
    private static boolean dateCheck(Timestamp start, Timestamp end) { return start.before(end); }

    /** @param entity; the entity that must be inserted **/
    private static void insert(OffertaEntity entity) throws HibernateException {

        DAO dao;
        if (entity instanceof ViaggioEntity) dao = ViaggioDaoHibernate.instance();
        else if (entity instanceof EventoEntity) dao = EventoDaoHibernate.instance();
        else dao = PernottamentoDaoHibernate.instance();

        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }

    /** @param string; string that must be checked
     *  @return boolean; return a boolean value that represents the result of operation **/

    private static boolean checkStrings(String string) { return string != null && !"".equals(string); }

    /** @param strings; strings that must be checked
     *  @return boolean; return a boolean value that represents the result of operation **/
    private static boolean checkStrings(String... strings) {
        for (String s : strings) if (!checkStrings(s)) return false;
        return true;
    }
}
