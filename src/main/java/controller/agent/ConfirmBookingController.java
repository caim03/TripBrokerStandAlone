package controller.agent;

import controller.Constants;
import model.DBManager;
import model.dao.*;
import model.entityDB.OffertaEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.StatusEntity;
import model.entityDB.ViaggioGruppoEntity;

import java.util.List;

/***
 * Controller class for booking management use case.
 ***/
public class ConfirmBookingController {

    /**
     * Failure is handled by View class, apparently
     * @param entity; the booking that must be confirmed
     **/
    public static void handle(PrenotazioneEntity entity) {

        try {
            DBManager.initHibernate();

            int id, qu;
            handleOffers(id = entity.getViaggioId(), qu = entity.getQuantità());
            handleTrip(id, qu);

            PrenotazioneDaoHibernate.instance().delete(entity); //Booking instance can be released from DB
        }
        finally { DBManager.shutdown(); }
    }

    /**
     * Update the booking and confirmation attributes values into group trip entity.
     * @param id: ViaggioGruppoEntity id.
     * @param qu: confirmed participation
     */
    private static void handleTrip(int id, int qu) {
        ViaggioGruppoEntity trip = (ViaggioGruppoEntity) ViaggioGruppoDaoHibernate.instance()
                .getById(id);

        //update bookings and confirmed values
        trip.addPrenotazione(-qu);
        trip.addAcquisti(qu);

        ViaggioGruppoDaoHibernate.instance().update(trip); //DB update

        //also register entries
        handleEconomics(Math.round(trip.getPrezzo() * qu * 100) / 100.0);
    }

    /**
     * Utility method for entries register update
     * @param entries: double; how much the company earned with this confirmation
     */
    private static void handleEconomics(double entries) {
        DAO dao = StatusDaoHibenate.getInstance();
        StatusEntity status = (StatusEntity) dao.getById(Constants.entries);
        status.update(entries);
        dao.update(status);
    }

    /**
     * OffertaEntities store a bookings attribute that has to be updated.
     * @param id: group trip id.
     * @param qu: quantity sold
     */
    private static void handleOffers(int id, int qu) {
        //retrieving offers
        List<OffertaEntity> entities = (List<OffertaEntity>) OffertaDaoHibernate.instance()
                .getByCriteria("WHERE id IN " +
                        "(SELECT idOfferta FROM GruppoOffertaEntity WHERE idGruppo = " + id + ")");

        //for each offer, both bookings and quantity attributes have to be updated
        for (OffertaEntity offer : entities) {
            offer.setQuantità(offer.getQuantità() - qu); //qu sold
            offer.addPrenotazioni(-qu);
            OffertaDaoHibernate.instance().update(offer);
        }
    }
}
