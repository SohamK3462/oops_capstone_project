package exceptions;

public class InvalidDepositValue extends RuntimeException {
    public InvalidDepositValue(String message) {
        super(message);
    }
}
