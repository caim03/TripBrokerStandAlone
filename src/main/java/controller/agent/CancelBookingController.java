package controller.agent;

import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.OffertaEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ViaggioGruppoEntity;

import java.util.List;

/*** This controller is used to delete a booking from a group trip;
 *   it refreshes the number of the bookings for the group trip selected,
 *   and it cancels the booking associated into DataBase ***/

public class CancelBookingController {

    /**
     * @param entity ; the booking that must be deleted  **/
    public static boolean handle(PrenotazioneEntity entity) {
        try {
            DBManager.initHibernate();

            // retrieve the list of offers contained into the group trip
            List<OffertaEntity> entities = (List<OffertaEntity>) OffertaDaoHibernate.instance()
                    .getByCriteria("WHERE id IN " +
                            "(SELECT idOfferta FROM GruppoOffertaEntity WHERE idGruppo = " + entity.getViaggioId() + ")");

            // for all offer in group trip refresh the quantity
            for (OffertaEntity offer : entities) {
                offer.addPrenotazioni(-entity.getQuantità());
                OffertaDaoHibernate.instance().update(offer);
            }

            // retrieve the trip book associated to the booking
            ViaggioGruppoEntity trip = (ViaggioGruppoEntity) ViaggioGruppoDaoHibernate.instance()
                    .getById(entity.getViaggioId());
            // refresh the number of the bookings
            trip.addPrenotazione(-entity.getQuantità());
            // update the group trip
            ViaggioGruppoDaoHibernate.instance().update(trip);

            // delete the booking
            PrenotazioneDaoHibernate.instance().delete(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }
}
