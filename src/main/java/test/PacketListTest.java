package test;

import model.entityDB.EventoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.desig.PacketList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

public class PacketListTest {
    private static long acceptableDelay = 12 * 3600000;
    private String cities[] = new String[] { "Roma", "Milano", "Torino", "Brescia", "Genova", "Napoli", "Bergamo",
            "Pescara", "L'Aquila", "Chieti", "Isernia", "Urbino", "Macerata", "Pesaro", "Firenze", "Siena",
            "Agrigento", "Palermo", "Aosta", "Venezia", "Trento" };
    private Random random;
    private OffertaEntity entity;
    private OffertaEntity prev;

    private OffertaEntity generate() {

        random = new Random(Date.from(Instant.now()).getTime());

        int type = random.nextInt(3);

        OffertaEntity entity;

        switch (type) {
            case 0: {
                entity = new ViaggioEntity();
                entity = genOffer(entity);
                genTravel((ViaggioEntity) entity);
                break;
            }
            case 1: {
                entity = new EventoEntity();
                entity = genOffer(entity);
                genEvent((EventoEntity) entity);
                break;
            }
            case 2: default: {
                entity = new PernottamentoEntity();
                entity = genOffer(entity);
                entity = genOvernight((PernottamentoEntity) entity);
            }
        }

        return entity;
    }

    private OffertaEntity genOffer(OffertaEntity entity) {
        entity.setCittà(randomCity());
        entity.setDataInizio(new Timestamp(116, randomMonth(), randomDay(), randomHour(), randomMinute(), 0, 0));
        return entity;
    }

    private ViaggioEntity genTravel(ViaggioEntity entity) {
        String destination;
        do {
            destination = randomCity();
        } while (destination.equals(entity.getCittà()));
        entity.setDestinazione(destination);
        entity.setDataArrivo(Timestamp.from(entity.getDataInizio().toInstant().plus(Duration.ofHours(4))));
        return entity;
    }
    private EventoEntity genEvent(EventoEntity entity) {
        entity.setDataFine(Timestamp.from(entity.getDataInizio().toInstant().plus(Duration.ofHours(2))));
        return entity;
    }

    private PernottamentoEntity genOvernight(PernottamentoEntity entity) {
        entity.setDataFinale(Timestamp.from(entity.getDataInizio().toInstant().plus(Duration.ofDays(6))));
        return entity;
    }


    private String randomCity() {
        return cities[random.nextInt(21)];
    }

    private int randomMonth() {
        return random.nextInt(12) + 1;
    }

    private int randomMinute() {
        return random.nextInt(60);
    }

    private int randomHour() {
        return random.nextInt(24);
    }

    private int randomDay() {
        return random.nextInt(30) + 1;
    }

    @Test
    public void testAdd() throws Exception {

        PacketList list = new PacketList(false);
        int expected = 0;
        for (long i = 0; i < 10000000; ++i) {
            entity = generate();
            if (i > 0) {
                prev = list.getPrevious(list.size());
                if (checkDate(prev, entity) && checkLocation(prev, entity)) {
                    ++expected;
                }
                list.add(entity);
            }
            else {
                list.add(entity);
                ++expected;
            }
        }

        assertEquals(expected, list.size());
    }

    private String getInfo(OffertaEntity entity) {

        String msg = entity.getCittà() + " " + entity.getDataInizio() + " ";
        if (entity instanceof ViaggioEntity) {
            msg += ((ViaggioEntity) entity).getDestinazione() + " " + ((ViaggioEntity) entity).getDataArrivo();
            msg = "Viaggio " + msg;
        }
        else if (entity instanceof EventoEntity) {
            msg += ((EventoEntity) entity).getDataFine();
            msg = "Evento " + msg;
        }
        else if (entity instanceof PernottamentoEntity) {
            msg += ((PernottamentoEntity) entity).getDataFinale();
            msg = "Pernotto " + msg;
        }

        return msg;
    }

    private boolean checkDate(OffertaEntity previous, OffertaEntity next) {

        Date firstDate,
                secondDate = next.getDataInizio(); //Scrutiny refers to the beginning of the added offer
        boolean result;

        if (previous instanceof ViaggioEntity) {
            /**
             * PernottamentoEntities behave differently when next to ViaggioEntity instances.
             * Given that PernottamentoEntities do not specify any schedule restriction,
             * one can place them after a travel offer as long as day of arrival and reception matches.
             * For all the other Offers, common sense suggests that arrival should take place earlier than
             * the next offer start and that delays between the two should not be longer than 12 hours.
             **/

            firstDate = ((ViaggioEntity) previous).getDataArrivo();

            if (next instanceof PernottamentoEntity) {

                long travel = firstDate.getTime() - firstDate.getHours() * 3600000
                        - firstDate.getMinutes() * 60000 - firstDate.getSeconds() * 1000,
                        stay = secondDate.getTime() - secondDate.getHours() * 3600000
                                - secondDate.getMinutes() * 60000 - secondDate.getSeconds() * 1000;
                result = travel == stay;
            }
            else
                result = firstDate.before(secondDate) && new Date(firstDate.getTime() + acceptableDelay).after(secondDate);
        }

        else if (previous instanceof PernottamentoEntity) {
            /**
             * PernottamentoEntities put specific restriction for every type of OfferEntity.
             * An event could take place anytime between reception and overnight stay end.
             * A trip departure date should take place on the last day of overnight, as should a change
             * of overnight stays.
             */

            firstDate = ((PernottamentoEntity)previous).getDataFinale();
            firstDate = new Timestamp(firstDate.getTime() - firstDate.getHours() * 3600000
                    - firstDate.getMinutes() * 60000 - firstDate.getSeconds() * 1000);
            Date firstDateEnd = new Date(firstDate.getTime() + 3600000 * 23 + 60000 * 59);

            if (next instanceof EventoEntity) {
                Date zeroDate = previous.getDataInizio();
                zeroDate = new Timestamp(zeroDate.getTime() - zeroDate.getHours() * 3600000
                        - zeroDate.getMinutes() * 60000 - zeroDate.getSeconds() * 1000);
                Date thirdDate = ((EventoEntity) next).getDataFine();
                result = secondDate.after(zeroDate) && thirdDate.before(firstDateEnd);
            }
            else if (next instanceof ViaggioEntity)
                result = secondDate.after(firstDate) && firstDateEnd.after(secondDate);
            else
                result = firstDate.equals(secondDate);
        }

        else {
            /**
             * EventoEntities put no specific restriction on offers;
             * common sense suggests that any new offer should take place after the event ends,
             * but not later than 12 hours.
             * Tipically, EventoEntities are not scrutinized, due to the fact that they are often
             * included into overnight stays time spans.
             */
            firstDate = previous.getDataInizio();
            result = firstDate.before(secondDate) && new Date(firstDate.getTime() + acceptableDelay).after(secondDate);
        }

        return result;
    }

    /**
     * Utility method for entities spatial comparison
     * @param previous OffertaEntity: last meaningful entity in the observed List
     * @param next OffertaEntity: scrutinized entity
     * @return boolean: whether or not the entities can be placed one after another
     */
    boolean checkLocation(OffertaEntity previous, OffertaEntity next) {

        String city;
        if (previous instanceof ViaggioEntity) city = ((ViaggioEntity) previous).getDestinazione();
        else city = previous.getCittà();

        return city.equals(next.getCittà());
    }
}