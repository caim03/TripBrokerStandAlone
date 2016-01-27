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

    private static boolean dateCheck(Timestamp start, Timestamp end) { return start.before(end); }

    private static void insert(OffertaEntity entity) throws HibernateException {

        DAO dao;
        if (entity instanceof ViaggioEntity) dao = ViaggioDaoHibernate.instance();
        else if (entity instanceof EventoEntity) dao = EventoDaoHibernate.instance();
        else dao = PernottamentoDaoHibernate.instance();

        DBManager.initHibernate();
        dao.store(entity);
        DBManager.shutdown();
    }

    private static boolean checkStrings(String string) { return string != null && !"".equals(string); }

    private static boolean checkStrings(String... strings) {
        for (String s : strings) if (!checkStrings(s)) return false;
        return true;
    }
}