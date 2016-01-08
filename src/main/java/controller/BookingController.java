package controller;

import model.DBManager;
import model.dao.GruppoOffertaDaoHibernate;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.*;

public class BookingController {

    public static void handle(ViaggioGruppoEntity entity, String name, String surname, int bookings) {

        new Thread(() -> {

            DBManager.initHibernate();
            PrenotazioneEntity booking = new PrenotazioneEntity();

            booking.setViaggioId(entity.getId());
            booking.setNome(name);
            booking.setCognome(surname);
            booking.setQuantità(bookings);
            PrenotazioneDaoHibernate.instance().store(booking);

            entity.addPrenotazione(bookings);
            ViaggioGruppoDaoHibernate.instance().update(entity);

            for (AbstractEntity gruppoOfferta : GruppoOffertaDaoHibernate.instance().getByCriteria("where id_gruppo = " + entity.getId())) {

                OffertaEntity offer = (OffertaEntity) OffertaDaoHibernate.instance().getByCriteria("where id = " + ((GruppoOffertaEntity) gruppoOfferta).getIdOfferta()).get(0);
                offer.addPrenotazioni(bookings);
                OffertaDaoHibernate.instance().update(offer);
            }

            DBManager.shutdown();
        }).start();
    }
}
