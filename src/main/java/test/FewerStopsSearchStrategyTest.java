package test;

import controller.strategy.FewerStopsSearchStrategy;
import model.Route;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class FewerStopsSearchStrategyTest {

    @Test
    public void testSearch() throws Exception {
        FewerStopsSearchStrategy strategy = new FewerStopsSearchStrategy();
        List<Route> list = strategy.search("Roma", "Torino", null);
        assertEquals(3, list.get(0).size());
    }

    @Test
    public void testSearch2() throws Exception {
        FewerStopsSearchStrategy strategy = new FewerStopsSearchStrategy();
        List<Route> list = strategy.search("Roma", "Torino", null);

        if (list.size() < 2) assertTrue(true);

        for(int i=1; i<list.size(); ++i) {
            assertEquals(list.get(i).size(), list.get(i-1).size());
        }
    }
}