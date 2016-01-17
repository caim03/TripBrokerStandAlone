package controller.strategy;

public class FewerStopsSearchStrategy extends BFSearchStrategy {

    @Override
    protected Station makeNode(Arrival arrival) { return new FewerStopsStation(arrival); }

    class FewerStopsStation extends Station {

        protected FewerStopsStation(Arrival city) { super(city); }

        @Override
        public Integer getWeight() {
            if (parent != null) return 1 + parent.getWeight().intValue();
            else return 0;
        }
    }
}
