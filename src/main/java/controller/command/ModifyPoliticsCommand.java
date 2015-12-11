package controller.command;

import view.admin.ModifyPoliticsView;
import view.material.ConsolePane;

public class ModifyPoliticsCommand extends Command {

    ConsolePane pane;

    public ModifyPoliticsCommand(ConsolePane container) {
        this.pane = container;
    }

    @Override
    public void execute() {
        pane.setCenter(new ModifyPoliticsView().BuildScene());
    }
}
