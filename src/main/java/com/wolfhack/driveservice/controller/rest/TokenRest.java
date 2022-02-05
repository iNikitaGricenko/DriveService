package com.wolfhack.driveservice.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfhack.driveservice.model.Role;
import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.service.UserService;
import com.wolfhack.driveservice.utils.AuthenticationUtils;
import com.wolfhack.driveservice.utils.AuthorizationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
@Slf4j
public class TokenRest {

    private final UserService service;

    private AuthenticationUtils authenticationUtils = new AuthenticationUtils();
    private AuthorizationUtils authorizationUtils = new AuthorizationUtils();

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationUtils.isTokenHeader(authorizationHeader)) {
            Map<String, String> tokens = getTokens(request, response, authorizationHeader);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    private Map<String, String> getTokens(HttpServletRequest request, HttpServletResponse response, String authorizationHeader) {
        String refresh_token = authorizationHeader.substring("Drive ".length());

        DecodedJWT decodedJWT = authorizationUtils.decodeJWT(authorizationHeader);

        String username = decodedJWT.getSubject();
        User user = service.get(username);

        String access_token = generateToken(request, user, Algorithm.HMAC256("secret".getBytes()));
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        return tokens;
    }

    private String generateToken(HttpServletRequest request, User user, Algorithm algorithm) {
        int time = 10080 * 60 * 1000;
        return JWT.create()
                .withSubject(user.getPhone())
                .withExpiresAt(new Date(System.currentTimeMillis() + time))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(toList()))
                .sign(algorithm);
    }

}
