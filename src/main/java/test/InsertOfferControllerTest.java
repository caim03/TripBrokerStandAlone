package test;

import controller.scout.InsertOfferController;
import controller.builder.EntityBuilder;
import model.entityDB.OffertaEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class InsertOfferControllerTest {

    private OffertaEntity entity;
    private String list[] = new String[]{"Viaggio", "Evento", "Pernottamento"};
    private Random random = new Random(Date.from(Instant.now()).getTime());
    private EntityBuilder.Arguments arg;

    @Before
    public void setUp() throws Exception {
        String type = list[random.nextInt(3)];
        Instant instant = Instant.now();

        EntityBuilder builder = EntityBuilder.getBuilder(type);
        builder.buildProduct("Test", 100.0, type);
        builder.buildOffer("Roma", 1, 0, Timestamp.from(instant));

        if (type.equals("Viaggio")) {
            arg = (EntityBuilder.Arguments.from("Pisa",
                    Timestamp.from(instant.plus(Duration.ofHours(random.nextInt(24) + 1))),
                    "Treno", "Prima"));
        } else if (type.equals("Evento")) {
            arg = (EntityBuilder.Arguments.from("Hotel Grippo",
                    Timestamp.from(instant.plus(Duration.ofHours(random.nextInt(24) + 1)))));
        } else {
            arg = (EntityBuilder.Arguments.from("Hotel Palace", "Pensione completa", "5",
                    Timestamp.from(instant.plus(Duration.ofHours(random.nextInt(24) + 1)))));
        }

        entity = builder.getEntity();

    }

    @Test
    public void testHandle() throws Exception {
        assertTrue(InsertOfferController.handle(entity.getNome(), entity.getPrezzo(),
                entity.getQuantità(), entity.getTipo(), entity.getCittà(), entity.getDataInizio(), arg));
    }

    @Test
    public void testHandle2() throws Exception {
        entity.setNome(null);
        assertFalse(InsertOfferController.handle(entity.getNome(), entity.getPrezzo(),
                entity.getQuantità(), entity.getTipo(), entity.getCittà(), entity.getDataInizio(), arg));
    }

    @Test
    public void testHandle3() throws Exception {
        entity.setNome("Test2");
        entity.setQuantità(0);
        assertFalse(InsertOfferController.handle(entity.getNome(), entity.getPrezzo(),
                entity.getQuantità(), entity.getTipo(), entity.getCittà(), entity.getDataInizio(), arg));
    }

    @Test
    public void testHandle4() throws Exception {
        entity.setQuantità(1);
        entity.setPrezzo(0);
        assertFalse(InsertOfferController.handle(entity.getNome(), entity.getPrezzo(),
                entity.getQuantità(), entity.getTipo(), entity.getCittà(), entity.getDataInizio(), arg));
    }

    @Test
    public void testHandle5() throws Exception {
        entity.setPrezzo(100.0);
        arg.setDate(Timestamp.from(entity.getDataInizio().toInstant().minus(Duration.ofMillis(100000L))));
        assertFalse(InsertOfferController.handle(entity.getNome(), entity.getPrezzo(),
                entity.getQuantità(), entity.getTipo(), entity.getCittà(), entity.getDataInizio(), arg));
    }
}