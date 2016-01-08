package controller;

import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.OffertaEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ViaggioGruppoEntity;

import java.util.List;

public class ConfirmBookingController {

    public static void handle(PrenotazioneEntity entity) {

        DBManager.initHibernate();
        List<OffertaEntity> entities = (List<OffertaEntity>) OffertaDaoHibernate.instance()
                .getByCriteria("WHERE id IN " +
                        "(SELECT idOfferta FROM GruppoOffertaEntity WHERE idGruppo = " + entity.getViaggioId() + ")");

        for (OffertaEntity offer : entities) {
            offer.setQuantità(offer.getQuantità() - entity.getQuantità());
            OffertaDaoHibernate.instance().update(offer);
        }

        PrenotazioneDaoHibernate.instance().delete(entity);
        DBManager.shutdown();
    }
}
