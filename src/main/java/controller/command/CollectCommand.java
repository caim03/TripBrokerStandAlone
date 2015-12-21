package controller.command;

import view.Collector;

public class CollectCommand extends Command {

    Collector collector;

    public CollectCommand(Collector collector) { this.collector = collector; }
    @Override public void execute() { collector.harvest(); }
}
