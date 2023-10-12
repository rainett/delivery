package io.rainett.deliverybackend.service;

import io.rainett.deliverybackend.dto.CredentialsDto;
import io.rainett.deliverybackend.dto.SignUpDto;
import io.rainett.deliverybackend.dto.UserBaseDto;

public interface UserService {
    UserBaseDto getUserByEmail(String email);

    UserBaseDto login(CredentialsDto credentialsDto);

    UserBaseDto register(SignUpDto signUpDto);
}
