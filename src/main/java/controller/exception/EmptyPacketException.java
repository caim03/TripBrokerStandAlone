package controller.exception;

public class EmptyPacketException extends Exception {
    @Override public String getMessage() { return "Pacchetto vuoto"; }
}
