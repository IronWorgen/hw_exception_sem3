package Exceptions;

/**
 * неверный формат номера телефона
 */
public class InValidPhoneNumber extends RuntimeException {
    public InValidPhoneNumber(String message) {
        super(message);
    }
}
