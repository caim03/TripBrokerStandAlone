package controller;

import model.DBManager;
import model.dao.CreaPacchettoDaoHibernate;
import model.dao.PacchettoOffertaDaoHibernate;
import model.entityDB.CreaPacchettoEntity;
import model.entityDB.PacchettoOffertaEntity;
import org.hibernate.Session;
import view.TripBrokerConsole;

public class PacketAssembleController {


    public static boolean create(String name, double price, int... ids) {

        try {

            CreaPacchettoEntity entity = new CreaPacchettoEntity();
            entity.setNome(name);
            entity.setPrezzo(price);
            entity.setCreatore(TripBrokerConsole.getGuest());
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
}
