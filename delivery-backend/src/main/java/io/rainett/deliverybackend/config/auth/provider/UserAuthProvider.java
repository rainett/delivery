package io.rainett.deliverybackend.config.auth.provider;


import io.rainett.deliverybackend.dto.UserBaseDto;
import org.springframework.security.core.Authentication;

/**
 *  Provides token creation and validation
 */
public interface UserAuthProvider {

    String createToken(UserBaseDto userBaseDto);
    Authentication validateToken(String token);
    Authentication validateTokenStrongly(String token);

}
