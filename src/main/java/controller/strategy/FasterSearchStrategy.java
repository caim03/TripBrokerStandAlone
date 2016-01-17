package controller.strategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FasterSearchStrategy extends BFSearchStrategy {

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

    @Override
    protected Station makeNode(Arrival arrival) {
        return new FasterStation(arrival);
    }

    class FasterStation extends Station {

        protected FasterStation(Arrival city) { super(city); }

        @Override
        public Long getWeight() {
            if (parent == null) return 0l;
            return cache.get(parent.acorn).getDataArrivo().getTime() - getStartingTime();
        }

        private long getStartingTime() {

            if (parent == null) return 0;
            else {
                long time = ((FasterStation) parent).getStartingTime();
                if (time > 0) return time;
                else return cache.get(parent.acorn).getDataInizio().getTime();
            }
        }

        @Override
        public String weightToString() {
            return "Tempo totale: " + new SimpleDateFormat("HH:mm").format(new Date(getWeight()));
        }
    }
}