package controller.strategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * BFSearchStrategy concrete implementation, searching for the fastest routes from root to destination.
 */
public class FasterSearchStrategy extends BFSearchStrategy {

    /**
     * Primitive method implementation. This method provides both solution insertion and
     * solution sorting by weight value, returning an ordered list of solutions.
     * @param list List<Station>: set of already-found solution.
     * @param station Station: newly found solution.
     * @return boolean: always false; finding the fastest routes requires full exploration.
     *                  This method drops worst solutions if List size exceeds search limits.
     */
    @Override
    protected boolean addToResults(List<Station> list, Station station) {

        int i;
        for (i = 0; i < list.size(); ++i)
            if (list.get(i).getWeight().doubleValue() > station.getWeight().doubleValue()) break;
        //Sorting routine

        list.add(i, station); //actual insertion

        while (list.size() > limit) list.remove(list.size() - 1); //pruning

        return false;
    }

    /**
     * Primitive method implementation.
     * @param current Station: the currently examined Station instance.
     * @param stations List<Station>: set of already found solutions, used to determine
     *                 an eventual stop.
     * @return boolean: always false; finding the fastest routes requires full exploration.
     */
    @Override protected boolean evaluate(Station current, List<Station> stations) { return false; }

    /**
     * @param arrival Arrival: Node acorn.
     * @return Station: a CheaperStation instance.
     */
    @Override protected Station makeNode(Arrival arrival) { return new FasterStation(arrival); }

    /**
     * Station implementation; its weight is the span of time from root departure to this Station arrival.
     */
    class FasterStation extends Station {

        protected FasterStation(Arrival city) { super(city); }

        /**
         * Recursive weight calculation.
         * @return Long: time in milliseconds from root departure to this Station arrival.
         */
        @Override
        public Long retrieveWeight() {
            if (parent == null) return 0L;
            return cache.getDataArrivo().getTime() - getStartingTime();
        }

        /**
         * Recursive utility method.
         * @return long: root departure timestamp in milliseconds.
         */
        private long getStartingTime() {

            if (parent == null) return 0; //recursive base
            else {
                long time = ((FasterStation) parent).getStartingTime(); //recursion
                if (time > 0) return time; //pass on result
                else return cache.getDataInizio().getTime(); //retrieve result
            }
        }

        @Override Long getWeight() { return (Long) super.getWeight(); }

        @Override public String weightToString() {
            return "Tempo totale: " + new SimpleDateFormat("HH:mm").format(new Date(getWeight()));
        }
    }
}