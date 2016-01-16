package controller.command;

import view.Collector;

/**
 * Command implementation, whose role is to call the harvest() method of
 * the referenced Collector instance.
 */
public class CollectCommand extends Command {

    Collector collector;

    /**
     * Main contructor
     * @param collector Collector: a Collector instance.
     */
    public CollectCommand(Collector collector) { this.collector = collector; }
    @Override public void execute() { collector.harvest(); } //Call harvest()
}
