package controller.command;

public class DeamonThread extends Thread {

    public DeamonThread(Runnable target) {
        super(target);
        setDaemon(true);
    }
}
