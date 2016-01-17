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

    @Override
    public List<Station> search(Arrival[] factor) {

        if (factor.length != 2) return null;

        Arrival from = factor[0];
        Arrival to = factor[1];

        DBManager.initHibernate();

        List<Station> aggregate = bfsearch(0, makeNode(from), to.city);

        DBManager.shutdown();

        if (aggregate == null) {
            System.out.println("NO RESULTS");
        }

        return aggregate;
    }

    private List<Station> bfsearch(int i, Station from, String to) {

        List<Station> results = new ArrayList<>();
        List<ViaggioEntity> result = new ArrayList<>(), partial = new ArrayList<>();
        List<Station> stations = new ArrayList<>();
        stations.add(from);

        Number found = -1;

        while (stations.size() > 0) {

            result.clear();

            Station current = stations.remove(0);
            System.out.println("CURRENTLY IN " + current.acorn.city);

            if (found.doubleValue() != -1 && found.doubleValue() <= current.getWeight().doubleValue()) continue;

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

                if (e.getDestinazione().equals(to)) {
                    found = newStation.getWeight();
                    System.out.println("FOUND YA!!! VALUE: " + newStation.getWeight());
                    addToResults(results, newStation);
                }
                else {
                    stations.add(newStation);
                    System.out.println(e.getDestinazione() + " WAS NOT " + to);
                }
            }
        }

        return results.size() > 0 ? results : null;
    }

    protected void addToResults(List<Station> list, Station station) {

        int i;
        for (i = 0; i < list.size(); ++i)
            if (list.get(i).getWeight().doubleValue() > station.getWeight().doubleValue()) break;

        list.add(i, station);
    }

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
                    format(new Timestamp(arrival.arrival.getTime() + 12 * 3600000));
            query += "', '%Y-%m-%d %k:%i')";
        }

        return query;
    }

    protected abstract class Station extends Node<Arrival, Number> {

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

        private String recreate() {

            String query = "where stazionePartenza like '";
            query += ((Arrival) getParent().acorn).city;
            query += "' and stazioneArrivo like '";
            query += acorn.city;
            query += "' and dataArrivo = ";
            query += "str_to_date('";
            query += new SimpleDateFormat("yyyy-MM-dd HH:mm").format(acorn.arrival);
            query += "', '%Y-%m-%d %k:%i')";

            return query;
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
