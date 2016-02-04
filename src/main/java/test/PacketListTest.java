package test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.entityDB.EventoEntity;
import model.entityDB.OffertaEntity;
import model.entityDB.PernottamentoEntity;
import model.entityDB.ViaggioEntity;
import org.junit.Before;
import org.junit.Test;
import view.desig.PacketList;
import view.material.DBListView;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PacketListTest {
    private static long acceptableDelay = 12 * 3600000;
    private String cities[] = new String[] { "Roma", "Milano", "Torino", "Brescia", "Genova", "Napoli", "Bergamo",
            "Pescara", "L'Aquila", "Chieti", "Isernia", "Urbino", "Macerata", "Pesaro", "Firenze", "Siena",
            "Agrigento", "Palermo", "Aosta", "Venezia", "Trento" };
    private Random random;

    @Before public void seed() { random = new Random(Date.from(Instant.now()).getTime()); }

    @Test
    public void testAdd() throws Exception {

        PacketList list = new PacketList(false);
        int expected = 0;
        OffertaEntity entity, prev;
        for (long i = 0; i < 1000000; ++i) {
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

    private OffertaEntity generate() {

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

    private boolean checkDate(OffertaEntity previous, OffertaEntity next) {

        Date firstDate,
                secondDate = next.getDataInizio();
        boolean result;

        if (previous instanceof ViaggioEntity) {

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
            firstDate = previous.getDataInizio();
            result = firstDate.before(secondDate) && new Date(firstDate.getTime() + acceptableDelay).after(secondDate);
        }

        return result;
    }

    boolean checkLocation(OffertaEntity previous, OffertaEntity next) {

        String city;
        if (previous instanceof ViaggioEntity) city = ((ViaggioEntity) previous).getDestinazione();
        else city = previous.getCittà();

        return city.equals(next.getCittà());
    }
}