package controller;

import model.DBManager;
import model.dao.GruppoOffertaDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.GruppoOffertaEntity;
import model.entityDB.ViaggioGruppoEntity;
import view.TripBrokerConsole;

public class GroupTripAssembleController {

    public static boolean create(String name, double price, int min, int max, int... ids) {

        try {
            ViaggioGruppoEntity entity = new ViaggioGruppoEntity();
            entity.setNome(name);
            entity.setPrezzo(price);
            entity.setCreatore(TripBrokerConsole.getGuest());
            entity.setTipo(Constants.group);
            entity.setMin(min);
            entity.setMax(max);

            DBManager.initHibernate();
            ViaggioGruppoDaoHibernate.instance().store(entity);

            for (int id : ids) {

                GruppoOffertaEntity link = new GruppoOffertaEntity();
                link.setIdOfferta(id);
                link.setIdGruppo(entity.getId());
                GruppoOffertaDaoHibernate.instance().store(link);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }
}
