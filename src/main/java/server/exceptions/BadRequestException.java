package server.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("Bad request");
    }
}
