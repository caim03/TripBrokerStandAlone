package controller.command;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import view.desig.PacketAssembleView;

/**
 * Created by stg on 11/12/15.
 */
public class CreatePacketCommand extends Command {

    PacketAssembleView view;
    public CreatePacketCommand(PacketAssembleView view) {

        this.view = view;
    }

    @Override
    public void execute() {

        view.harvest();
    }
}
