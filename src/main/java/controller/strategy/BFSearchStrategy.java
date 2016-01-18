package controller.strategy;

import model.DBManager;
import model.dao.ViaggioDaoHibernate;
import model.entityDB.ViaggioEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This ConcreteStrategy class implements the BFS search algorithm via Template Method pattern.
 * Template method search specifies the algorithm main steps, but leaves to subclasses the implementation
 * of steps concerning the final result creation.
 * T type is defined by the Arrival inner class; a Node subclass is also specified to responde to the
 * algorithm needs.
 */

public abstract class BFSearchStrategy extends SearchStrategy<BFSearchStrategy.Arrival[]> {

    int limit = Integer.MAX_VALUE; //default results number limit

    /**
     * search method implementation. It checks arguments before actually doing research.
     * @param factor T: a couple of Arrival objects. The algorithm only proceeds if is an actual couple
     *               of Arrival objects.
     * @return List<Station>: a List of Station objects. It is possible to retrieve a list of ViaggioEntities
     *                        from them via climbUp() method.
     */
    @Override
    public List<Station> search(Arrival[] factor) {

        if (factor.length != 2) return null;

        Arrival from = factor[0];
        Arrival to = factor[1];

        DBManager.initHibernate(); //DB initialization and lock

        List<Station> aggregate = bfsearch(makeNode(from), to.city); //Actual search

        DBManager.shutdown(); //DB release

        return aggregate;
    }

    /**
     * Actual BFS search algorithm. Its goal is to find routes from an A city to a B city.
     * @param from Station: the Station root node wrapping A city name.
     * @param to String: arrival city name.
     * @return List<Station>: same as search return value.
     */
    private List<Station> bfsearch(Station from, String to) {

        List<Station> results = new ArrayList<>(); //@return
        List<ViaggioEntity> partial = new ArrayList<>(), buffer; //ViaggioEntity buffer
        List<Station> stations = new ArrayList<>(); //Station buffer
        stations.add(from);

        int found = 0; //results counter

        //while all meaningful tree nodes have been explored
        while (stations.size() > 0) {

            partial.clear();

            Station current = stations.remove(0); //Pop top Station

            if (found >= limit || evaluate(current, results)) continue;
            //If the number of requested results has been reached or research is no longer meaningful
            //just skip exploration

            buffer = (List<ViaggioEntity>) ViaggioDaoHibernate.instance().
                    getByCriteria(query(current.getAcorn()));
            if (buffer != null) partial.addAll(buffer);

            if (partial.isEmpty()) continue; //Leaf

            for (ViaggioEntity e : partial) {

                Station newStation =
                        makeNode((
                                new Arrival(
                                        e.getDestinazione(),
                                        e.getDataArrivo())));

                current.attach(newStation, e);
                //Attach a new Station child to its parent

                if (e.getDestinazione().equals(to)) {
                    //Destination has been found and (hopefully) added into results
                    if (addToResults(results, newStation)) ++found; //counter updated
                }

                else stations.add(newStation); //continue exploring
            }
        }

        return results.size() > 0 ? results : null;
    }

    protected abstract boolean evaluate(Station station, List<Station> station1) ;

    protected abstract boolean addToResults(List<Station> list, Station station);

    protected abstract Station makeNode(Arrival arrival) ;

    private String query(Arrival arrival) {

        String query = "where stazionePartenza like '";
        query += arrival.city;
        query += "'";

        if (arrival.arrival != null) {
            query += " and dataInizio >= ";
            query += "str_to_date('";
            query += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(arrival.arrival);
            query += "', '%Y-%m-%d %k:%i') and dataInizio < ";
            query += "str_to_date('";
            query += new SimpleDateFormat("yyyy-MM-dd HH:mm").
                    format(new Timestamp(arrival.arrival.getTime() + 24 * 3600000));
            query += "', '%Y-%m-%d %k:%i')";
        }

        System.out.println(query);

        return query;
    }

    public abstract class Station extends Node<Arrival, Number> {

        protected Map<Arrival, ViaggioEntity> cache = new HashMap<>();

        protected Station(Arrival city) { super(city); }

        protected void attach(Node son, ViaggioEntity entity) {
            ((Station) son).cache.put(acorn, entity);
            attach(son);
        }

        @Override
        protected void attach(Node son) {
            son.setParent(this);
            subTree.put(son, son.getWeight());
        }

        @Override
        public List<ViaggioEntity> climbUp() {

            List<ViaggioEntity> entities = new ArrayList<>();

            if (getParent() == null) return entities;

            entities.addAll(getParent().climbUp());
            entities.add(cache.get(parent.acorn));

            return entities;
        }

        @Override
        public String toString() { return this.acorn.toString(); }
    }

    public static class Arrival {

        private String city;
        private Timestamp arrival;

        public Arrival(String city, Timestamp arrival) {
            this.city = city;
            this.arrival = arrival;
        }

        @Override
        public String toString() {

            return arrival != null ? city + " @ " + new SimpleDateFormat("HH:mm dd/MM/yyyy").format(arrival) :
                    city;
        }
    }
}
