package com.wolfhack.driveservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AuthorizationUtils {
    public AuthorizationUtils() {
    }

    public Map<String, String> setError(HttpServletResponse response, Exception exception) {
        response.setHeader("error", exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<String, String>();
        error.put("error_message", exception.getMessage());
        return error;
    }

    public void putAuthorizationData(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        Arrays.stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public DecodedJWT decodeJWT(String authorizationHeader) {
        String token = authorizationHeader.substring("Drive ".length());
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public boolean isAuthorizationPage(HttpServletRequest request) {
        return request.getServletPath().equals("/login") || request.getServletPath().equals("/api/token/refresh"); // @TODO was /api/token/refresh/**
    }

    public boolean isTokenHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Drive ");
    }
}