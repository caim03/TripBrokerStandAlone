package controller.agent;

import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.OffertaEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ViaggioGruppoEntity;

import java.util.List;

/*** This controller is used to confirm a booking from a group trip;
 *   it refreshes the quantity of the offers contained into the group trip selected,
 *   and it cancels the booking associated into DataBase ***/

public class ConfirmBookingController {

    /** @param entity; the booking that must be confirmed **/
    public static void handle(PrenotazioneEntity entity) {

        DBManager.initHibernate();

        // retrieve the list of offers contained into the group trip
        List<OffertaEntity> entities = (List<OffertaEntity>) OffertaDaoHibernate.instance()
                .getByCriteria("WHERE id IN " +
                        "(SELECT idOfferta FROM GruppoOffertaEntity WHERE idGruppo = " + entity.getViaggioId() + ")");

        // for all offer in group trip refresh the quantity
        for (OffertaEntity offer : entities) {
            offer.setQuantità(offer.getQuantità() - entity.getQuantità());
            OffertaDaoHibernate.instance().update(offer);
        }

        // delete the booking
        PrenotazioneDaoHibernate.instance().delete(entity);
        DBManager.shutdown();
    }
}
