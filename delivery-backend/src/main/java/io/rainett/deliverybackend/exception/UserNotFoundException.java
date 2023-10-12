package io.rainett.deliverybackend.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AppException {
    public UserNotFoundException(String email) {
        super("User with email = [" + email + "] was not found", HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(Long id) {
        super("User with id = [" + id + "] was not found", HttpStatus.NOT_FOUND);
    }
}
