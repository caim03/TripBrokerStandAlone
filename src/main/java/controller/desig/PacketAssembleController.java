package controller.desig;

import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.ViaggioEntity;

import java.util.List;

/**
 * Controller class for both packet prototype creation and editing use cases.
 * If the CreaPacchettoEntity instance passed to this controller already has an ID set,
 * it means the user is requiring an entity update; otherwise, this is the first time
 * the user creates the instance and has to be stored in catalog.
 */
public class PacketAssembleController {

    /**
     * Main controller interface. It considers whether performing entity storing or update.
     * @param entity: CreaPacchetto entity to store/update
     * @param bound0: double; price lower bound
     * @param bound1: double; price upper bound
     * @param offers: List<OffertaEntity>; offers assembled by entity
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    public static boolean handle(CreaPacchettoEntity entity, double bound0, double bound1, List<OffertaEntity> offers) throws Exception {
        //regardless the operation, coherence checks are made
        performChecks(entity, bound0, bound1, offers);
        try {
            /**
             * if entity ID is already set, it requires update;
             * otherwise, it is a first-time store
             */
            if (entity.getId() == 0)
                return create(entity, offers); //store
            else return update(entity, offers); //update
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Store method.
     * @param entity: CreaPacchettoEntity to be stored
     * @param offers: List<OffertaEntity> to bond to entity
     * @return boolean: whether or not the operation was successful
     */
    private static boolean create(CreaPacchettoEntity entity, List<OffertaEntity> offers) {
        try {
            DBManager.initHibernate();
            CreaPacchettoDaoHibernate dao = (CreaPacchettoDaoHibernate) CreaPacchettoDaoHibernate.instance();
            dao.bindAll(dao.store(entity), offers); //storing & binding
        }
        catch (Exception e) {
            //failure
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut the DB down

        return true;
    }

    /**
     * Update method.
     * @param entity: CreaPacchettoEntity to update
     * @param offers: List<OffertaEntity> to bond to entity
     * @return boolean: whether or not the operation was successful
     */
    private static boolean update(CreaPacchettoEntity entity, List<OffertaEntity> offers) {
        try {
            DBManager.initHibernate();
            CreaPacchettoDaoHibernate dao = (CreaPacchettoDaoHibernate) CreaPacchettoDaoHibernate.instance();
            dao.update(entity); //updating & disrupting bonds
            dao.bindAll(entity.getId(), offers); //bonding
        }
        catch (Exception e) {
            //failure
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut the DB down

        return true;
    }

    /**
     * Utility method for coherence checking.
     * @param entity: CreaPacchettoEntity to check.
     * @param bound0: double; price lower bound
     * @param bound1: double; price upper bound
     * @param offers: List<OffertaEntity>; offers assembled by entity
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    private static void performChecks(CreaPacchettoEntity entity, double bound0, double bound1, List<OffertaEntity> offers)
            throws Exception {

        if (!checkString(entity.getNome())) throw new Exception("Riempire tutti i campi obbligatori");
        if (offers.size() == 0) throw new Exception("Pacchetto vuoto");
        if (!(entity.getPrezzo() >= bound0 && entity.getPrezzo() <= bound1))
            throw new Exception("Il prezzo deve essere compreso tra i suoi limiti");

        checkLocations(offers.get(0), offers.get(offers.size() - 1));
    }

    /**
     * Utility method.
     * @param string: String to check
     * @return boolean.
     */
    private static boolean checkString(String string) { return string != null && !"".equals(string); }

    /**
     * Utility method for coherence checking; it evaluates if ordered offers are rightfully placed,
     * ie they begin and end at the same location.
     * @param from: first OffertaEntity in the packet; ya better be ViaggioEntity!
     * @param to: last OffertaEntity in the packet; ya better be ViaggioEntity!
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    private static void checkLocations(OffertaEntity from, OffertaEntity to) throws Exception {
        String message = "I pacchetti dovrebbero iniziare e terminare con un viaggio, " +
                "check-in e check-out nella stessa location";
        if (!(from instanceof ViaggioEntity && to instanceof ViaggioEntity))
            throw new Exception(message);
        else if (!from.getCittà().equals(((ViaggioEntity) to).getDestinazione()))
            throw new Exception(message);
    }
}
