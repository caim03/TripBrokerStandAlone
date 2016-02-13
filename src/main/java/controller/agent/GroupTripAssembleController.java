package controller.agent;

import controller.Constants;
import model.DBManager;
import model.dao.PacchettoOffertaDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.*;
import view.TripBrokerConsole;

import java.util.List;

/**
 * Controller class for group trips creation use case.
 */
public class GroupTripAssembleController {

    /**
     * @param entity; ViaggioGruppoEntity to create
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    public static boolean create(ViaggioGruppoEntity entity, double bound0, double bound1, List<OffertaEntity> entities) throws Exception {
        //checks
        if (entity.getNome() == null || "".equals(entity.getNome())) throw new Exception("Riempire tutti i campi obbligatori");
        if (entities.size() == 0) throw new Exception("Pacchetto vuoto");
        if (!(entity.getPrezzo() >= bound0 && entity.getPrezzo() <= bound1)) throw new Exception("Il prezzo deve essere compreso tra i suoi limiti");
        if (entity.getMin() > entity.getMax()) throw new Exception("Prenotazioni minime e massime incoerenti");

        checkLocations(entities.get(0), entities.get(entities.size() - 1));

        //Preparations
        //retrieving offers IDs
        int ids[] = new int[entities.size()], i = 0;
        for (OffertaEntity offer : entities) {
            ids[i] = offer.getId();
            ++i;
        }

        //ViaggioGruppoEntity creation
        entity.setCreatore(TripBrokerConsole.getGuestID());
        entity.setTipo(Constants.group);
        entity.setStato(1);

        try {
            DBManager.initHibernate();

            int gtid = ViaggioGruppoDaoHibernate.instance().store(entity); //group trip id

            //binding offers to group trip
            int pos = 0;
            for (int id : ids) {
                bind(gtid, id, pos);
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
     * Utility method for ViaggioGruppo/Offerta binding.
     * @param gtid: int; ViaggioGruppoEntity id
     * @param id: int; OffertaEntity id
     * @param pos: int; OffertaEntity position into ViaggioGruppoEntity
     */
    private static void bind(int gtid, int id, int pos) {
        PacchettoOffertaEntity link = new PacchettoOffertaEntity();
        link.setIdOfferta(id);
        link.setIdPacchetto(gtid);
        link.setPosizione(pos);
        PacchettoOffertaDaoHibernate.instance().store(link);
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
