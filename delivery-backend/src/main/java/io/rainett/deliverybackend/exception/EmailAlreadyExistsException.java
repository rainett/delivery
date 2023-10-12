package io.rainett.deliverybackend.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends AppException {
    public EmailAlreadyExistsException(String email) {
        super("User with email = [" + email + "] already exists", HttpStatus.BAD_REQUEST);
    }
}
