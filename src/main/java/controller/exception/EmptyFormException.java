package controller.exception;

public class EmptyFormException extends Exception {
    @Override public String getMessage() { return "Riempire tutti i campi obbligatori"; }
}
