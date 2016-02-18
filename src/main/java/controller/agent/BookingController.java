package controller.agent;

import javafx.scene.Node;
import model.DBManager;
import model.dao.*;
import model.entityDB.*;
import view.agent.GroupTripBookingView;

import java.util.List;

/**
 * Controller class for group trip booking use case.
 **/

public class BookingController {

    public static Node getView() { return new GroupTripBookingView(); }

    /** @param entity; ViaggioGruppoEntity to be booked
     *  @param name; String, part of client's credential
     *  @param surname; String, part of client's credential
     *  @param phone: integer, part of client's credential
     *  @param qu: integer, how many group trip instances the client books
     *  @return boolean: whether or not the operation was successful
     * @throws Exception: incomplete/invalid submitted input is handled via Exception
     **/
    public static boolean handle(ViaggioGruppoEntity entity, String name, String surname, int phone, int qu) throws Exception{

        //checks
        if (!checkStrings(name, surname)) throw new Exception("Dati del cliente non pervenuti");
        if (phone == 0) throw new Exception("Numero del cliente non pervenuto");

        try {
            DBManager.initHibernate();

            //Booking entity
            PrenotazioneEntity booking = new PrenotazioneEntity();
            booking.setViaggioId(entity.getId());
            booking.setNome(name);
            booking.setCognome(surname);
            booking.setTelefono(phone);
            booking.setQuantità(qu);
            PrenotazioneDaoHibernate.instance().store(booking);

            //bookings update for group trip entity
            entity.addPrenotazione(qu);
            ViaggioGruppoDaoHibernate.instance().update(entity);

            //bookings update for each offer assembled by the group trip
            List<PacchettoOffertaEntity> links = (List<PacchettoOffertaEntity>) PacchettoOffertaDaoHibernate.instance().
                    getByCriteria("where id_pacchetto = " + entity.getId());
            for (PacchettoOffertaEntity link : links) {
                int id = link.getIdOfferta();
                System.out.println("ID " + id);
                OffertaEntity offer = (OffertaEntity) OffertaDaoHibernate.instance().getById(id);
                offer.addPrenotazioni(qu);
                OffertaDaoHibernate.instance().update(offer);
            }
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
     * Utility method for input validation.
     * @param strings: Strings to check.
     * @return boolean
     */
    private static boolean checkStrings(String... strings) {
        for (String string : strings) if (!checkString(string)) return false;
        return true;
    }

    /**
     * Utility method for input validation.
     * @param string: String to check.
     * @return boolean
     */
    private static boolean checkString(String string) { return string != null && !"".equals(string); }
}
