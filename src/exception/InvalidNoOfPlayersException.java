package exception;

public class InvalidNoOfPlayersException extends RuntimeException{
    public InvalidNoOfPlayersException(String message) {
        super(message);
    }
}
