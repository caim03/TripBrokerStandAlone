package controller.agent;


import controller.Constants;
import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.DAO;
import model.dao.PoliticheDaoHibernate;
import model.dao.StatusDaoHibenate;
import model.entityDB.OffertaEntity;
import model.entityDB.PoliticheEntity;
import model.entityDB.StatusEntity;

public class SellController {

    /** @param entity; the entity that must be updated
     *  @param qu; represents the quantity selected in spinner
     *  @return boolean; return false if arguments are not legal, else return true **/
    public static boolean handle(OffertaEntity entity, int qu) throws Exception {

        if (qu <= 0) throw new Exception("Selezionare una quantità prima dell'acquisto!");
        else if (entity == null) return false;

        int currQuantity;
        currQuantity = entity.getQuantità();
        entity.setQuantità(currQuantity - qu); // set new quantity to offer

        try {
            DBManager.initHibernate();
            DAO dao = OffertaDaoHibernate.instance();
            dao.update(entity);

            double overprice = ((PoliticheEntity) PoliticheDaoHibernate.instance().getById(Constants.minOverprice)).getValore();
            DAO statusDao = StatusDaoHibenate.getInstance();
            StatusEntity entries = (StatusEntity) statusDao.getById(Constants.entries);
            entries.update(Math.round(overprice * entity.getPrezzo() * qu * 100) / 100.0);
            statusDao.update(entries);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }
}
