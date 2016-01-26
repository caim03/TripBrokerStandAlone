package view.agent;

import controller.agent.GroupTripOverseer;
import view.ObservantSpinner;
import view.desig.PacketList;
import view.observers.Observer;

public class GroupTripList extends PacketList {

    private int qu = 99;

    @Override protected void addListener(boolean notify) { addListener(new GroupTripOverseer(this, notify)); }

    @Override
    public Double requestInfo(Observer observer) {
        if (observer instanceof ObservantSpinner.ObserverAdapter) return (double) qu;
        return requestInfo();
    }

    public void setQu(int qu) { setQu(qu, false); }
    public void forceQu(int qu) { setQu(qu, true); }

    private void setQu(int qu, boolean force) {
        if (force || this.qu > qu) {
            this.qu = qu;
            publishQu();
        }
    }

    private void publishQu() {
        for (Observer o : observers) {
            if (o instanceof ObservantSpinner.ObserverAdapter)
                o.update();
        }
    }
}
