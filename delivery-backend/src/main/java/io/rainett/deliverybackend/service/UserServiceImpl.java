package io.rainett.deliverybackend.service;

import io.rainett.deliverybackend.config.auth.provider.UserAuthProvider;
import io.rainett.deliverybackend.dto.CredentialsDto;
import io.rainett.deliverybackend.dto.SignUpDto;
import io.rainett.deliverybackend.dto.UserBaseDto;
import io.rainett.deliverybackend.exception.EmailAlreadyExistsException;
import io.rainett.deliverybackend.exception.InvalidPasswordException;
import io.rainett.deliverybackend.exception.UserNotFoundException;
import io.rainett.deliverybackend.mapper.UserMapper;
import io.rainett.deliverybackend.model.UserBase;
import io.rainett.deliverybackend.repository.UserBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserAuthProvider authProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserBaseRepository userBaseRepository;

    private static void throwEmailAlreadyExistsException(UserBase u) {
        throw new EmailAlreadyExistsException(u.getEmail());
    }

    @Override
    public UserBaseDto getUserByEmail(String email) {
        return userBaseRepository
                .findByEmail(email)
                .map(userMapper::userToDto)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public UserBaseDto login(CredentialsDto credentialsDto) {
        UserBase userBase = userBaseRepository
                .findByEmail(credentialsDto.email())
                .orElseThrow(() -> new UserNotFoundException(credentialsDto.email()));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()), userBase.getPassword())) {
            UserBaseDto userBaseDto = userMapper.userToDto(userBase);
            userBaseDto.setToken(authProvider.createToken(userBaseDto));
            return userBaseDto;
        }
        throw new InvalidPasswordException();
    }

    @Override
    public UserBaseDto register(SignUpDto signUpDto) {
        userBaseRepository.findByEmail(signUpDto.email())
                .ifPresent(UserServiceImpl::throwEmailAlreadyExistsException);
        UserBase userBase = userMapper.signUpToUser(signUpDto);
        userBase.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.email())));
        UserBase savedUser = userBaseRepository.save(userBase);
        UserBaseDto userBaseDto = userMapper.userToDto(savedUser);
        userBaseDto.setToken(authProvider.createToken(userBaseDto));
        return userBaseDto;
    }
}
