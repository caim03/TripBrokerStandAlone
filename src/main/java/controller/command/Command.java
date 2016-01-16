package controller.command;

/**
 * Base class for Command pattern realization; this pattern uncouples invoker and receiver objects,
 * this way there is no direct interaction between the two of them. Extending this class requires
 * the implementation of the execute() method.
 */
public abstract class Command {

    protected abstract void execute();
}
