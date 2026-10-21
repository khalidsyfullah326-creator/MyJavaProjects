package exception;

/**
 * Custom checked exception.
 * Thrown when a participant tries to register for the same event twice.
 */
public class DuplicateRegistrationException extends Exception {

    public DuplicateRegistrationException(String message) {
        super(message); // super: passes the message to the parent Exception class
    }
}
