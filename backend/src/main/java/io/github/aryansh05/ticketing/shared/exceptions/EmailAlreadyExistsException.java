package io.github.aryansh05.ticketing.shared.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Email Already Exists");
    }
}
