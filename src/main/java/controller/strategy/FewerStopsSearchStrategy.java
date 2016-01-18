package controller.strategy;

import java.util.List;

/**
 * BFSearchStrategy concrete implementation, searching for routes with fewer stops.
 * This translates to a pure BF search algorithm, with weight being the Station height.
 */
public class FewerStopsSearchStrategy extends BFSearchStrategy {

    /**
     * Primitive method implementation. It simply adds the new solution in the set and
     * returns true to trigger found solution counter increment.
     * @param list List<Station>: set of already-found solution.
     * @param station Station: newly found solution.
     * @return boolean: always true.
     */
    @Override
    protected boolean addToResults(List<Station> list, Station station) {
        list.add(station);
        return true;
    }

    /**
     * Primitive method implementation. Since the search algorithm degenerates to pure BFS,
     * research stops when all sibling nodes (ie with same height) have been analyzed; their
     * children are ignored and exploration buffer is quickly emptied.
     * @param current Station: the currently examined Station instance.
     * @param stations List<Station>: set of already found solutions, used to determine
     *                 an eventual stop.
     * @return boolean: whether or not BFS can continue or should empty its buffer.
     */
    @Override
    protected boolean evaluate(Station current, List<Station> stations) {
        if (stations.isEmpty()) return false; //no solutions found yet, game still on
        int currentMin = ((FewerStopsStation) stations.get(0)).getWeight(),
                weight = ((FewerStopsStation) current).getWeight();
        return  currentMin <= weight;
    }

    /**
     * @param arrival Arrival: Node acorn.
     * @return Station: a CheaperStation instance.
     */
    @Override protected Station makeNode(Arrival arrival) { return new FewerStopsStation(arrival); }

    /**
     * Station implementation; its weight is the Node height in the tree.
     */
    class FewerStopsStation extends Station {

        protected FewerStopsStation(Arrival city) { super(city); }

        /**
         * Recursive weight calculation.
         * @return Integer: height level.
         */
        @Override
        public Integer retrieveWeight() {
            if (parent == null) return 0; //recursive base
            else return 1 + (int) parent.getWeight(); //recursion
        }

        @Override Integer getWeight() { return (Integer) super.getWeight(); }
        @Override public String weightToString() {
            return "Cambi totali: " + super.weightToString();
        }
    }
}
