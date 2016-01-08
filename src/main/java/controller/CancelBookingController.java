package controller;

import model.DBManager;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ViaggioGruppoEntity;

public class CancelBookingController {

    public static void handle(PrenotazioneEntity entity) {

        new Thread(() -> {
            DBManager.initHibernate();

            ViaggioGruppoEntity trip = (ViaggioGruppoEntity) ViaggioGruppoDaoHibernate.instance()
                    .getByCriteria("where id = " + entity.getViaggioId()).get(0);
            trip.setPrenotazioni(trip.getPrenotazioni() - entity.getQuantità());
            ViaggioGruppoDaoHibernate.instance().update(trip);

            PrenotazioneDaoHibernate.instance().delete(entity);
            DBManager.shutdown();
        }).start();
    }
}
