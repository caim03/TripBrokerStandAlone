package controller.agent;


import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.dao.DAO;
import model.entityDB.OffertaEntity;

public class SellController {

    public static boolean handle(OffertaEntity entity, String quantityStr) {
        /** @param: OffertaEntity; the entity that must be updated
         *  @param: String; this string represents the quantity selected in spinner that must be converted to int
         *  @result: boolean; return false if arguments are not legal, else return true **/

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
