package Exceptions;

/**
 * недостаточно данных
 */
public class NotEnoughDataException extends RuntimeException {
    public NotEnoughDataException(String message) {
        super(message);
    }
}
