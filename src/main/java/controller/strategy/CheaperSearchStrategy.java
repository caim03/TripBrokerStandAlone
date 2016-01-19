package controller.strategy;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * BFSearchStrategy concrete implementation, searching for the cheapest routes from A to B;
 * it specifies primitive methods, fulfilling the Template Method pattern used to re-use the research
 * algorithm code.
 */
public class CheaperSearchStrategy extends BFSearchStrategy {

    /**
     * Primitive method implementation. This method provides both solution insertion and
     * solution sorting by weight value, returning an ordered list of solutions.
     * @param list List<Station>: set of already-found solution.
     * @param station Station: newly found solution.
     * @return boolean: always false; finding the cheapest routes requires full exploration.
     *                  This method drops worst solutions if List size exceeds search limits.
     */
    @Override
    protected boolean addToResults(List<Station> list, Station station) {

        int i;
        for (i = 0; i < list.size(); ++i)
            if (((CheaperStation) list.get(i)).getWeight() >
                    ((CheaperStation) station).getWeight()) break;
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
     * @return boolean: always false; finding the cheapest routes requires full exploration.
     */
    @Override protected boolean evaluate(Station current, List<Station> stations) { return false; }

    /**
     * @param arrival Arrival: Node acorn.
     * @return Station: a CheaperStation instance.
     */
    @Override protected Station makeNode(Arrival arrival) {
        return new CheaperStation(arrival);
    }

    /**
     * Station implementation; its weight is the total cost of the root-this Station route.
     */
    class CheaperStation extends Station {

        protected CheaperStation(Arrival city) { super(city); }

        /**
         * Recursive weight calculation.
         * @return Double: the total price for travelling from root to this Station.
         */
        public Double retrieveWeight() {
            if (parent == null) return 0.0; //recursive base
            return parent.getWeight().doubleValue() + cache.getPrezzo(); //recursion
        }

        @Override Double getWeight() { return (Double) super.getWeight(); }

        @Override public String weightToString() {
            return "Prezzo: " + NumberFormat.getCurrencyInstance(Locale.ITALY).format(getWeight());
        }
    }
}
