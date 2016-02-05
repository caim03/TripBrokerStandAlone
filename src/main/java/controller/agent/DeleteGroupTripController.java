package controller.agent;

import model.DBManager;
import model.dao.DAO;
import model.dao.OffertaDaoHibernate;
import model.dao.PrenotazioneDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.OffertaEntity;
import model.entityDB.PrenotazioneEntity;
import model.entityDB.ViaggioGruppoEntity;

import java.util.List;

public class DeleteGroupTripController {

    public static boolean handle(ViaggioGruppoEntity entity) {

        try {
            int id = entity.getId();
            DBManager.initHibernate();

            int qu = entity.getPrenotazioni();
            List<OffertaEntity> offers = (List<OffertaEntity>) OffertaDaoHibernate.instance().
                    getByCriteria("where id in (select idOfferta from GruppoOffertaEntity where idGruppo = " + id + ")");

            DAO offerDao = OffertaDaoHibernate.instance();
            for (OffertaEntity offer : offers) {
                offer.addPrenotazioni(-qu);
                offerDao.update(offer);
            }

            ViaggioGruppoDaoHibernate.instance().delete(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }
}
