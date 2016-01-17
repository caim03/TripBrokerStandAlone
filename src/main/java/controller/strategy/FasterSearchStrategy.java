package controller.strategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FasterSearchStrategy extends BFSearchStrategy {

    @Override
    protected void addToResults(List<Station> list, Station station) {
        super.addToResults(list, station);
        while (list.size() > 1) list.remove(1);
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
            return new SimpleDateFormat("HH:mm").format(new Date(getWeight()));
        }
    }
}