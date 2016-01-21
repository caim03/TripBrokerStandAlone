package view.agent;

import controller.GroupTripOverseer;
import view.ObservantSpinner;
import view.desig.PacketList;
import view.observers.Observer;

public class GroupTripList extends PacketList {

    private int qu = 999;

    @Override protected void addListener() { addListener(new GroupTripOverseer(this)); }

    @Override
    public Double requestInfo(Observer observer) {
        if (observer instanceof ObservantSpinner) return (double) qu;
        return requestInfo();
    }

    public int getQu() {
        return qu;
    }

    public void setQu(int qu) { setQu(qu, false); }
    public void forceQu(int qu) { setQu(qu, true); }

    private void setQu(int qu, boolean force) {

        System.out.println("QUANTITY " + qu);
        if (force || this.qu > qu) {
            System.out.println("UPDATED");
            this.qu = qu;
            publishQu();
        }
    }

    private void publishQu() {
        for (Observer o : observers) {
            if (o instanceof ObservantSpinner) o.update();
        }
    }
}
