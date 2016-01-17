package controller.strategy;

import java.util.List;

public class CheaperSearchStrategy extends BFSearchStrategy {

    @Override
    protected void addToResults(List<Station> list, Station station) {
        super.addToResults(list, station);
        while (list.size() > 1) list.remove(1);
    }

    @Override
    protected Station makeNode(Arrival arrival) {
        return new CheaperStation(arrival);
    }

    class CheaperStation extends Station {

        protected CheaperStation(Arrival city) { super(city); }

        public Double getWeight() {
            if (parent == null) return 0.0;
            return parent.getWeight().doubleValue() + cache.get(parent.acorn).getPrezzo();
        }
    }
}
