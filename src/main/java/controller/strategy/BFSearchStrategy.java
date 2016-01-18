package controller.strategy;

import model.DBManager;
import model.dao.ViaggioDaoHibernate;
import model.entityDB.ViaggioEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    /***
     * Utility method; it generates an SQL query matching the next exploration needs.
     * @param arrival Arrival: an Arrival object, wrapping up a city and a timestamp
     * @return String: the appropriate SQL query needed to continue branch exploration
     */
    private String query(Arrival arrival) {

        String query = "where stazionePartenza like '";
        query += arrival.city;
        query += "'";
        //City constraint

        if (arrival.arrival != null) {
            query += " and dataInizio between ";
            query += "str_to_date('";
            query += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(arrival.arrival);
            query += "', '%Y-%m-%d %k:%i') and ";
            query += "str_to_date('";
            query += new SimpleDateFormat("yyyy-MM-dd HH:mm").
                    format(new Timestamp(arrival.arrival.getTime() + 24 * 3600000));
            query += "', '%Y-%m-%d %k:%i')";
        }
        //Timestamp constraint; initial steps may omit a starting time

        return query;
    }

    /**
     * Primitive operation (from Template Method) used as discriminator when
     * search algorithm contemplate stopping.
     * @param current Station: the currently examined Station instance.
     * @param stations List<Station>: set of already found solutions, used to determine
     *                 an eventual stop.
     * @return boolean: whether or not the algorithm should stop.
     */
    protected abstract boolean evaluate(Station current, List<Station> stations) ;

    /**
     * This method provides a custom way of inserting newly found solutions to the others.
     * @param list List<Station>: set of already-found solution.
     * @param station Station: newly found solution.
     * @return boolean: whether or not the Station instance was added to solutions.
     */
    protected abstract boolean addToResults(List<Station> list, Station station);

    /**
     * Subclasses specify concrete Station implementations; this method allows Template Method
     * to avoid direct instantiation of an abstract class.
     * @param arrival Arrival: Node acorn.
     * @return Station: a Station object, wrapping up its acorn.
     */
    protected abstract Station makeNode(Arrival arrival) ;

    /**
     * Node abstract implementation; subclasses implement methods according to the search
     * purpose. It uses Arrival objects as acorns, wrapping up a destination with a
     * timestamp.
     * Strategies from the outer class are only used for ViaggioEntities research;
     * a Station object caches ViaggioEntity instances for later ascending.
     */
    public abstract class Station extends Node<Arrival, Number> {

        protected ViaggioEntity cache;
        //Cached ViaggioEntity; it refers to the parent-child bond, there for there
        //can be only one item cached at a time.

        /**
         * Main constructor.
         * @param city Arrival: acorn.
         */
        protected Station(Arrival city) { super(city); }

        /**
         * This method provides the child attaching routine to the parent subtree.
         * @param child Station: the child to be attached.
         * @param entity ViaggioEntity: the route this bond represents.
         */
        protected void attach(Node child, ViaggioEntity entity) {
            ((Station) child).cache =  entity;
            attach(child);
        }

        /**
         * Actual attaching routine.
         * @param child Node: the child to be attached.
         */
        @Override
        protected void attach(Node child) {
            child.setParent(this);
            subTree.put(child, child.getWeight());
        }

        /**
         * Climbing up the route to the route from the calling Station instance.
         * Cached items are recollected on the way and ultimately returned to the caller.
         * @return List<ViaggioEntity>: List of cached ViaggioEntities, from root to leaf.
         */
        @Override
        public List<ViaggioEntity> climbUp() {

            List<ViaggioEntity> entities = new ArrayList<>();

            if (getParent() == null) return entities; //recursion base

            entities.addAll(getParent().climbUp()); //recursive call
            entities.add(cache); //adding this Station

            return entities;
        }

        /**
         * @return String: a message correlated to this Node.
         */
        @Override
        public String toString() { return this.acorn.toString(); }
    }

    /**
     * Acorn class; Station instances wraps up this value for search management.
     * It contains a city name and (usually) a timestamp indicating time of arrival.
     * Timestamp is needed to select other relatively forthcoming travels; however,
     * it can be omitted, with no strong repercussions on the search algorithm.
     */
    public static class Arrival {

        private String city; //Arriving city name
        private Timestamp arrival; //Time of arrival

        /**
         * Main constructor.
         * @param city String: the city name.
         * @param arrival Timestamp: time of arrival.
         */
        public Arrival(String city, Timestamp arrival) {
            this.city = city;
            this.arrival = arrival;
        }
    }
}
