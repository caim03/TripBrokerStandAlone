package controller;


import model.DBManager;
import model.dao.OffertaDaoHibernate;
import model.daoInterface.DAO;
import model.entityDB.OffertaEntity;

public class SellController {

    public static boolean handle(OffertaEntity entity, String quantityStr) {

        if (entity == null || quantityStr == null || "".equals(quantityStr)) {
            return false;
        }

        int currQuantity;
        currQuantity = entity.getQuantità();
        entity.setQuantità(currQuantity - Integer.parseInt(quantityStr));

        DBManager.initHibernate();
        DAO dao = OffertaDaoHibernate.instance();
        dao.update(entity);
        DBManager.shutdown();

        return true;
    }
}
