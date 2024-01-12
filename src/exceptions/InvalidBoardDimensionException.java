package exceptions;

public class InvalidBoardDimensionException extends RuntimeException{
    public InvalidBoardDimensionException(String message) {
        super(message);
    }
}
