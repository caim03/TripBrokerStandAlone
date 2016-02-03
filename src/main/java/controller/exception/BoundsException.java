package controller.exception;

public class BoundsException extends Exception {
    @Override
    public String getMessage() { return "Il prezzo deve essere compreso tra i suoi limiti"; }
}
