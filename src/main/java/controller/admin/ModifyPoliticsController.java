package controller.admin;

import controller.Constants;
import javafx.scene.Node;
import model.DBManager;
import model.dao.DAO;
import model.dao.PoliticaDaoHibernate;
import model.entityDB.PoliticaEntity;
import view.admin.ModifyPoliticsView;

/**
 * Controller class for politics management use case.
 */
public class ModifyPoliticsController {

    public static Node getView() { return new ModifyPoliticsView(); }

    /**
     * @param entity: PoliticaEntity to update
     * @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     */
    public static boolean handle(PoliticaEntity entity) throws Exception {

        entity.setValore(polish(entity.getId(), entity.getValore())); //polishing and setting new value
        String msg = evaluate(entity.getId(), entity.getValore()); //checking for input coherence
        if (msg != null) throw new Exception(msg);

        try {
            DBManager.initHibernate();
            PoliticaDaoHibernate.instance().update(entity);
        }
        catch (Exception e) {
            //failure
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); } //always shut the DB down

        return true;
    }

    /**
     * Utility method for politic value evaluation.
     * @param id: integer representing the pending politic, needed in order to perform the right check
     * @param newValue: double value proposed as a replacement
     * @return boolean: whether or not the entity passed this check
     */
    private static String evaluate(int id, double newValue) {

        DAO dao = PoliticaDaoHibernate.instance();

        switch (id) {
            /**
             * If admin is updating the minimum overprice politic, it has to be taken in account
             * both maximum overprice value (because it can't be less than the minimum) AND the
             * agency discount value (which could take products prices below their factory price)
             */
            case Constants.minOverprice:
                double maxValue = ((PoliticaEntity) dao.getById(Constants.maxOverprice)).getValore();
                double discount = ((PoliticaEntity) dao.getById(Constants.discount)).getValore();
                if (newValue >= maxValue) return "Il nuovo valore eccede quello del sovrapprezzo massimo";
                else if (newValue * discount < 1) return "Lo sconto corrente è superiore al sovrapprezzo minimo";
                break;

            /**
             * If admin is updating the maximum overprice politic, it has to be compared with
             * minimum overprice.
             */
            case Constants.maxOverprice:
                double minValue = ((PoliticaEntity) dao.getById(Constants.minOverprice)).getValore();
                if (newValue <= minValue) return "Il sovrapprezzo massimo è inferiore a quello minimo";
                break;

            /**
             * If admin is updating the agency discount politic, it has to be compared with
             * minimum overprice, in order to avoid it taking prices below factory ones
             */
            case Constants.discount:
                minValue = ((PoliticaEntity) dao.getById(Constants.minOverprice)).getValore();
                if (minValue * newValue < 1) return "Lo sconto corrente è superiore al sovrapprezzo minimo";
                break;

            /**
             * Minimum group trip partecipants number only has to be > 1, duh
             */
            case Constants.minGroup: default:
                if (newValue < 2) return "Un viaggio di gruppo dev'essere composto da almeno 2 persone";
        }

        return null; //check passed
    }

    /**
     * Utility method, used for value adjustment (different representation of this value: from
     * percentage to double)
     * @param id: Politic id for proper polishing
     * @param value: percentual representation of the value
     * @return double: proper value
     */
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
