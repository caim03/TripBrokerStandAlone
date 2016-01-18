package controller.strategy;

import java.util.List;

public class CheaperSearchStrategy extends BFSearchStrategy {

    @Override
    protected boolean addToResults(List<Station> list, Station station) {

        int i;
        for (i = 0; i < list.size(); ++i)
            if (list.get(i).getWeight().doubleValue() > station.getWeight().doubleValue()) break;

        boolean partial = i == list.size();
        boolean result = true;
        list.add(i, station);

        while (list.size() > limit) {
            list.remove(list.size() - 1);
            if (partial) result = partial = false;
        }

        return result;
    }

    @Override protected boolean evaluate(Station station, List<Station> station1) { return false; }

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

        @Override
        public String weightToString() {
            return "Prezzo: " + super.weightToString();
        }
    }
}
