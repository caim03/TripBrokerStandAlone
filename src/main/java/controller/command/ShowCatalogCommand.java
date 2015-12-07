package controller.command;

import view.CatalogView;
import view.material.ConsolePane;

public class ShowCatalogCommand extends Command {

    ConsolePane pane;

    public ShowCatalogCommand(ConsolePane pane) {

        this.pane = pane;
    }

    @Override
    public void execute() {

        pane.setCenter(CatalogView.buildScene());
    }
}
