package io.github.aryansh05.ticketing.shared.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Email Already Exists");
    }
}
