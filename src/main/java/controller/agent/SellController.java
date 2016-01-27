package controller.agent;


import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.DAO;
import model.entityDB.OffertaEntity;

public class SellController {

    /** @param entity; the entity that must be updated
     *  @param quantityStr; this string represents the quantity selected in spinner that must be converted to int
     *  @return boolean; return false if arguments are not legal, else return true **/
    public static boolean handle(OffertaEntity entity, String quantityStr) {

        if (entity == null || quantityStr == null || "".equals(quantityStr)) {
            return false;
        }

        int currQuantity;
        currQuantity = entity.getQuantità();
        entity.setQuantità(currQuantity - Integer.parseInt(quantityStr)); // set new quantity to offer

        DBManager.initHibernate();
        DAO dao = OffertaDaoHibernate.instance();
        dao.update(entity);
        DBManager.shutdown();

        return true;
    }
}
