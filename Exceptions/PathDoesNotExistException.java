package Exceptions;

public class PathDoesNotExistException extends Exception{
    public PathDoesNotExistException() {
        super("File path does not exist");
    }
    
    public PathDoesNotExistException(String message) {
        super(message);
    }
}
