package controller.agent;

import controller.Constants;
import model.DBManager;
import model.dao.GruppoOffertaDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.GruppoOffertaEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.ViaggioEntity;
import model.entityDB.ViaggioGruppoEntity;
import view.TripBrokerConsole;

import java.util.List;

/**
 * Controller class for group trips creation use case.
 */
public class GroupTripAssembleController {

    /**
     * @param name; group trip name
     * @param price; group trip price
     * @param min; minimum number of bookings required for selling the group trip
     * @param max; maximum number of bookings allowed
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    public static boolean create(String name, double price, double bound0, double bound1, int min, int max, List<OffertaEntity> entities) throws Exception {

        //checks
        if (name == null || "".equals(name)) throw new Exception("Riempire tutti i campi obbligatori");
        if (entities.size() == 0) throw new Exception("Pacchetto vuoto");
        if (!(price >= bound0 && price <= bound1)) throw new Exception("Il prezzo deve essere compreso tra i suoi limiti");
        if (min > max) throw new Exception("Prenotazioni minime e massime incoerenti");

        checkLocations(entities.get(0), entities.get(entities.size() - 1));

        //Preparations
        //retrieving offers IDs
        int ids[] = new int[entities.size()], i = 0;
        for (OffertaEntity entity : entities) {
            ids[i] = entity.getId();
            ++i;
        }

        //ViaggioGruppoEntity creation
        ViaggioGruppoEntity entity = new ViaggioGruppoEntity();
        entity.setNome(name);
        entity.setPrezzo(price);
        entity.setCreatore(TripBrokerConsole.getGuestID());
        entity.setTipo(Constants.group);
        entity.setMin(min);
        entity.setMax(max);

        try {
            DBManager.initHibernate();

            int gtid = ViaggioGruppoDaoHibernate.instance().store(entity); //group trip id

            //binding offers to group trip
            int pos = 0;
            for (int id : ids) {
                GruppoOffertaEntity link = new GruppoOffertaEntity();
                link.setIdOfferta(id);
                link.setIdGruppo(gtid);
                link.setPosizione(pos);
                GruppoOffertaDaoHibernate.instance().store(link);

                ++pos;
            }
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
     * Utility method used to check if group trip actually returns to departure place
     * @param from: first OffertaEntity in the group trip
     * @param to: last OffertaEntity in the group trip
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
