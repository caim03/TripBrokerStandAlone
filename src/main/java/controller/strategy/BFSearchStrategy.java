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

public abstract class BFSearchStrategy extends SearchStrategy<BFSearchStrategy.Arrival[]> {

    int limit = Integer.MAX_VALUE;

    @Override
    public List<Station> search(Arrival[] factor) {

        if (factor.length != 2) return null;

        Arrival from = factor[0];
        Arrival to = factor[1];

        DBManager.initHibernate();

        List<Station> aggregate = bfsearch(makeNode(from), to.city);

        DBManager.shutdown();

        return aggregate;
    }

    private List<Station> bfsearch(Station from, String to) {

        List<Station> results = new ArrayList<>();
        List<ViaggioEntity> result = new ArrayList<>(), partial;
        List<Station> stations = new ArrayList<>();
        stations.add(from);

        int found = 0;

        while (stations.size() > 0) {

            result.clear();

            Station current = stations.remove(0);
            System.out.println("CURRENTLY IN " + current.acorn.city);

            if (found >= limit || evaluate(current, results)) continue;

            partial = (List<ViaggioEntity>) ViaggioDaoHibernate.instance().
                    getByCriteria(query(current.getAcorn()));

            if (partial == null) continue;

            result.addAll(partial);

            for (ViaggioEntity e : result) {

                Station newStation =
                        makeNode((
                                new Arrival(
                                        e.getDestinazione(),
                                        e.getDataArrivo())));

                current.attach(newStation, e);

                System.out.println("NEW STATION " + newStation.acorn.city);

                if (e.getDestinazione().equals(to)) {
                    if (addToResults(results, newStation)) ++found;
                    System.out.println("FOUND ONE");
                }

                else {
                    System.out.println("NO LUCK");
                    stations.add(newStation);
                }
            }
        }

        return results.size() > 0 ? results : null;
    }

    protected boolean evaluate(Station station, List<Station> station1) { return false; }

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
