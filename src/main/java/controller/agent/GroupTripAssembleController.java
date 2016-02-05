package controller.agent;

import controller.Constants;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.dao.GruppoOffertaDaoHibernate;
import model.dao.ViaggioGruppoDaoHibernate;
import model.entityDB.*;
import view.TripBrokerConsole;

import java.util.List;

public class GroupTripAssembleController {

    /** @param name; the name of the new group trip
     *  @param price; the price of the new group trip
     *  @param min; the minimum number of bookings for this group trip
     *  @param max; the maximum number of bookings for this group trip
     */
    public static boolean create(String name, double price, double bound0, double bound1, int min, int max, List<OffertaEntity> entities) throws Exception {

        if (name == null || "".equals(name)) throw new Exception("Riempire tutti i campi obbligatori");
        if (entities.size() == 0) throw new Exception("Pacchetto vuoto");
        if (!(price >= bound0 && price <= bound1)) throw new Exception("Il prezzo deve essere compreso tra i suoi limiti");
        if (min > max) throw new Exception("Prenotazioni minime e massime incoerenti");

        checkLocations(entities.get(0), entities.get(entities.size() - 1));

        int ids[] = new int[entities.size()], i = 0;
        for (OffertaEntity entity : entities) {
            ids[i] = entity.getId();
            ++i;
        }

        try {
            ViaggioGruppoEntity entity = new ViaggioGruppoEntity();
            entity.setNome(name);
            entity.setPrezzo(price);
            entity.setCreatore(TripBrokerConsole.getGuestID());
            entity.setTipo(Constants.group);
            entity.setMin(min);
            entity.setMax(max);

            DBManager.initHibernate();
            int gtid = ViaggioGruppoDaoHibernate.instance().store(entity);

            int pos = 0;
            for (int id : ids) {

                GruppoOffertaEntity link = new GruppoOffertaEntity();
                link.setIdOfferta(id);
                link.setIdGruppo(gtid);
                link.setPosizione(pos);
                GruppoOffertaDaoHibernate.instance().store(link);

                ++pos;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        finally { DBManager.shutdown(); }

        return true;
    }

    private static void checkLocations(OffertaEntity from, OffertaEntity to) throws Exception {
        String message = "I pacchetti dovrebbero iniziare e terminare con un viaggio, " +
                "check-in e check-out nella stessa location";
        if (!(from instanceof ViaggioEntity && to instanceof ViaggioEntity))
            throw new Exception(message);
        else if (!from.getCittà().equals(((ViaggioEntity) to).getDestinazione()))
            throw new Exception(message);
    }
}
