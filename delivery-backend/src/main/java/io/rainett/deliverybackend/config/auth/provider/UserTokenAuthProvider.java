package io.rainett.deliverybackend.config.auth.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.rainett.deliverybackend.dto.UserBaseDto;
import io.rainett.deliverybackend.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserTokenAuthProvider implements UserAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

    private static final Function<String, Algorithm> ALGORITHM_SUPPLIER = Algorithm::HMAC256;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public String createToken(UserBaseDto userBaseDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000); // 1 hour
        Algorithm algorithm = ALGORITHM_SUPPLIER.apply(secretKey);
        return JWT.create()
                .withSubject(userBaseDto.getEmail())
                .withClaim("firstName", userBaseDto.getFirstName())
                .withClaim("lastName", userBaseDto.getLastName())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    @Override
    public Authentication validateToken(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        UserBaseDto userBaseDto = UserBaseDto.builder()
                .email(decodedJWT.getSubject())
                .firstName(decodedJWT.getClaim("firstName").asString())
                .lastName(decodedJWT.getClaim("lastName").asString())
                .build();
        return new UsernamePasswordAuthenticationToken(userBaseDto, null, Collections.emptyList());
    }

    @Override
    public Authentication validateTokenStrongly(String token) {
        DecodedJWT decodedJWT = getDecodedJWT(token);
        UserBaseDto userBaseDto = userService.getUserByEmail(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userBaseDto, null, Collections.emptyList());
    }

    private DecodedJWT getDecodedJWT(String token) {
        Algorithm algorithm = ALGORITHM_SUPPLIER.apply(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

}
