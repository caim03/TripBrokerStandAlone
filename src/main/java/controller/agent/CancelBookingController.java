package controller.agent;

import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.OffertaEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ViaggioGruppoEntity;

import java.util.List;

/***
 * Controller class for booking management use case.
 ***/

public class CancelBookingController {

    /**
     * @param entity; PrenotazioneEntity to cancel
     * @return boolean: whether or not the operation was successful
     * **/
    public static boolean handle(PrenotazioneEntity entity) {
        try {
            DBManager.initHibernate();

            int id, qu;
            handleOffers(id = entity.getViaggioId(), qu = entity.getQuantità());
            handleGroupTrip(id, qu);

            PrenotazioneDaoHibernate.instance().delete(entity); //cancelling booking
        }
        catch (Exception e) {
            //failure
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut down DB

        return true;
    }

    /**
     * Bookings has to be released from OffertaEntities assembled by the group trip.
     * @param id: integer; the group trip id
     * @param qu: bookings quantity
     */
    private static void handleOffers(int id, int qu) {
        //retrieving offers
        List<OffertaEntity> entities = (List<OffertaEntity>) OffertaDaoHibernate.instance()
                .getByCriteria("WHERE id IN " +
                        "(SELECT idOfferta FROM PacchettoOffertaEntity WHERE idPacchetto = " + id + ")");

        //For each OffertaEntity release the bookings
        for (OffertaEntity offer : entities) {
            offer.addPrenotazioni(-qu);
            OffertaDaoHibernate.instance().update(offer);
        }
    }

    /**
     * Group trip entities also store bookings, which have to be released.
     * @param id: integer; the group trip id
     * @param qu: bookings quantity
     */
    private static void handleGroupTrip(int id, int qu) {
        ViaggioGruppoEntity trip = (ViaggioGruppoEntity) ViaggioGruppoDaoHibernate.instance()
                .getById(id);
        trip.addPrenotazione(-qu);
        ViaggioGruppoDaoHibernate.instance().update(trip);
    }
}
