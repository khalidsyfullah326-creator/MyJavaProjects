package exception;

/**
 * Custom checked exception.
 * Thrown when no event exists with the requested event ID.
 */
public class EventNotFoundException extends Exception {

    public EventNotFoundException(int eventId) {
        super("Event with ID " + eventId + " was not found.");
    }
}
