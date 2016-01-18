package controller;

import model.DBManager;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ViaggioGruppoEntity;

/*** This controller is used to delete a booking from a group trip;
 *   it refreshes the number of the bookings for the group trip selected,
 *   and it cancels the booking associated into DataBase ***/

public class CancelBookingController {

    public static void handle(PrenotazioneEntity entity) {
        /** @param PrenotazioneEntity; the booking that must be deleted **/

        new Thread(() -> {
            DBManager.initHibernate();

            // retrieve the trip book associated to the booking
            ViaggioGruppoEntity trip = (ViaggioGruppoEntity) ViaggioGruppoDaoHibernate.instance()
                    .getByCriteria("where id = " + entity.getViaggioId()).get(0);

            // refresh the number of the bookings
            trip.setPrenotazioni(trip.getPrenotazioni() - entity.getQuantità());
            // update the group trip
            ViaggioGruppoDaoHibernate.instance().update(trip);

            // delete the booking
            PrenotazioneDaoHibernate.instance().delete(entity);
            DBManager.shutdown();
        }).start();
    }
}
