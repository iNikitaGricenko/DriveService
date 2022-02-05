package com.wolfhack.driveservice.service;

import com.wolfhack.driveservice.model.Driver;
import com.wolfhack.driveservice.model.Trip;
import com.wolfhack.driveservice.model.User;
import com.wolfhack.driveservice.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public void edit(Trip trip, Long id) {
        Trip foundedTrip = tripRepository.findById(id)
                .orElseThrow();
        trip.setId(foundedTrip.getId());
        tripRepository.save(trip);
    }

    public List<Trip> getAll() {
        return tripRepository.findAll();
    }

    public List<Trip> get(double latitude, double longitude) {
        return tripRepository.findByInitialLatitudeAndInitialLongitude(latitude, longitude);
    }

    public List<Trip> get(Driver driver) {
        return tripRepository.findByDriver(driver);
    }

    public List<Trip> get(User user) {
        return tripRepository.findByUser(user);
    }

    public Trip get(Long id) {
        return tripRepository.findById(id)
                .orElseThrow();
    }

    public Trip create(Trip trip) {
        return tripRepository.save(trip);
    }

    public void remove(Long id) {
        tripRepository.deleteById(id);
    }
}
