package io.rainett.deliverybackend.config.filter;

import io.rainett.deliverybackend.config.auth.provider.UserAuthProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider authProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        getTokenAndSetAuthentication(request);
        filterChain.doFilter(request, response);
    }

    private void getTokenAndSetAuthentication(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (hasBearer(header)) {
            AuthElements authElements = getAuthElements(header);
            setAuthentication(request, authElements.token);
        }
    }

    private void setAuthentication(HttpServletRequest request, String token) {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            // use regular validation for GET requests and strong validation for any other type of request
            Authentication authentication = request.getMethod().equals("GET")
                    ? authProvider.validateToken(token)
                    : authProvider.validateTokenStrongly(token);
            context.setAuthentication(authentication);
        } catch (RuntimeException ex) {
            SecurityContextHolder.clearContext();
            throw ex;
        }
    }

    private static AuthElements getAuthElements(String header) {
        String[] authElementsArray = header.split(" ");
        return new AuthElements(authElementsArray[0], authElementsArray[1]);
    }

    private static boolean hasBearer(String header) {
        if (header == null) {
            return false;
        }
        String[] authElementsArray = header.split(" ");
        return authElementsArray.length == 2 && authElementsArray[0].equals("Bearer");
    }

    private record AuthElements(String bearer, String token) {
    }

}
