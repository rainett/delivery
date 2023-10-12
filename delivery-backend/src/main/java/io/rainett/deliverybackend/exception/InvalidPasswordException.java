package io.rainett.deliverybackend.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends AppException {
    public InvalidPasswordException() {
        super("Invalid password", HttpStatus.BAD_REQUEST);
    }
}
