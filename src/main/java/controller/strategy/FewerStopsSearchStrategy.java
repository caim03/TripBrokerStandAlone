package controller.strategy;

import java.util.List;

public class FewerStopsSearchStrategy extends BFSearchStrategy {

    @Override
    protected Station makeNode(Arrival arrival) { return new FewerStopsStation(arrival); }

    @Override
    protected boolean addToResults(List<Station> list, Station station) {
        list.add(station);
        return true;
    }

    @Override
    protected boolean evaluate(Station station, List<Station> stations) {
        if (stations.isEmpty()) return false;
        int currentMin = stations.get(0).getWeight().intValue();
        int weight = station.getWeight().intValue();
        System.out.println("EVALUATING " + currentMin + " AND " + weight);
        return  currentMin <= weight;
    }

    class FewerStopsStation extends Station {

        protected FewerStopsStation(Arrival city) { super(city); }

        @Override
        public Integer getWeight() {
            if (parent != null) return 1 + parent.getWeight().intValue();
            else return 0;
        }

        @Override
        public String weightToString() {
            return "Cambi totali: " + super.weightToString();
        }
    }
}
