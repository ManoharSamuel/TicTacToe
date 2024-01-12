package exceptions;

public class InvalidSymbolCountException extends RuntimeException{
    public InvalidSymbolCountException(String message) {
        super(message);
    }
}
