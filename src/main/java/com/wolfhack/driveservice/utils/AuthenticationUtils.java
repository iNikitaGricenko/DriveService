package com.wolfhack.driveservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfhack.driveservice.service.CustomUserDetails;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.springframework.http.MediaType.*;

public class AuthenticationUtils {
    public AuthenticationUtils() {
    }

    public void putTokens(HttpServletRequest request, HttpServletResponse response, CustomUserDetails user, Algorithm algorithm) throws IOException {
        String access_token = generateToken(user, request, algorithm, 10080);
        String refresh_token = generateToken(user, request, algorithm, 20160);

        Map<String, String> tokens = new HashMap<String, String>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        writeResponse(response, tokens);
    }

    public void writeResponse(HttpServletResponse response, Map<String, String> tokens) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    public String generateToken(CustomUserDetails user, HttpServletRequest request, Algorithm algorithm, int minutes) {
        int time = minutes * 60 * 1000;
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(role -> role.getAuthority()).collect(toList()))
                .sign(algorithm);
    }
}