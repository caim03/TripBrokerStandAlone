package controller.agent;

import controller.Constants;
import model.DBManager;
import model.dao.*;
import model.entityDB.OffertaEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.StatusEntity;
import model.entityDB.ViaggioGruppoEntity;

import java.util.List;

/*** This controller is used to confirm a booking from a group trip;
 *   it refreshes the quantity of the offers contained into the group trip selected,
 *   and it cancels the booking associated into DataBase ***/

public class ConfirmBookingController {

    /** @param entity; the booking that must be confirmed **/
    public static void handle(PrenotazioneEntity entity) {

        try {
            DBManager.initHibernate();

            int id = entity.getViaggioId(), qu = entity.getQuantità();
            // retrieve the list of offers contained into the group trip
            handleOffers(id, qu);
            handleTrip(id, qu);

            // delete the booking
            PrenotazioneDaoHibernate.instance().delete(entity);
        }
        finally { DBManager.shutdown(); }
    }

    private static void handleTrip(int id, int qu) {
        ViaggioGruppoEntity trip = (ViaggioGruppoEntity) ViaggioGruppoDaoHibernate.instance()
                .getById(id);

        // refresh the number of the bookings
        trip.addPrenotazione(-qu);
        trip.addAcquisti(qu);
        // update the group trip
        ViaggioGruppoDaoHibernate.instance().update(trip);

        handleEconomics(Math.round(trip.getPrezzo() * qu * 100) / 100.0);
    }

    private static void handleEconomics(double entries) {
        DAO dao = StatusDaoHibenate.getInstance();
        StatusEntity status = (StatusEntity) dao.getById(Constants.entries);
        status.update(entries);
        dao.update(status);
    }

    private static void handleOffers(int id, int qu) {

        List<OffertaEntity> entities = (List<OffertaEntity>) OffertaDaoHibernate.instance()
                .getByCriteria("WHERE id IN " +
                        "(SELECT idOfferta FROM GruppoOffertaEntity WHERE idGruppo = " + id + ")");

        // for all offer in group trip refresh the quantity
        for (OffertaEntity offer : entities) {
            offer.setQuantità(offer.getQuantità() - qu);
            offer.addPrenotazioni(-qu);
            OffertaDaoHibernate.instance().update(offer);
        }
    }
}
