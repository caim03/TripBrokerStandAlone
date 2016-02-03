package controller.desig;

import controller.Constants;
import controller.exception.BoundsException;
import controller.exception.EmptyFormException;
import controller.exception.EmptyPacketException;
import controller.exception.LocationsException;
import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.dao.PacchettoOffertaDaoHibernate;
import model.entityDB.*;
import view.TripBrokerConsole;

import java.util.List;

public class PacketAssembleController {


    /** @param name; represents the name of the new packet
     *  @param price; represents the name of the new packet
     *
     *
     *
     *  @return boolean; return a boolean value that represents the result of operation **/
    public static boolean create(String name, double price, double bound0, double bound1, List<OffertaEntity> entities) throws Exception {

        if (name == null || "".equals(name)) throw new EmptyFormException();
        if (entities.size() == 0) throw new EmptyPacketException();
        if (price < bound0 || price > bound1) throw new BoundsException();

        checkLocations(entities.get(0), entities.get(entities.size() - 1));

        int ids[] = new int[entities.size()], i = 0;
        for (OffertaEntity entity : entities) {
            ids[i] = entity.getId();
            ++i;
        }

        try {

            CreaPacchettoEntity entity = new CreaPacchettoEntity();
            entity.setNome(name);
            entity.setPrezzo(price);
            entity.setCreatore(TripBrokerConsole.getGuestID());
            entity.setTipo(Constants.packet);
            DBManager.initHibernate();
            CreaPacchettoDaoHibernate.instance().store(entity);

            int pos = 0;
            for (int id : ids) {

                PacchettoOffertaEntity link = new PacchettoOffertaEntity();
                link.setIdOfferta(id);
                link.setIdPacchetto(entity.getId());
                link.setPosizione(pos);
                PacchettoOffertaDaoHibernate.instance().store(link);

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

    private static void checkLocations(OffertaEntity from, OffertaEntity to) throws LocationsException {
        if (!(from instanceof ViaggioEntity && to instanceof ViaggioEntity)) throw new LocationsException();
        else if (!from.getCittà().equals(((ViaggioEntity) to).getDestinazione())) throw new LocationsException();
    }
}
