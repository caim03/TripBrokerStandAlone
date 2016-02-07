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

/**
 * Controller class for products selling use case.
 */
public class SellController {

    /**
     * @param entity; OffertaEntity to be purchased
     * @param qu; integer; how many instances of entity the client purchases
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     *  **/
    public static boolean handle(OffertaEntity entity, int qu) throws Exception {

        //checks
        if (qu <= 0) throw new Exception("Selezionare una quantità prima dell'acquisto!");
        else if (entity == null) return false;

        int currQuantity;
        currQuantity = entity.getQuantità();
        entity.setQuantità(currQuantity - qu); //Update OffertaEntity remaining quantity

        try {
            DBManager.initHibernate();
            DAO dao = OffertaDaoHibernate.instance();
            dao.update(entity); //OffertaEntity update

            double overprice = ((PoliticheEntity) PoliticheDaoHibernate.instance().getById(Constants.minOverprice)).getValore();
            handleEconomics(Math.round(overprice * entity.getPrezzo() * qu * 100) / 100.0);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }

    /**
     * Every purchase has to be registered into company account.
     * @param mmmmoney: double; purchase total price
     */
    private static void handleEconomics(double mmmmoney) {
        DAO statusDao = StatusDaoHibenate.getInstance();
        StatusEntity entries = (StatusEntity) statusDao.getById(Constants.entries);
        entries.update(mmmmoney);
        statusDao.update(entries);
    }
}
