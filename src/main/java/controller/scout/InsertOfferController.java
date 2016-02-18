package controller.scout;

import controller.Constants;
import controller.builder.EntityBuilder;
import javafx.scene.Node;
import model.DBManager;
import model.dao.*;
import model.entityDB.EventoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.StatusEntity;
import model.entityDB.ViaggioEntity;
import org.hibernate.HibernateException;
import view.scout.OfferInsertionView;

import java.sql.Timestamp;

/**
 * Controller class for offer insertion use case.
 */
public class InsertOfferController {

    public static Node getView() { return new OfferInsertionView(); }

    /**
     * @param name; String; offer name
     * @param price; double; offer price
     * @param qu; integer; offer availability
     * @param type; String; offer type (among Travel, Overnight and Event)
     * @param city; String; city from which the offer takes place (its meaning can vary)
     * @param date; Timestamp; when the offer takes place (its meaning can vary)
     * @param arguments; Arguments; utility class wrapping up all remaining attributes
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     **/
    public static boolean handle(String name, double price, int qu, String type,
                                 String city, Timestamp date, EntityBuilder.Arguments arguments) throws Exception {
        //checks
        if (!checkStrings(name, type, city) || arguments == null)
            throw new Exception("Form incompleto; si prega di ricontrollare");
        else if (qu < 1 || price <= 0 || !dateCheck(date, arguments.getDate()))
            throw new Exception("Informazioni non coerenti; si prega di ricontrollare");

        //Offer instantiation is performed via Builder class due to its complexity
        EntityBuilder builder = EntityBuilder.getBuilder(type); //getting Builder by type

        builder.buildProduct(name, price,type);
        builder.buildOffer(city, qu, 0, date);
        builder.buildEntity(arguments); //subclass

        //DB interaction
        try {
            DBManager.initHibernate();
            insert(builder.getEntity()); //Offer storing
            update(Math.round(price * qu * 100) / 100.0); //Costs register update
        }
        catch (HibernateException e) {
            //failure
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut the DB down

        return true;
    }

    /**
     * Utility method for Timestamp instances comparison.
     * @param start; the timestamp of the start date
     * @param end; the timestamp of the end date
     * @return boolean
     **/
    private static boolean dateCheck(Timestamp start, Timestamp end) { return start.before(end); }

    /**
     * Store method; it selects DAO from entity subclass
     * @param entity; OffertaEntity to be stored
     * **/
    private static void insert(OffertaEntity entity) throws HibernateException {

        DAO dao;
        if (entity instanceof ViaggioEntity) dao = ViaggioDaoHibernate.instance();
        else if (entity instanceof EventoEntity) dao = EventoDaoHibernate.instance();
        else dao = PernottamentoDaoHibernate.instance();

        dao.store(entity);
    }

    /**
     * Costs register update method. Everytime a new offer is inserted into catalog,
     * its TOTAL cost (price * quantity) is accounted.
     * @param totalPrice: double; price * quantity
     */
    private static void update(double totalPrice) {
        DAO dao = StatusDaoHibenate.getInstance();
        StatusEntity entity = (StatusEntity) dao.getById(Constants.costs);
        entity.update(totalPrice);
        dao.update(entity);
    }

    /**
     * Utility method for String coherence checking.
     * @param string; String to check
     * @return boolean
     **/
    private static boolean checkStrings(String string) { return string != null && !"".equals(string); }

    /**
     * Utility method for Strings coherence checking
     * @param strings; String[]; strings to check
     * @return boolean
     **/
    private static boolean checkStrings(String... strings) {
        for (String s : strings) if (!checkStrings(s)) return false;
        return true;
    }
}
