package controller.exception;

public class LocationsException extends Exception {
    @Override
    public String getMessage() {
        return "I pacchetti dovrebbero iniziare e terminare con un viaggio, " +
                "check-in e check-out nella stessa location";
    }
}
