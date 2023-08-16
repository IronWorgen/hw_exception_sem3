package Exceptions;

public class InValidPhoneNumber extends RuntimeException{
    public InValidPhoneNumber(String message) {
        super(message);
    }
}
