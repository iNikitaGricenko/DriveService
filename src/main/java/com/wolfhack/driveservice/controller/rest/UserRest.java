package com.wolfhack.driveservice.controller.rest;

import com.wolfhack.driveservice.model.Role;
import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.model.dto.UserDto;
import com.wolfhack.driveservice.model.dto.mapper.UserMapper;
import com.wolfhack.driveservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserRest {

    private final UserMapper mapper;
    private final UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = service.getAll();

        return ResponseEntity.ok().body(mapper.toDtos(users));
    }

    @GetMapping
    public ResponseEntity<User> getUser(Principal principal) {
        String subject = principal.getName();
        log.info("subject is", subject);
        return ResponseEntity.ok().body(service.get(subject));
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto dto) {
        User user = mapper.toUser(dto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

        return ResponseEntity.created(uri).body(mapper.toDto(service.add(user)));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(service.addRole(role));
    }

}
