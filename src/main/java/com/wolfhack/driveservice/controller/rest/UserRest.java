package com.wolfhack.driveservice.controller.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wolfhack.driveservice.model.Role;
import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.model.dto.UserDto;
import com.wolfhack.driveservice.model.dto.mapper.UserMapper;
import com.wolfhack.driveservice.service.UserService;
import com.wolfhack.driveservice.utils.AuthenticationUtils;
import com.wolfhack.driveservice.utils.AuthorizationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserRest {

    private final UserMapper mapper;
    private final UserService service;
    private AuthenticationUtils authenticationUtils = new AuthenticationUtils();
    private AuthorizationUtils authorizationUtils = new AuthorizationUtils();

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = service.getAll();

        return ResponseEntity.ok().body(mapper.toDtos(users));
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser(Principal principal) {
        String subject = principal.getName();
        log.info("subject is", subject);
        return ResponseEntity.ok().body(service.get(subject));
    }

    @PostMapping("/user/save")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        User user = mapper.toUser(dto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

        return ResponseEntity.created(uri).body(mapper.toDto(service.add(user)));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(service.addRole(role));
    }

    @PostMapping("/token/refresh")
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
