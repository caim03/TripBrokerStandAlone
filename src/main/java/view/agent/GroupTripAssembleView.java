package view.agent;

import view.GroupTripFormView;
import view.desig.PacketAssembleView;

public class GroupTripAssembleView extends PacketAssembleView {

    public GroupTripAssembleView() {

        super(new GroupTripFormView());
    }

    @Override
    public void harvest() {

    }
}
