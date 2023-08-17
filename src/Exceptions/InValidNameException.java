package Exceptions;

/**
 * ошибка в ФИО
 */
public class InValidNameException extends RuntimeException {
    public InValidNameException(String message) {
        super(message);
    }
}
