package controller;

import model.DBManager;
import model.dao.GruppoOffertaDaoHibernate;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.*;

/** This controller is called when someone (an agent) books a group trip;
 *  it refresh the number of bookings in the group trip in DataBase and create a new booking **/

public class BookingController {

    public static void handle(ViaggioGruppoEntity entity, String name, String surname, int bookings) {
        /** @param ViaggioGruppoEntity; this entity represents the group trip booked
         *  @param String; this string represents the buyer name
         *  @param String; this string represents the buyer surname
         *  @param int; this integer represents the number of bookings for the group trip **/

        new Thread(() -> {

            DBManager.initHibernate();

            // new booking
            PrenotazioneEntity booking = new PrenotazioneEntity();

            // set parameters
            booking.setViaggioId(entity.getId());
            booking.setNome(name);
            booking.setCognome(surname);
            booking.setQuantità(bookings);

            // store the booking in DataBase
            PrenotazioneDaoHibernate.instance().store(booking);

            // refresh the number of the bookings
            entity.addPrenotazione(bookings);

            // update the group trip entity
            ViaggioGruppoDaoHibernate.instance().update(entity);

            // for all offer in group trip refresh the number of bookings
            for (AbstractEntity gruppoOfferta : GruppoOffertaDaoHibernate.instance().getByCriteria("where id_gruppo = " + entity.getId())) {

                OffertaEntity offer = (OffertaEntity) OffertaDaoHibernate.instance().getByCriteria("where id = " + ((GruppoOffertaEntity) gruppoOfferta).getIdOfferta()).get(0);
                offer.addPrenotazioni(bookings);
                OffertaDaoHibernate.instance().update(offer);
            }

            DBManager.shutdown();
        }).start();
    }
}
