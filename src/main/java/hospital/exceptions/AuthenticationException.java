package hospital.exceptions;
public class AuthenticationException extends Exception {

    public AuthenticationException() {
        super("Invalid email or password. Please try again.");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}