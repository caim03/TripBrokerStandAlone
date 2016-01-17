package controller.strategy;

import javafx.scene.layout.StackPane;
import model.DBManager;
import model.dao.ViaggioDaoHibernate;
import model.entityDB.AbstractEntity;
import model.entityDB.ViaggioEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BFSearchStrategy extends SearchStrategy<BFSearchStrategy.Arrival[]> {

    @Override
    public List<ViaggioEntity> search(Arrival[] factor) {

        if (factor.length != 2) return null;

        Arrival from = factor[0];
        Arrival to = factor[1];

        DBManager.initHibernate();

        List<ViaggioEntity> aggregate = bfsearch(0, new Station(from), to.city);

        DBManager.shutdown();

        if (aggregate == null) {
            System.out.println("NO RESULTS");
        }

        return aggregate;
    }

    private List<ViaggioEntity> bfsearch(int i, Station from, String to) {

        List<ViaggioEntity> results = new ArrayList<>(), partial = new ArrayList<>();
        List<Station> stations = new ArrayList<>();
        stations.add(from);

        while (stations.size() > 0) {

            results.clear();

            Station current = stations.remove(0);
            System.out.println("CURRENTLY IN " + current.acorn.city);

            partial = (List<ViaggioEntity>) ViaggioDaoHibernate.instance().
                    getByCriteria(query(current.getAcorn()));

            if (partial == null) continue;

            results.addAll(partial);

            for (ViaggioEntity e : results) {

                Station newStation = new Station(new Arrival(e.getDestinazione(), e.getDataArrivo()));

                if (e.getDestinazione().equals(to)) {
                    //TODO sth
                    System.out.println("FOUND YA!!!");
                    current.attach(newStation);
                    stations.clear();
                    results = newStation.climbUp();
                    break;
                } else {
                    System.out.println(e.getDestinazione() + " WAS NOT " + to);
                    stations.add(newStation);
                    current.attach(newStation);
                }
            }
        }

        return results.size() > 0 ? results : null;
    }

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

    private class Station extends Node<Arrival> {

        protected Station(Arrival city) { super(city); }

        @Override
        public List<ViaggioEntity> climbUp() {

            List<ViaggioEntity> entities = new ArrayList<>();

            if (getParent() == null) return entities;

            entities.addAll(getParent().climbUp());
            entities.addAll((List<ViaggioEntity>) ViaggioDaoHibernate.instance().
                    getByCriteria(recreate()));

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
