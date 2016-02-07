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
        assertTrue(list.size() > 0);
        assertEquals(3, list.get(0).size());
        //Expected results: at least one solution exists,with number of stops being 3

        //has every found solution got the same amount of stops?
        //if there is one or no solution, then yes
        if (list.size() < 2) assertTrue(true);
        //otherwise, a check is required
        for(int i=1; i<list.size(); ++i)
            assertEquals(list.get(i).size(), list.get(i-1).size());
    }
}