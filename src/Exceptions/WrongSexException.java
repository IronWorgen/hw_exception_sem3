package Exceptions;

/**
 * неправильный пол
 */
public class WrongSexException extends RuntimeException {
    public WrongSexException(String message) {
        super(message);
    }
}
