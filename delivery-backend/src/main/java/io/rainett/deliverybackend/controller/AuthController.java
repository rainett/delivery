package io.rainett.deliverybackend.controller;

import io.rainett.deliverybackend.dto.CredentialsDto;
import io.rainett.deliverybackend.dto.SignUpDto;
import io.rainett.deliverybackend.dto.UserBaseDto;
import io.rainett.deliverybackend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserBaseDto> login(@RequestBody @Valid CredentialsDto credentialsDto) {
        UserBaseDto userBaseDto = userService.login(credentialsDto);
        return ResponseEntity.ok(userBaseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserBaseDto> register(@RequestBody @Valid SignUpDto signUpDto) {
        UserBaseDto userBaseDto = userService.register(signUpDto);
        return ResponseEntity.created(URI.create("/api/v1/users/" + userBaseDto.getId())).body(userBaseDto);
    }

}
