/**
 * ================================================
 * CUSTOM EXCEPTION (Exception Handling)
 * ================================================
 * A custom CHECKED exception used across this app whenever a
 * user tries to set a negative value for any tracked category
 * (hours/count can never be negative in real life).
 *
 * Because it extends Exception (a CHECKED exception, not
 * RuntimeException), every constructor/method that can throw
 * it must either catch it or declare "throws InvalidTargetException"
 * - the compiler forces the caller to handle this case, which is
 * exactly what we want for a business-rule violation like this.
 */
public class InvalidTargetException extends Exception {

    public InvalidTargetException(String message) {
        // SUPER: passes the error message up to the parent
        // Exception class so getMessage() returns it correctly.
        super(message);
    }
}
