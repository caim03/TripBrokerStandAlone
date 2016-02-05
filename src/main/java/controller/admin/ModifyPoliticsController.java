package controller.admin;

import controller.Constants;
import model.DBManager;
import model.dao.DAO;
import model.dao.PoliticheDaoHibernate;
import model.entityDB.PoliticheEntity;

public class ModifyPoliticsController {

    public static boolean handle(PoliticheEntity entity) throws Exception {

        entity.setValore(polish(entity.getId(), entity.getValore()));
        String msg = evaluate(entity.getId(), entity.getValore());
        if (msg != null) throw new Exception(msg);

        try {
            DBManager.initHibernate();
            PoliticheDaoHibernate.instance().update(entity);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }

    private static String evaluate(int id, double newValue) {

        DAO dao = PoliticheDaoHibernate.instance();

        switch (id) {
            case Constants.minOverprice:
                double maxValue = ((PoliticheEntity) dao.getById(Constants.maxOverprice)).getValore();
                double discount = ((PoliticheEntity) dao.getById(Constants.discount)).getValore();
                if (newValue >= maxValue) return "Il nuovo valore eccede quello del sovrapprezzo massimo";
                else if (newValue * discount < 1) return "Lo sconto corrente è superiore al sovrapprezzo minimo";
                break;
            case Constants.maxOverprice:
                double minValue = ((PoliticheEntity) dao.getById(Constants.minOverprice)).getValore();
                if (newValue <= minValue) return "Il sovrapprezzo massimo è inferiore a quello minimo";
                break;
            case Constants.discount:
                minValue = ((PoliticheEntity) dao.getById(Constants.minOverprice)).getValore();
                if (minValue * newValue < 1) return "Lo sconto corrente è superiore al sovrapprezzo minimo";
                break;
            case Constants.minGroup: default:
                if (newValue < 2) return "Un viaggio di gruppo dev'essere composto da almeno 2 persone";
        }

        return null;
    }

    private static double polish(int id, double value) {

        switch (id) {
            case Constants.discount:
                return 1 - value / 100.0;
            case Constants.minGroup:
                return value;
            default:
                return 1 + value / 100.0;
        }
    }
}
