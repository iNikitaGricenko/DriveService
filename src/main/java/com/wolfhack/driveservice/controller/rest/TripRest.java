package com.wolfhack.driveservice.controller.rest;

import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.model.dto.TripInfoDto;
import com.wolfhack.driveservice.model.dto.UserDto;
import com.wolfhack.driveservice.model.dto.mapper.TripMapper;
import com.wolfhack.driveservice.model.dto.mapper.UserMapper;
import com.wolfhack.driveservice.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
@Slf4j
public class TripRest {

    private final TripService tripService;
    private final TripMapper tripMapper;
    private final UserMapper userMapper;

    @GetMapping
    public List<TripInfoDto> getAll() {
        return tripMapper.toDtos(tripService.getAll());
    }

    @GetMapping("/{id}")
    public TripInfoDto getOne(@PathVariable("id") Long id) {
        return tripMapper.toDto(tripService.get(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        tripService.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user")
    public List<TripInfoDto> getAllUsersTrips(@RequestBody UserDto userDto) {
        User user = userMapper.toUser(userDto);
        return tripMapper.toDtos(tripService.get(user));
    }

}
