package Exceptions;

public class NotEnoughDataException extends RuntimeException{
    public NotEnoughDataException(String message) {
        super(message);
    }
}
