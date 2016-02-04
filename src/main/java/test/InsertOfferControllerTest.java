package test;

import controller.Constants;
import controller.builder.EntityBuilder;
import controller.scout.InsertOfferController;
import model.DBManager;
import model.dao.StatusDaoHibenate;
import model.entityDB.OffertaEntity;
import model.entityDB.StatusEntity;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class InsertOfferControllerTest {

    private OffertaEntity entity;
    private String list[] = new String[]{"Viaggio", "Evento", "Pernottamento"};
    private Random random = new Random(Date.from(Instant.now()).getTime());
    private EntityBuilder.Arguments arg;
    private int qu;
    private double price, initialCost;

    @Before
    public void setUp() throws Exception {
        String type = list[random.nextInt(3)];
        Instant instant = Instant.now();
        qu = random.nextInt(100);
        price = random.nextInt(300) + (Math.floor(random.nextDouble() * 100)) / 100.0;

        EntityBuilder builder = EntityBuilder.getBuilder(type);
        builder.buildProduct("Test", price, type);
        builder.buildOffer("Roma", qu, 0, Timestamp.from(instant));

        switch (type) {
            case "Viaggio":
                arg = (EntityBuilder.Arguments.from("Pisa",
                        Timestamp.from(instant.plus(Duration.ofHours(random.nextInt(24) + 1))),
                        "Treno", "Prima"));
                break;
            case "Evento":
                arg = (EntityBuilder.Arguments.from("Hotel Grippo",
                        Timestamp.from(instant.plus(Duration.ofHours(random.nextInt(24) + 1)))));
                break;
            default:
                arg = (EntityBuilder.Arguments.from("Hotel Palace", "Pensione completa", "5",
                        Timestamp.from(instant.plus(Duration.ofHours(random.nextInt(24) + 1)))));
                break;
        }

        entity = builder.getEntity();

        DBManager.initHibernate();
        initialCost = ((StatusEntity) StatusDaoHibenate.getInstance().getById(Constants.costs)).getValore();
        System.out.println(initialCost);
        DBManager.shutdown();
    }

    @Test
    public void testHandle() throws Exception {

        assertTrue(InsertOfferController.handle(entity.getNome(), entity.getPrezzo(),
                entity.getQuantità(), entity.getTipo(), entity.getCittà(), entity.getDataInizio(), arg));

        DBManager.initHibernate();
        StatusDaoHibenate.getInstance().getById(Constants.entries);
        double total = ((StatusEntity) StatusDaoHibenate.getInstance().getById(Constants.costs)).getValore() - initialCost;
        DBManager.shutdown();

        assertEquals(qu * price, total, 0.01);
    }
}